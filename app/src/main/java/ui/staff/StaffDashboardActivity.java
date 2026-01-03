package com.example.comp2000restaurantapp.ui.staff;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.comp2000restaurantapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class StaffDashboardActivity extends AppCompatActivity {

    private TextView tvReservationAlert;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);

        tvReservationAlert = findViewById(R.id.tvReservationAlert);
        db = FirebaseFirestore.getInstance();

        listenForReservationAlerts();
    }

    private void listenForReservationAlerts() {
        db.collection("reservations")
                .whereEqualTo("completed", false)
                .addSnapshotListener((snapshots, e) -> {
                    if (snapshots == null) return;

                    int count = snapshots.size();

                    if (count == 0) {
                        tvReservationAlert.setText("No pending reservations");
                        tvReservationAlert.setTextColor(
                                ContextCompat.getColor(
                                        this,
                                        android.R.color.darker_gray
                                )
                        );
                    } else {
                        tvReservationAlert.setText(
                                "⚠ " + count + " pending reservation(s)"
                        );
                        tvReservationAlert.setTextColor(
                                ContextCompat.getColor(
                                        this,
                                        android.R.color.holo_red_dark
                                )
                        );
                    }
                });
    }
}
