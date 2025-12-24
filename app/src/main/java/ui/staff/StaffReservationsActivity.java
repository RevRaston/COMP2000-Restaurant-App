package com.example.comp2000restaurantapp.ui.staff;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.reservation.Reservation;
import com.example.comp2000restaurantapp.domain.reservation.ReservationStore;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Locale;

public class StaffReservationsActivity extends AppCompatActivity {

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_reservations);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        container = findViewById(R.id.reservationContainer);

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
            empty.setText("No reservations found.");
            empty.setPadding(0, 32, 0, 0);
            container.addView(empty);
            return;
        }

        for (Reservation r : reservations) {
            View card = getLayoutInflater()
                    .inflate(R.layout.item_staff_reservation, container, false);

            TextView tvTitle = card.findViewById(R.id.tvTitle);
            TextView tvSubtitle = card.findViewById(R.id.tvSubtitle);
            MaterialButton btnDelete = card.findViewById(R.id.btnDelete);

            tvTitle.setText(
                    String.format(Locale.UK,
                            "%s – %s (%d)",
                            r.getGuestName(),
                            r.getTime(),
                            r.getPartySize())
            );

            tvSubtitle.setText("Date: " + r.getDate());

            btnDelete.setOnClickListener(v -> {
                ReservationStore.deleteReservation(this, r.getId());
                loadReservations();
            });

            container.addView(card);
        }
    }
}
