package com.example.bakeryproject.product;

public class Cake {
    private String cakeId;
    private String cakeName;
    private String category;
    private double price;
    private int quantity;

    public Cake() {
    }

    public Cake(String cakeId, String cakeName, String category, double
            price, int quantity) {
        this.cakeId = cakeId;
        this.cakeName = cakeName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public static Cake fromFileString(String line) {
        String[] data = line.split(",");
        return new Cake(
                data[0],
                data[1],
                data[2],
                Double.parseDouble(data[3]),
                Integer.parseInt(data[4])
        );
    }

    public double calculatePrice() {
        return price;
    }

    public String toFileString() {
        return cakeId + "," + cakeName + "," + category + "," + price + ","
                + quantity;
    }

    public String getCakeId() {
        return cakeId;
    }

    public void setCakeId(String cakeId) {
        this.cakeId = cakeId;
    }

    public String getCakeName() {
        return cakeName;
    }

    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

