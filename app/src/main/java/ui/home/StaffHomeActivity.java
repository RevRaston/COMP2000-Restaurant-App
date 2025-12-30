package com.example.comp2000restaurantapp.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.auth.AuthManager;
import com.example.comp2000restaurantapp.ui.auth.LoginActivity;
import com.example.comp2000restaurantapp.ui.menu.StaffMenuEditorActivity;
import com.example.comp2000restaurantapp.ui.settings.SettingsActivity;
import com.example.comp2000restaurantapp.ui.staff.StaffDashboardActivity;
import com.example.comp2000restaurantapp.ui.staff.StaffReservationsActivity;

public class StaffHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔐 Staff-only access guard
        if (!AuthManager.isLoggedIn() || !AuthManager.isStaff()) {
            finish();
            return;
        }

        setContentView(R.layout.activity_staff_home);

        // 📊 Dashboard
        findViewById(R.id.btnDashboard)
                .setOnClickListener(v ->
                        startActivity(new Intent(this, StaffDashboardActivity.class)));

        // 🍽️ STAFF menu editor (FIXED)
        findViewById(R.id.btnMenu)
                .setOnClickListener(v ->
                        startActivity(new Intent(this, StaffMenuEditorActivity.class)));

        // 📅 STAFF reservations view (FIXED)
        findViewById(R.id.btnReservations)
                .setOnClickListener(v ->
                        startActivity(new Intent(this, StaffReservationsActivity.class)));

        // ⚙️ Settings
        findViewById(R.id.btnSettings)
                .setOnClickListener(v ->
                        startActivity(new Intent(this, SettingsActivity.class)));

        // 🚪 Logout
        findViewById(R.id.btnLogout)
                .setOnClickListener(v -> {
                    AuthManager.logout();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                });
    }
}
