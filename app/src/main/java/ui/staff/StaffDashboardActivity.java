package com.example.comp2000restaurantapp.ui.staff;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class StaffDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        MaterialButton btnManageReservations =
                findViewById(R.id.btnManageReservations);

        // ✅ Safe ONLY if toolbar exists in XML
        toolbar.setNavigationOnClickListener(v -> finish());

        btnManageReservations.setOnClickListener(v ->
                startActivity(
                        new Intent(this, StaffReservationsActivity.class)
                )
        );
    }
}
