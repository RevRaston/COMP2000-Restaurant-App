package com.example.comp2000restaurantapp.ui.auth;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.ui.home.GuestHomeActivity;

public class GuestSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_splash);

        // Delay then move to Guest Home
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(this, GuestHomeActivity.class));
            finish();
        }, 1500); // 1.5 seconds
    }
}
