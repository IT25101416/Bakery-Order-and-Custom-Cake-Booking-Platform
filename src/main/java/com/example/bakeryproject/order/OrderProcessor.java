package com.example.bakeryproject.order;

/**
 * Abstract class that defines the contract for order processing.
 * Any class that processes orders must extend this class
 * and provide an implementation for calculateTotal().
 */
public abstract class OrderProcessor {
    public abstract double calculateTotal(double price, int quantity);
}
