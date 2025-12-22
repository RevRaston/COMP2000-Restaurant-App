package com.example.comp2000restaurantapp.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.auth.AuthManager;

import com.example.comp2000restaurantapp.domain.notifications.NotificationHelper;

public class GuestHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔒 Guard: must be logged in
        if (!AuthManager.isLoggedIn()) {
            finish();
            return;
        }
        NotificationHelper.showNotification(
                this,
                "Welcome",
                "Browse the menu and make a reservation"
        );


        setContentView(R.layout.activity_guest_home);
    }
}


