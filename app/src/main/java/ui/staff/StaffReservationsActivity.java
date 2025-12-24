package com.example.comp2000restaurantapp.ui.staff;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.reservation.Reservation;
import com.example.comp2000restaurantapp.domain.reservation.ReservationStore;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
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

        sortReservations(reservations);

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

            tvTitle.setText(
                    r.getGuestName() + " – " + r.getTime() +
                            " (" + r.getPartySize() + ")"
            );

            tvSubtitle.setText("Date: " + r.getDate());

            // Completed styling
            if (r.isCompleted()) {
                tvTitle.setAlpha(0.4f);
                tvSubtitle.setAlpha(0.4f);
            }

            card.findViewById(R.id.btnComplete).setOnClickListener(v -> {
                r.setCompleted(true);
                ReservationStore.saveReservation(this, r);
                loadReservations();
            });

            card.findViewById(R.id.btnDelete).setOnClickListener(v ->
                    confirmDelete(r.getId())
            );

            container.addView(card);
        }
    }

    private void confirmDelete(long id) {
        new AlertDialog.Builder(this)
                .setTitle("Delete reservation")
                .setMessage("Are you sure you want to delete this reservation?")
                .setPositiveButton("Delete", (d, w) -> {
                    ReservationStore.deleteReservation(this, id);
                    loadReservations();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void sortReservations(List<Reservation> list) {
        SimpleDateFormat df =
                new SimpleDateFormat("d/M/yyyy HH:mm", Locale.UK);

        Collections.sort(list, (a, b) -> {
            try {
                return df.parse(a.getDate() + " " + a.getTime())
                        .compareTo(df.parse(b.getDate() + " " + b.getTime()));
            } catch (ParseException e) {
                return 0;
            }
        });
    }
}
