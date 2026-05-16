package com.example.bakeryproject.product;

// INHERITANCE: CustomCake inherits from the base Cake class
public class CustomCake extends Cake {

    // ENCAPSULATION: Private variables to protect the data
    private String cakeTheme;
    private String customMessage;
    private int layers;
    private String pickupDate;
    private String instructions;
    private double customDesignFee;

    // Constructor
    public CustomCake(String cakeTheme, String flavor, double basePrice, String customMessage, int layers, String pickupDate, String instructions) {
        super(cakeTheme, flavor, basePrice); // Passes theme, flavor, and base price to the parent Cake class
        this.cakeTheme = cakeTheme;
        this.customMessage = customMessage;
        this.layers = layers;
        this.pickupDate = pickupDate;
        this.instructions = instructions;
        this.customDesignFee = 25.00; // Flat fee for custom design
    }

    // POLYMORPHISM: Overriding the parent's calculateTotal method for dynamic custom pricing
    @Override
    public double calculateTotal() {
        // Base Price + Design Fee + ($15 for every extra layer beyond the first)
        double layerFee = (this.layers - 1) * 15.00;
        return getBasePrice() + customDesignFee + layerFee;
    }

    // Getters and Setters
    public String getCakeTheme() { return cakeTheme; }
    public void setCakeTheme(String cakeTheme) { this.cakeTheme = cakeTheme; }

    public String getCustomMessage() { return customMessage; }
    public void setCustomMessage(String customMessage) { this.customMessage = customMessage; }

    public int getLayers() { return layers; }
    public void setLayers(int layers) { this.layers = layers; }

    public String getPickupDate() { return pickupDate; }
    public void setPickupDate(String pickupDate) { this.pickupDate = pickupDate; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}