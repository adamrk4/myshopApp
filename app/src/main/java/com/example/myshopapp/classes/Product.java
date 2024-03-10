package com.example.myshopapp.classes;

// Product.java
public class Product {
    private String id;
    private String name;
    private int amount;

    public Product(String productName, int quantity) {
        // Default constructor required for Firebase
    }

    public Product(String id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

