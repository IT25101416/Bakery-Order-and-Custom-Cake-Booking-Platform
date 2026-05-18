package com.example.bakeryproject.cart;

import com.example.bakeryproject.product.Cake;
import com.example.bakeryproject.product.CakeService;
import com.example.bakeryproject.product.Savoury;
import com.example.bakeryproject.product.SavouryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CakeService cakeService;
    private final SavouryService savouryService;

    public CartController(CakeService cakeService, SavouryService savouryService) {
        this.cakeService = cakeService;
        this.savouryService = savouryService;
    }

    @GetMapping
    public String viewCart(Model model, HttpSession session) {
        if (session.getAttribute("role") == null) {
            return "redirect:/login";
        }

        List<CartItem> cart = getCart(session);

        model.addAttribute("cartItems", cart);
        model.addAttribute("cartTotal", calculateCartTotal(cart));

        updateCartCount(session, cart);

        return "cart";
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable String id, HttpSession session) {
        if (session.getAttribute("role") == null) {
            return "redirect:/login";
        }

        Cake cake = cakeService.findCakeById(id);

        if (cake != null) {
            List<CartItem> cart = getCart(session);
            boolean found = false;

            for (CartItem item : cart) {
                if (item.getCakeId().equals(cake.getCakeId())) {
                    item.setQuantity(item.getQuantity() + 1);
                    found = true;
                    break;
                }
            }

            if (!found) {
                cart.add(new CartItem(
                        cake.getCakeId(),
                        cake.getCakeName(),
                        cake.getCategory(),
                        cake.getPrice(),
                        1
                ));
            }

            session.setAttribute("cart", cart);
            updateCartCount(session, cart);
        }

        return "redirect:/products/list";
    }

    @GetMapping("/add-savoury/{id}")
    public String addSavouryToCart(@PathVariable String id, HttpSession session) {
        if (session.getAttribute("role") == null) {
            return "redirect:/login";
        }

        Savoury savoury = savouryService.findSavouryById(id);

        if (savoury != null) {
            List<CartItem> cart = getCart(session);
            boolean found = false;

            for (CartItem item : cart) {
                if (item.getCakeId().equals(savoury.getSavouryId())) {
                    item.setQuantity(item.getQuantity() + 1);
                    found = true;
                    break;
                }
            }

            if (!found) {
                cart.add(new CartItem(
                        savoury.getSavouryId(),
                        savoury.getSavouryName(),
                        savoury.getCategory(),
                        savoury.getPrice(),
                        1
                ));
            }

            session.setAttribute("cart", cart);
            updateCartCount(session, cart);
        }

        return "redirect:/savouries/list";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable String id, HttpSession session) {
        List<CartItem> cart = getCart(session);

        cart.removeIf(item -> item.getCakeId().equals(id));

        session.setAttribute("cart", cart);
        updateCartCount(session, cart);

        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        session.setAttribute("cartCount", 0);

        return "redirect:/cart";
    }

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        Object cartObject = session.getAttribute("cart");

        if (cartObject instanceof List<?>) {
            return (List<CartItem>) cartObject;
        }

        List<CartItem> cart = new ArrayList<>();
        session.setAttribute("cart", cart);
        session.setAttribute("cartCount", 0);

        return cart;
    }

    private void updateCartCount(HttpSession session, List<CartItem> cart) {
        int count = 0;

        for (CartItem item : cart) {
            count += item.getQuantity();
        }

        session.setAttribute("cartCount", count);
    }

    private double calculateCartTotal(List<CartItem> cart) {
        double total = 0;

        for (CartItem item : cart) {
            total += item.getSubTotal();
        }

        return total;
    }
}