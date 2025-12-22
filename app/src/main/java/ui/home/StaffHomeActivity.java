package com.example.comp2000restaurantapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.auth.AuthManager;
import com.example.comp2000restaurantapp.ui.auth.LoginActivity;

import com.example.comp2000restaurantapp.domain.notifications.NotificationHelper;

public class StaffHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔒 Guard: only staff allowed
        if (!AuthManager.isLoggedIn() || !AuthManager.isStaff()) {
            finish();
            return;
        }

        setContentView(R.layout.activity_staff_home);

        Button logoutBtn = findViewById(R.id.btnLogout);
        logoutBtn.setOnClickListener(v -> {
            AuthManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        NotificationHelper.showNotification(
                this,
                "Staff Dashboard",
                "You are logged in as staff"
        );

    }
}
