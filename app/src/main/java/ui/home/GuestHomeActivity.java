package com.example.comp2000restaurantapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.auth.AuthManager;
import com.example.comp2000restaurantapp.ui.auth.LoginActivity;
import com.example.comp2000restaurantapp.ui.menu.MenuActivity;
import com.example.comp2000restaurantapp.ui.reservation.ReservationActivity;
import com.example.comp2000restaurantapp.ui.settings.SettingsActivity;

public class GuestHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!AuthManager.isLoggedIn()) {
            finish();
            return;
        }

        setContentView(R.layout.activity_guest_home);

        findViewById(R.id.btnMenu)
                .setOnClickListener(v ->
                        startActivity(new Intent(this, MenuActivity.class)));

        findViewById(R.id.btnReservations)
                .setOnClickListener(v ->
                        startActivity(new Intent(this, ReservationActivity.class)));

        findViewById(R.id.btnSettings)
                .setOnClickListener(v ->
                        startActivity(new Intent(this, SettingsActivity.class)));

        findViewById(R.id.btnLogout)
                .setOnClickListener(v -> {
                    AuthManager.logout();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                });
    }
}
