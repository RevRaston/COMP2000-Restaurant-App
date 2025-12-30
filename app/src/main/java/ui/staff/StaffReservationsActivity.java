package com.example.comp2000restaurantapp.ui.staff;

import android.app.AlertDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.reservation.Reservation;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class StaffReservationsActivity extends AppCompatActivity {

    private LinearLayout container;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_reservations);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        container = findViewById(R.id.reservationContainer);
        db = FirebaseFirestore.getInstance();

        toolbar.setNavigationOnClickListener(v -> finish());

        // ✅ Staff action menu
        toolbar.inflateMenu(R.menu.menu_staff_reservations);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_add_reservation) {
                showAddReservationDialog();
                return true;
            }
            return false;
        });

        startLiveUpdates();
    }

    private void startLiveUpdates() {
        db.collection("reservations")
                .addSnapshotListener((snapshots, e) -> {
                    if (snapshots == null) return;

                    container.removeAllViews();

                    if (snapshots.isEmpty()) {
                        TextView empty = new TextView(this);
                        empty.setText("No reservations found.");
                        empty.setPadding(0, 32, 0, 0);
                        container.addView(empty);
                        return;
                    }

                    for (DocumentSnapshot doc : snapshots) {

                        Long rid = doc.getLong("reservationId");
                        long safeId = (rid != null) ? rid : 0L;

                        Reservation r = new Reservation(
                                safeId,
                                doc.getString("guestName"),
                                doc.getString("date"),
                                doc.getString("time"),
                                doc.getLong("partySize") != null
                                        ? doc.getLong("partySize").intValue()
                                        : 0,
                                doc.getString("notes"),
                                Boolean.TRUE.equals(doc.getBoolean("completed"))
                        );



                        View card = getLayoutInflater()
                                .inflate(R.layout.item_staff_reservation, container, false);

                        TextView tvTitle = card.findViewById(R.id.tvTitle);
                        TextView tvSubtitle = card.findViewById(R.id.tvSubtitle);
                        TextView badge = card.findViewById(R.id.tvCompletedBadge);

                        tvTitle.setText(String.format(
                                Locale.UK,
                                "%s – %s (%d)",
                                r.getGuestName(),
                                r.getTime(),
                                r.getPartySize()
                        ));

                        tvSubtitle.setText("Date: " + r.getDate());

                        if (r.isCompleted()) {
                            tvTitle.setPaintFlags(
                                    tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
                            );
                            tvTitle.setAlpha(0.4f);
                            tvSubtitle.setAlpha(0.4f);
                            badge.setVisibility(View.VISIBLE);
                        }

                        card.findViewById(R.id.btnComplete)
                                .setOnClickListener(v ->
                                        db.collection("reservations")
                                                .document(doc.getId())
                                                .update("completed", true)
                                );

                        card.findViewById(R.id.btnDelete)
                                .setOnClickListener(v ->
                                        confirmDelete(doc.getId())
                                );

                        container.addView(card);
                    }
                });
    }

    private void showAddReservationDialog() {
        Map<String, Object> data = new HashMap<>();
        data.put("guestName", "Walk-in Guest");
        data.put("date", "2025-01-01");
        data.put("time", "18:00");
        data.put("partySize", 2);
        data.put("notes", "Added by staff");
        data.put("completed", false);

        db.collection("reservations")
                .document(String.valueOf(System.currentTimeMillis()))
                .set(data);
    }

    private void confirmDelete(String docId) {
        new AlertDialog.Builder(this)
                .setTitle("Delete reservation")
                .setMessage("Are you sure you want to delete this reservation?")
                .setPositiveButton("Delete", (d, w) ->
                        db.collection("reservations")
                                .document(docId)
                                .delete()
                )
                .setNegativeButton("Cancel", null)
                .show();
    }
}
