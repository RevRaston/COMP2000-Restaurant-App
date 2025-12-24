package com.example.comp2000restaurantapp.domain.reservation;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReservationStore {

    private static final String PREF_NAME = "reservations_store";
    private static final String KEY_RESERVATIONS = "reservations_json";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void saveReservation(Context context, Reservation reservation) {
        List<Reservation> existing = getReservations(context);

        // replace if exists
        boolean replaced = false;
        for (int i = 0; i < existing.size(); i++) {
            if (existing.get(i).getId() == reservation.getId()) {
                existing.set(i, reservation);
                replaced = true;
                break;
            }
        }

        if (!replaced) {
            existing.add(reservation);
        }

        persist(context, existing);
    }

    public static List<Reservation> getReservations(Context context) {
        SharedPreferences prefs = getPrefs(context);
        String json = prefs.getString(KEY_RESERVATIONS, "[]");
        List<Reservation> result = new ArrayList<>();

        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                result.add(new Reservation(
                        obj.optLong("id"),
                        obj.optString("guestName"),
                        obj.optString("date"),
                        obj.optString("time"),
                        obj.optInt("partySize"),
                        obj.optString("notes"),
                        obj.optBoolean("completed", false)
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void deleteReservation(Context context, long id) {
        List<Reservation> existing = getReservations(context);
        Iterator<Reservation> it = existing.iterator();

        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                break;
            }
        }

        persist(context, existing);
    }

    private static void persist(Context context, List<Reservation> reservations) {
        JSONArray arr = new JSONArray();

        try {
            for (Reservation r : reservations) {
                JSONObject obj = new JSONObject();
                obj.put("id", r.getId());
                obj.put("guestName", r.getGuestName());
                obj.put("date", r.getDate());
                obj.put("time", r.getTime());
                obj.put("partySize", r.getPartySize());
                obj.put("notes", r.getNotes());
                obj.put("completed", r.isCompleted());
                arr.put(obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getPrefs(context)
                .edit()
                .putString(KEY_RESERVATIONS, arr.toString())
                .apply();
    }
}
