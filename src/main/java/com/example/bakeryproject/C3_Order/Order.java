package com.example.bakeryproject.C3_Order;

public class Order extends AbstractOrder {

    // ENCAPSULATION — all private
    private String orderId, customerName, cakeType, status;
    private int quantity;
    private double pricePerUnit, totalPrice;

    public Order(String orderId, String customerName,
                 String cakeType, int quantity, double price) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.cakeType = cakeType;
        this.quantity = quantity;
        this.pricePerUnit = price;
        this.totalPrice = quantity * price;
        this.status = "Pending";
    }

    @Override
    public double calculateTotal() {
        return quantity * pricePerUnit;
    }

    @Override
    public String getOrderSummary() {
        return orderId + " | " + customerName + " | " + cakeType
                + " | Status: " + status;
    }

    @Override
    public String getOrderType() {
        return "Standard Order";
    }

    // All getters and setters here (see full code below)


    // Save to file format
    public String toFileString() {
        return String.format("%s,%s,%s,%d,%.2f,%.2f,%s,%s",
                orderId, customerName, cakeType, quantity,
                pricePerUnit, totalPrice, status, getOrderType());
    }
}
