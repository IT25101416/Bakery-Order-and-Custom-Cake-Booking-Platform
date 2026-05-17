package com.example.bakeryproject.order;

public class OnlineOrderProcessor extends OrderProcessor{
    @Override
    public double calculateTotal(double price, int quantity) {
        return price * quantity;
    }
}
