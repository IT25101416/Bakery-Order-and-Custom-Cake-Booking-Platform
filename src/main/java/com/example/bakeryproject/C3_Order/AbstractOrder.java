package com.example.bakeryproject.C3_Order;

public abstract class AbstractOrder {

    //Forces all order types to implement these methods

    public abstract double calculateTotal();
    public abstract String getOrderSummary();
    public abstract String getOrderType();
}
