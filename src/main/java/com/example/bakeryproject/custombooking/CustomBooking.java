package com.example.bakeryproject.custombooking;

public class CustomBooking {
    private String bookingId;
    private String customerName;
    private String cakeSize;
    private String cakeFlavor;
    private String designDescription;
    private String date;
    private String status;

    public CustomBooking() {
    }

    public CustomBooking(String bookingId, String customerName, String cakeSize, String cakeFlavor,
                         String designDescription, String date, String status) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.cakeSize = cakeSize;
        this.cakeFlavor = cakeFlavor;
        this.designDescription = designDescription;
        this.date = date;
        this.status = status;
    }

    public String toFileString() {
        return clean(bookingId) + "," + clean(customerName) + "," + clean(cakeSize) + "," + clean(cakeFlavor) + "," +
                clean(designDescription) + "," + clean(date) + "," + clean(status);
    }

    private static String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.replace(",", " ").replace("\n", " ").replace("\r", " ").trim();
    }

    public static CustomBooking fromFileString(String line) {
        String[] data = line.split(",", -1);

        if (data.length < 7) {
            String[] safeData = new String[7];
            for (int i = 0; i < safeData.length; i++) {
                safeData[i] = i < data.length ? data[i] : "";
            }
            return new CustomBooking(safeData[0], safeData[1], safeData[2], safeData[3], safeData[4], safeData[5], safeData[6]);
        }

        if (data.length > 7) {
            StringBuilder description = new StringBuilder(data[4]);
            for (int i = 5; i < data.length - 2; i++) {
                description.append(" ").append(data[i]);
            }
            return new CustomBooking(data[0], data[1], data[2], data[3], description.toString(), data[data.length - 2], data[data.length - 1]);
        }

        return new CustomBooking(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCakeSize() {
        return cakeSize;
    }

    public void setCakeSize(String cakeSize) {
        this.cakeSize = cakeSize;
    }

    public String getCakeFlavor() {
        return cakeFlavor;
    }

    public void setCakeFlavor(String cakeFlavor) {
        this.cakeFlavor = cakeFlavor;
    }

    public String getDesignDescription() {
        return designDescription;
    }

    public void setDesignDescription(String designDescription) {
        this.designDescription = designDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
