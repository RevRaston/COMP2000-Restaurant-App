package com.example.comp2000restaurantapp.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.auth.AuthManager;
import com.example.comp2000restaurantapp.ui.auth.LoginActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        SwitchMaterial switchNotifications = findViewById(R.id.switchNotifications);
        SwitchMaterial switchDarkMode = findViewById(R.id.switchDarkMode);

        toolbar.setNavigationOnClickListener(v -> finish());

        switchNotifications.setOnCheckedChangeListener((b, checked) ->
                Toast.makeText(
                        this,
                        checked ? "Notifications enabled" : "Notifications disabled",
                        Toast.LENGTH_SHORT
                ).show()
        );

        switchDarkMode.setOnCheckedChangeListener((b, checked) ->
                Toast.makeText(
                        this,
                        "Dark mode is a placeholder feature",
                        Toast.LENGTH_SHORT
                ).show()
        );

        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            AuthManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finishAffinity();
        });
    }
}
