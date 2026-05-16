package com.example.bakeryproject;

public class Customer {
    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;

    public Customer() { }

    public Customer(String id, String name, String email, String password, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public double getDiscount() {
        return 0.0;
    }

    public String toFileString() {
        return id + "," + name + "," + email + "," + password + "," + phone;
    }

    public static Customer fromFileString(String line) {
        String[] data = line.split(",", -1);
        String id = data.length > 0 ? data[0] : "";
        String name = data.length > 1 ? data[1] : "";
        String email = data.length > 2 ? data[2] : "";
        String password = data.length > 3 ? data[3] : "";
        String phone = data.length > 4 ? data[4] : "";
        return new Customer(id, name, email, password, phone);
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}