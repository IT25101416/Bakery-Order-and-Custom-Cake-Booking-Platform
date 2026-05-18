package com.example.bakeryproject.product;


public class ReadyMadeCake extends Cake {

    public ReadyMadeCake() {
    }

    public ReadyMadeCake(String cakeId, String cakeName, String category, double price, int quantity) {
        super(cakeId, cakeName, category, price, quantity);
    }

    @Override
    public double calculatePrice() {
        return getPrice();
    }
}
