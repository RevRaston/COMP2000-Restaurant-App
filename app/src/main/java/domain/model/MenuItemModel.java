package com.example.comp2000restaurantapp.domain.model;

public class MenuItemModel {
    public String id;
    public String name;
    public double price;
    public String category;
    public boolean available;

    public MenuItemModel() { }

    public MenuItemModel(String id, String name, double price, String category, boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
    }
}
