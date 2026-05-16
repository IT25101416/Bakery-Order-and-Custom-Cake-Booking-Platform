package com.example.bakeryproject.product;

public class Savoury {
    private String savouryId;
    private String savouryName;
    private String category;
    private double price;
    private int quantity;

    public Savoury() {
    }

    public Savoury(String savouryId, String savouryName, String category, double price, int quantity) {
        this.savouryId = savouryId;
        this.savouryName = savouryName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public String toFileString() {
        return savouryId + "," + savouryName + "," + category + "," + price + "," + quantity;
    }

    public static Savoury fromFileString(String line) {
        String[] data = line.split(",", -1);
        return new Savoury(
                data.length > 0 ? data[0] : "",
                data.length > 1 ? data[1] : "",
                data.length > 2 ? data[2] : "",
                data.length > 3 && !data[3].isEmpty() ? Double.parseDouble(data[3]) : 0.0,
                data.length > 4 && !data[4].isEmpty() ? Integer.parseInt(data[4]) : 0
        );
    }

    public String getSavouryId() { return savouryId; }
    public void setSavouryId(String savouryId) { this.savouryId = savouryId; }

    public String getSavouryName() { return savouryName; }
    public void setSavouryName(String savouryName) { this.savouryName = savouryName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
