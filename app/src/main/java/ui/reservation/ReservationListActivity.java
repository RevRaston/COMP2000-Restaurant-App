package com.example.comp2000restaurantapp.ui.reservation;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.reservation.Reservation;
import com.example.comp2000restaurantapp.domain.reservation.ReservationStore;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;
import java.util.Locale;

public class ReservationListActivity extends AppCompatActivity {

    private LinearLayout reservationContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        reservationContainer = findViewById(R.id.reservationContainer);

        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadReservations();
    }

    private void loadReservations() {
        reservationContainer.removeAllViews();

        List<Reservation> reservations = ReservationStore.getReservations(this);

        if (reservations.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("You have no reservations yet.");
            empty.setPadding(0, 32, 0, 0);
            reservationContainer.addView(empty);
            return;
        }

        for (Reservation r : reservations) {
            View item = getLayoutInflater()
                    .inflate(R.layout.item_reservation, reservationContainer, false);

            TextView tvTitle = item.findViewById(R.id.tvTitle);
            TextView tvSubtitle = item.findViewById(R.id.tvSubtitle);
            TextView tvNotes = item.findViewById(R.id.tvNotes);

            tvTitle.setText(
                    String.format(Locale.UK, "%s – %s (%d people)",
                            r.getGuestName(), r.getTime(), r.getPartySize())
            );
            tvSubtitle.setText(
                    String.format(Locale.UK, "Date: %s", r.getDate())
            );

            if (r.getNotes() == null || r.getNotes().trim().isEmpty()) {
                tvNotes.setVisibility(View.GONE);
            } else {
                tvNotes.setText("Notes: " + r.getNotes());
                tvNotes.setVisibility(View.VISIBLE);
            }

            reservationContainer.addView(item);
        }
    }
}
