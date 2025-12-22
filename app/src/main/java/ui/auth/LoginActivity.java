package com.example.comp2000restaurantapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.auth.AuthManager;
import com.example.comp2000restaurantapp.domain.model.User;
import com.example.comp2000restaurantapp.ui.home.GuestHomeActivity;
import com.example.comp2000restaurantapp.ui.home.StaffHomeActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btnLoginGuest).setOnClickListener(v -> {
            AuthManager.login(new User("guest", "", "guest"));
            startActivity(new Intent(this, GuestHomeActivity.class));
            finish();
        });

        findViewById(R.id.btnLoginStaff).setOnClickListener(v -> {
            AuthManager.login(new User("admin", "password", "staff"));
            startActivity(new Intent(this, StaffHomeActivity.class));
            finish();
        });
    }
}
