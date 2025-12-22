package com.example.comp2000restaurantapp.domain.auth;

import com.example.comp2000restaurantapp.domain.model.User;

public class AuthManager {

    private static User currentUser;

    public static void login(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isStaff() {
        return currentUser != null && "staff".equalsIgnoreCase(currentUser.getRole());
    }

    public static boolean isGuest() {
        return currentUser != null && "guest".equalsIgnoreCase(currentUser.getRole());
    }
}
