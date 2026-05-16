package com.example.bakeryproject.product;


//inheritance
public class ReadyMadeCake extends Cake {

    //default constructor
    public ReadyMadeCake() {
    }

    //parameterize constructor
    public ReadyMadeCake(String cakeId, String cakeName, String category,
                         double price, int quantity) {
        super(cakeId, cakeName, category, price, quantity);
    }

    //overriding
    @Override
    public double calculatePrice() {
        return getPrice();
    }
}


