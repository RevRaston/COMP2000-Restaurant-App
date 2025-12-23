package com.example.comp2000restaurantapp.ui.reservation;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.comp2000restaurantapp.R;

public class ReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
