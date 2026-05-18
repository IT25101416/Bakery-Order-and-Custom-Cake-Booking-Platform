package com.example.bakeryproject.order;

/**
 * Concrete implementation of OrderProcessor for online orders.
 * Calculates the total price by multiplying unit price by quantity.
 * Follows the Open/Closed Principle — new pricing strategies
 * can be added by creating new subclasses without modifying this class.
 */
public class OnlineOrderProcessor extends OrderProcessor{

    @Override
    public double calculateTotal(double price, int quantity) {

        return price * quantity;
    }
}