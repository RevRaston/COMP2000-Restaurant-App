package com.example.comp2000restaurantapp.domain.model;

public class User {

    private String username;
    private String password;
    private String role; // API-compatible

    public User() {}

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    // 👇 Application control helper
    public UserRole getUserRole() {
        if ("staff".equalsIgnoreCase(role)) {
            return UserRole.STAFF;
        }
        return UserRole.GUEST;
    }
}

