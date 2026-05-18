package com.example.bakeryproject.product;


public class CustomCake extends Cake {
    private double designCharge = 500.00;

    public CustomCake() {
    }

    public CustomCake(String cakeId, String cakeName, String category, double price, int quantity) {
        super(cakeId, cakeName, category, price, quantity);
    }

    @Override
    public double calculatePrice() {
        return getPrice() + designCharge;
    }

    public double getDesignCharge() {
        return designCharge;
    }

    public void setDesignCharge(double designCharge) {
        this.designCharge = designCharge;
    }
}
