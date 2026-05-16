package com.example.bakeryproject.C3_Order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
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

    // READ - All orders
    @GetMapping("/get-orders")
    @ResponseBody
    public List<String> getAllOrders() {
        return FileHandler.readFromFile(FILE);
    }

    // READ - Search by customer name
    @GetMapping("/search-orders")
    @ResponseBody
    public List<String> searchOrders(@RequestParam String name) {
        List<String> all    = FileHandler.readFromFile(FILE);
        List<String> result = new ArrayList<>();
        for (String line : all) {
            String[] p = line.split(",");
            if (p.length > 1 && p[1].toLowerCase().contains(name.toLowerCase())) result.add(line);
        }
        return result;
    }

    // UPDATE - Change order status
    @PostMapping("/update-order-status")
    public String updateStatus(@RequestParam String orderId,
                               @RequestParam String newStatus) {
        List<String> all     = FileHandler.readFromFile(FILE);
        List<String> updated = new ArrayList<>();
        for (String line : all) {
            String[] p = line.split(",");
            if (p.length >= 7 && p[0].equals(orderId)) {
                p[6] = newStatus;
                updated.add(String.join(",", p));
            } else { updated.add(line); }
        }
        FileHandler.overwriteFile(FILE, updated);
        return "redirect:/order-list.html?updated=true";
    }

    // DELETE - Cancel order
    @PostMapping("/delete-order")
    public String deleteOrder(@RequestParam String orderId) {
        FileHandler.deleteLineById(FILE, orderId);
        return "redirect:/order-list.html?deleted=true";
    }
}
