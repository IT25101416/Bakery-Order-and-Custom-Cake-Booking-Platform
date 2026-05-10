package com.example.bakeryproject.C3_Order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class OrderController {

    private static final String FILE = "orders.txt";

    // CREATE - Standard order
    @PostMapping("/place-order")
    public String placeOrder(@RequestParam String customerName,
                             @RequestParam String cakeType,
                             @RequestParam int quantity,
                             @RequestParam double price) {
        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Order order = new Order(orderId, customerName, cakeType, quantity, price);
        FileHandler.saveToFile(FILE, order.toFileString());
        return "redirect:/order-list.html?success=true";
    }

    // CREATE - Custom cake order
    @PostMapping("/place-custom-order")
    public String placeCustomOrder(@RequestParam String customerName,
                                   @RequestParam String cakeType,
                                   @RequestParam int quantity,
                                   @RequestParam double price,
                                   @RequestParam String designDescription,
                                   @RequestParam double designFee) {
        String orderId = "CUS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        CustomCakeOrder order = new CustomCakeOrder(orderId, customerName, cakeType, quantity, price, designDescription, designFee);
        FileHandler.saveToFile(FILE, order.toFileString());
        return "redirect:/order-list.html?success=true";
    }
}
