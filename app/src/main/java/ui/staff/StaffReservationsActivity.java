package com.example.comp2000restaurantapp.ui.staff;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.reservation.Reservation;
import com.example.comp2000restaurantapp.domain.reservation.ReservationStore;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class StaffReservationsActivity extends AppCompatActivity {

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        container = findViewById(R.id.reservationContainer);

        toolbar.setTitle("All Reservations");
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadReservations();
    }

    private void loadReservations() {
        container.removeAllViews();

        List<Reservation> reservations =
                ReservationStore.getReservations(this);

        if (reservations.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("No reservations yet.");
            empty.setPadding(32, 32, 32, 32);
            container.addView(empty);
            return;
        }

        for (Reservation r : reservations) {
            TextView tv = new TextView(this);
            tv.setPadding(16, 16, 16, 16);
            tv.setText(
                    r.getGuestName() + " — " +
                            r.getDate() + " " + r.getTime() +
                            " (" + r.getPartySize() + ")"
            );
            container.addView(tv);
        }
    }
}
