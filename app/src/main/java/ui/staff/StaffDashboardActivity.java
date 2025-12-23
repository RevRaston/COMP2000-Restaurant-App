package com.example.comp2000restaurantapp.ui.staff;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.comp2000restaurantapp.R;

public class StaffDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
