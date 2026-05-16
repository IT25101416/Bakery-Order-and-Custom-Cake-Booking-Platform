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

    // Getters & Setters
    public String getOrderId()                     { return orderId; }
    public void   setOrderId(String orderId)       { this.orderId = orderId; }
    public String getCustomerName()                { return customerName; }
    public void   setCustomerName(String n)        { this.customerName = n; }
    public String getCakeType()                    { return cakeType; }
    public void   setCakeType(String c)            { this.cakeType = c; }
    public int    getQuantity()                    { return quantity; }
    public void   setQuantity(int q)               { this.quantity = q; this.totalPrice = calculateTotal(); }
    public double getPricePerUnit()                { return pricePerUnit; }
    public void   setPricePerUnit(double p)        { this.pricePerUnit = p; this.totalPrice = calculateTotal(); }
    public double getTotalPrice()                  { return totalPrice; }
    public void   setTotalPrice(double t)          { this.totalPrice = t; }
    public String getStatus()                      { return status; }
    public void   setStatus(String status)         { this.status = status; }
}

