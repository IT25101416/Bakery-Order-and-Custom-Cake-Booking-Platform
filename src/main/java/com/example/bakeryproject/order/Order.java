package com.example.bakeryproject.order;


public class Order {
    private String orderId;
    private String customerName;
    private String cakeName;
    private int quantity;
    private double totalPrice;
    private String status;

    public Order() {
    }

    public Order(String orderId, String customerName, String cakeName, int quantity, double totalPrice, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.cakeName = cakeName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public String toFileString() {
        return orderId + "," + customerName + "," + cakeName + "," + quantity + "," + totalPrice + "," + status;
    }

    public static Order fromFileString(String line) {
        String[] data = line.split(",", -1);
        return new Order(
                data.length > 0 ? data[0] : "",
                data.length > 1 ? data[1] : "",
                data.length > 2 ? data[2] : "",
                data.length > 3 && !data[3].isEmpty() ? Integer.parseInt(data[3]) : 0,
                data.length > 4 && !data[4].isEmpty() ? Double.parseDouble(data[4]) : 0.0,
                data.length > 5 ? data[5] : "Pending"
        );
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCakeName() {
        return cakeName;
    }

    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}