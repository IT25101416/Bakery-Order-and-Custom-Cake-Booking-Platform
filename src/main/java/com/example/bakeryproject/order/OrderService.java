package com.example.bakeryproject.order;

import com.example.bakeryproject.common.FileUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Service class that handles all business logic related to orders.
 * Responsible for adding, retrieving, updating, and deleting orders.
 * Orders are stored and retrieved from a plain text file (orders.txt)
 * using comma-separated values (CSV) format.
 */
@Service
public class OrderService {

    private final String FILE_PATH = "src/main/resources/data/orders.txt";

    public void addOrder(Order order) {
        if (order.getOrderId() == null || order.getOrderId().trim().isEmpty() || "null".equalsIgnoreCase(order.getOrderId())) {
            order.setOrderId(generateOrderId());
        }

        if (order.getStatus() == null || order.getStatus().trim().isEmpty()) {
            order.setStatus("Pending");
        }

        FileUtil.saveLine(FILE_PATH, order.toFileString());
    }

    public List<Order> getAllOrders() {
        List<String> lines = FileUtil.readLines(FILE_PATH);
        List<Order> orders = new ArrayList<>();

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                orders.add(Order.fromFileString(line));
            }
        }

        return orders;
    }

    public List<Order> getOrdersByCustomerName(String customerName) {
        List<Order> customerOrders = new ArrayList<>();

        if (customerName == null) {
            return customerOrders;
        }

        for (Order order : getAllOrders()) {
            if (order.getCustomerName() != null && order.getCustomerName().equalsIgnoreCase(customerName.trim())) {
                customerOrders.add(order);
            }
        }

        return customerOrders;
    }

    public Order findOrderById(String id) {
        if (id == null) {
            return null;
        }

        for (Order order : getAllOrders()) {
            if (order.getOrderId() != null && order.getOrderId().equalsIgnoreCase(id.trim())) {
                return order;
            }
        }
        return null;
    }

    public void updateOrder(Order updatedOrder) {
        List<Order> orders = getAllOrders();
        List<String> updatedLines = new ArrayList<>();

        for (Order order : orders) {
            if (order.getOrderId().equals(updatedOrder.getOrderId())) {
                updatedLines.add(updatedOrder.toFileString());
            } else {
                updatedLines.add(order.toFileString());
            }
        }

        FileUtil.overwriteFile(FILE_PATH, updatedLines);
    }

    public void updateStatus(String id, String status) {
        Order order = findOrderById(id);

        if (order != null) {
            order.setStatus(status);
            updateOrder(order);
        }
    }

    public void deleteOrder(String id) {
        List<Order> orders = getAllOrders();
        List<String> updatedLines = new ArrayList<>();

        for (Order order : orders) {
            if (!order.getOrderId().equals(id)) {
                updatedLines.add(order.toFileString());
            }
        }

        FileUtil.overwriteFile(FILE_PATH, updatedLines);
    }

    private String generateOrderId() {
        return "ORD" + System.currentTimeMillis();
    }
}
