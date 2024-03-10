package com.example.myshopapp.classes;

public class MyData {
    public static String[] groceryItems = {"Apple", "Banana", "Orange", "Milk", "Bread", "Eggs", "Chicken", "Rice", "Potato", "Tomato", "Onion"};
    public static String[] ids = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    public static Integer[] amounts ={0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static Product[] getProducts() {
        Product[] products = new Product[groceryItems.length];
        for (int i = 0; i < groceryItems.length; i++) {
            // Initialize Product with correct name
            products[i] = new Product(groceryItems[i], String.valueOf(ids[i]), 0);
        }
        return products;
    }
}


