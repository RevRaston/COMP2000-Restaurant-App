package com.example.comp2000restaurantapp.domain.reservation;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreReservationSync {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void syncLocalToFirestore(Context context) {
        List<Reservation> reservations = ReservationStore.getReservations(context);

        for (Reservation r : reservations) {
            Map<String, Object> data = new HashMap<>();
            data.put("guestName", r.getGuestName());
            data.put("date", r.getDate());
            data.put("time", r.getTime());
            data.put("partySize", r.getPartySize());
            data.put("notes", r.getNotes());
            data.put("completed", r.isCompleted());

            db.collection("reservations")
                    .document(String.valueOf(r.getId()))
                    .set(data);
        }
    }
}
