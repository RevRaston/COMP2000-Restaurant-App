package com.example.comp2000restaurantapp.domain.notifications;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationPrefs {

    private static final String PREFS_NAME = "notification_prefs";
    private static final String KEY_ENABLED = "notifications_enabled";

    public static boolean isEnabled(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_ENABLED, true); // default ON
    }

    public static void setEnabled(Context context, boolean enabled) {
        SharedPreferences prefs =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_ENABLED, enabled).apply();
    }
}

