package com.example.comp2000restaurantapp.domain.auth;

import android.app.Activity;
import android.content.Intent;

import com.example.comp2000restaurantapp.ui.auth.LoginActivity;
import com.example.comp2000restaurantapp.ui.home.GuestHomeActivity;
import com.example.comp2000restaurantapp.ui.home.StaffHomeActivity;

public class SessionGuard {

    public static void routeUser(Activity activity) {

        if (!AuthManager.isLoggedIn()) {
            activity.startActivity(new Intent(activity, LoginActivity.class));
            activity.finish();
            return;
        }

        if (AuthManager.isStaff()) {
            activity.startActivity(new Intent(activity, StaffHomeActivity.class));
        } else {
            activity.startActivity(new Intent(activity, GuestHomeActivity.class));
        }

        activity.finish();
    }
}
