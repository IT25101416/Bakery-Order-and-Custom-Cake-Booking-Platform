package com.example.bakeryproject.order;

import com.example.bakeryproject.cart.CartItem;
import com.example.bakeryproject.product.Cake;
import com.example.bakeryproject.product.CakeService;
import com.example.bakeryproject.product.Savoury;
import com.example.bakeryproject.product.SavouryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

// @Controller = this class handles HTTP requests and returns HTML views.
// @RequestMapping('/orders') = all routes in this class start with /orders
@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CakeService cakeService;
    private final SavouryService savouryService;
    private final OnlineOrderProcessor orderProcessor = new
            OnlineOrderProcessor();
    public OrderController(OrderService orderService, CakeService
            cakeService, SavouryService savouryService) {
        this.orderService = orderService;
        this.cakeService = cakeService;
        this.savouryService = savouryService;
    }
    @GetMapping("/place")
    public String showPlaceOrderPage(@RequestParam(required = false) String
                                             cakeError,
                                     Model model,
                                     HttpSession session) {
        if (session.getAttribute("role") == null) {
            return "redirect:/";
        }
        Order order = new Order();
        if ("CUSTOMER".equals(session.getAttribute("role")) &&
                session.getAttribute("customerName") != null) {
            order.setCustomerName(session.getAttribute("customerName").toString());
        }
        model.addAttribute("order", order);
        model.addAttribute("cakes", cakeService.getAllCakes());
        model.addAttribute("savouries", savouryService.getAllSavouries());
        model.addAttribute("cakeError", cakeError != null);
        return "order/place-order";
    }
    @PostMapping("/place")
    public String placeOrder(@ModelAttribute Order order, HttpSession
            session) {
        if (session.getAttribute("role") == null) {
            return "redirect:/login";
        }
        if ("CUSTOMER".equals(session.getAttribute("role")) &&
                session.getAttribute("customerName") != null) {
            order.setCustomerName(session.getAttribute("customerName").toString());
        }
        Cake selectedCake =
                cakeService.findCakeByName(order.getCakeName());
        Savoury selectedSavoury =
                savouryService.findSavouryByName(order.getCakeName());
        if ((selectedCake == null && selectedSavoury == null) ||
                order.getQuantity() <= 0) {
            return "redirect:/orders/place?cakeError=true";
        }
        double unitPrice;
        if (selectedCake != null) {
            order.setCakeName(selectedCake.getCakeName());
            unitPrice = selectedCake.getPrice();
        } else {
            order.setCakeName(selectedSavoury.getSavouryName());
            unitPrice = selectedSavoury.getPrice();
        }
        double total = orderProcessor.calculateTotal(unitPrice,
                order.getQuantity());
        order.setTotalPrice(total);
        order.setStatus("Pending");
        orderService.addOrder(order);
        if ("CUSTOMER".equals(session.getAttribute("role"))) {
            return "redirect:/orders/my-status?ordered=true";
        }
        return "redirect:/orders/list";
    }
    @SuppressWarnings("unchecked")
    @PostMapping("/place-cart")
    public String placeCartOrder(HttpSession session) {
        if (!"CUSTOMER".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        Object cartObject = session.getAttribute("cart");
        if (!(cartObject instanceof List<?>)) {
            session.setAttribute("cartCount", 0);
            return "redirect:/cart";
        }
        List<CartItem> cartItems = new ArrayList<>();
        for (Object item : (List<?>) cartObject) {
            if (item instanceof CartItem) {
                cartItems.add((CartItem) item);
            }
        }
        if (cartItems.isEmpty()) {
            session.removeAttribute("cart");
            session.setAttribute("cartCount", 0);
            return "redirect:/cart";
        }
        String customerName = session.getAttribute("customerName") != null
                ? session.getAttribute("customerName").toString()
                : session.getAttribute("username").toString();
        for (CartItem cartItem : cartItems) {
            Order order = new Order();
            order.setCustomerName(customerName);
            order.setCakeName(cartItem.getCakeName());
            order.setQuantity(cartItem.getQuantity());
            order.setTotalPrice(cartItem.getSubTotal());
            order.setStatus("Pending");
            orderService.addOrder(order);
        }
// Clear cart after successfully creating the orders.
        session.removeAttribute("cart");
        session.setAttribute("cartCount", 0);
        return "redirect:/orders/my-status?ordered=true";
    }
    @GetMapping("/list")
    public String showOrderList(Model model, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/customer-home";
        }
        model.addAttribute("orders", orderService.getAllOrders());
        return "order/order-list";
    }
    @GetMapping("/my-status")
    public String showCustomerOrderStatus(@RequestParam(required = false)
                                          String orderId,
                                          @RequestParam(required = false)
                                          String ordered,
                                          Model model,
                                          HttpSession session) {
        if (!"CUSTOMER".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        String customerName = session.getAttribute("customerName") != null
                ? session.getAttribute("customerName").toString()
                : session.getAttribute("username").toString();
        List<Order> myOrders =
                orderService.getOrdersByCustomerName(customerName);
        model.addAttribute("orders", myOrders);
        model.addAttribute("ordered", ordered != null);
        if (orderId != null && !orderId.trim().isEmpty()) {
            Order searchedOrder = orderService.findOrderById(orderId);
            if (searchedOrder == null) {
                model.addAttribute("error", "No order found for this Order ID.");
            } else if (searchedOrder.getCustomerName() == null ||
                    !searchedOrder.getCustomerName().equalsIgnoreCase(customerName)) {
                model.addAttribute("error", "This order does not belong to your account.");
            } else {
                model.addAttribute("searchedOrder", searchedOrder);
            }
        }
        return "order/customer-order-status";
    }
    @GetMapping("/status")
    public String redirectStatusPage(HttpSession session) {
        if ("ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/orders/list";
        }
        return "redirect:/orders/my-status";
    }
    @GetMapping("/status/{id}")
    public String showAdminStatusPage(@PathVariable String id, Model model,
                                      HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/customer-home";
        }
        Order order = orderService.findOrderById(id);
        model.addAttribute("order", order);
        return "order/admin-update-status";
    }
    @PostMapping("/status")
    public String updateStatus(@RequestParam String orderId, @RequestParam
    String status, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/customer-home";
        }
        orderService.updateStatus(orderId, status);
        return "redirect:/orders/list";
    }
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable String id, HttpSession session)
    {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/customer-home";
        }
        orderService.deleteOrder(id);
        return "redirect:/orders/list";
    }
}

