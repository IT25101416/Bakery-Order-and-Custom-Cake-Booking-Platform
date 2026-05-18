package com.example.bakeryproject.review;

public class Review {
    private String reviewId;
    private String customerName;
    private String cakeName;
    private int rating;
    private String comment;
    public Review() {
    }
    public Review(String reviewId, String customerName, String cakeName,
                  int rating, String comment) {
        this.reviewId = reviewId;
        this.customerName = customerName;
        this.cakeName = cakeName;
        this.rating = rating;
        this.comment = comment;
    }
    public String displayReview() {
        if (rating >= 4) {
            return "Excellent Review: " + comment;
        } else {
            return "Customer Review: " + comment;
        }
    }
    public String toFileString() {
        return clean(reviewId) + "," + clean(customerName) + "," +
                clean(cakeName) + "," + rating + "," + clean(comment);
    }
    public static Review fromFileString(String line) {
        String[] data = line.split(",", 5);
        if (data.length < 5) {
            return new Review("", "", "", 0, "");
        }
        int safeRating = 0;
        try {
            safeRating = Integer.parseInt(data[3].trim());
        } catch (NumberFormatException e) {
            safeRating = 0;
        }
        if (safeRating < 1) {
            safeRating = 1;
        } else if (safeRating > 5) {
            safeRating = 5;
        }
        return new Review(data[0], data[1], data[2], safeRating, data[4]);
    }private String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.replace(",", " ");
    }
    public String getReviewId() {
        return reviewId;
    }
    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCakeName() {
        return cakeName;
    }
    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }
    public int getRating() {
        return rating;
    }
    public String getStars() {
        int safeRating = rating;
        if (safeRating < 1) {
            safeRating = 1;
        } else if (safeRating > 5) {
            safeRating = 5;
        }
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < safeRating; i++) {
            stars.append("★");
        }
        return stars.toString();
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;}
}