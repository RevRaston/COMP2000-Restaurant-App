package com.example.comp2000restaurantapp.ui.settings;

import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.notifications.NotificationPrefs;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch notifSwitch = findViewById(R.id.switchNotifications);

        notifSwitch.setChecked(NotificationPrefs.isEnabled(this));

        notifSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                NotificationPrefs.setEnabled(this, isChecked)
        );
    }
}
