package com.example.comp2000restaurantapp.domain.model;

import java.util.ArrayList;
import java.util.List;

public class MenuData {

    public enum Category {
        SPECIAL, DEAL, MEAL, DRINK, DESSERT
    }

    public static class MenuItem {
        public String name;
        public double price;
        public int imageResId;
        public Category category;

        public MenuItem(String name, double price, int imageResId, Category category) {
            this.name = name;
            this.price = price;
            this.imageResId = imageResId;
            this.category = category;
        }
    }

    public static List<MenuItem> menuItems = new ArrayList<>();

    static {
        menuItems.add(new MenuItem("Cheeseburger", 6.99, android.R.drawable.ic_menu_gallery, Category.MEAL));
        menuItems.add(new MenuItem("Caesar Salad", 5.49, android.R.drawable.ic_menu_gallery, Category.MEAL));
        menuItems.add(new MenuItem("House Special Pizza", 9.99, android.R.drawable.ic_menu_gallery, Category.SPECIAL));
        menuItems.add(new MenuItem("2-for-1 Mojitos", 7.00, android.R.drawable.ic_menu_gallery, Category.DEAL));
        menuItems.add(new MenuItem("Cola", 2.00, android.R.drawable.ic_menu_gallery, Category.DRINK));
        menuItems.add(new MenuItem("Chocolate Cake", 3.99, android.R.drawable.ic_menu_gallery, Category.DESSERT));
    }
}
