package com.example.bakeryproject.C3_Order;

/**
 * Component 03: Order Management
 *
 * INHERITANCE: CustomCakeOrder inherits from Order (which inherits from AbstractOrder).
 * This demonstrates a multi-level inheritance chain:
 *   AbstractOrder → Order → CustomCakeOrder
 *
 * POLYMORPHISM: calculateTotal(), getOrderSummary(), and getOrderType()
 * are OVERRIDDEN here to behave differently from the parent Order class.
 * A custom cake has an extra design fee on top of the base price.
 */
public class CustomCakeOrder extends Order {

    // Extra fields specific to custom cake orders (ENCAPSULATION)
    private String designDescription;
    private double designFee;

    // Constructor - calls parent constructor using super()
    public CustomCakeOrder(String orderId, String customerName, String cakeType,
                           int quantity, double pricePerUnit,
                           String designDescription, double designFee) {
        // INHERITANCE - reuse parent constructor
        super(orderId, customerName, cakeType, quantity, pricePerUnit);
        this.designDescription = designDescription;
        this.designFee         = designFee;
        // Override total to include design fee
        this.setTotalPrice(calculateTotal());
    }

    // ----------------------------------------------------------------
    // POLYMORPHISM - Override parent methods with custom behaviour
    // ----------------------------------------------------------------

    @Override
    public double calculateTotal() {
        // Custom cake total = (quantity × price) + design fee
        return (getQuantity() * getPricePerUnit()) + designFee;
    }

    @Override
    public String getOrderSummary() {
        return String.format(
                "CustomOrder[%s] | Customer: %s | Design: %s | Qty: %d | Design Fee: LKR %.2f | Total: LKR %.2f | Status: %s",
                getOrderId(), getCustomerName(), designDescription,
                getQuantity(), designFee, getTotalPrice(), getStatus());
    }

    @Override
    public String getOrderType() {
        return "Custom Cake Order";
    }

    // ENCAPSULATION - Getters and Setters for new fields
    public String getDesignDescription()                         { return designDescription; }
    public void   setDesignDescription(String designDescription) { this.designDescription = designDescription; }

    public double getDesignFee()               { return designFee; }
    public void   setDesignFee(double designFee) {
        this.designFee = designFee;
        this.setTotalPrice(calculateTotal()); // recalculate total
    }

    // Format for saving to orders.txt
    @Override
    public String toFileString() {
        return String.format("%s,%s,%s,%d,%.2f,%.2f,%s,%s,%.2f,%s",
                getOrderId(), getCustomerName(), getCakeType(), getQuantity(),
                getPricePerUnit(), getTotalPrice(), getStatus(), getOrderType(),
                designFee, designDescription.replace(",", ";"));
    }
    
}