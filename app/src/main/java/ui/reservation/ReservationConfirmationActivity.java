package com.example.comp2000restaurantapp.ui.reservation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.ui.home.GuestHomeActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class ReservationConfirmationActivity extends AppCompatActivity {

    private static final String EXTRA_DATE = "extra_date";
    private static final String EXTRA_TIME = "extra_time";
    private static final String EXTRA_PARTY = "extra_party";

    public static void start(Context context, String date, String time, int partySize) {
        Intent intent = new Intent(context, ReservationConfirmationActivity.class);
        intent.putExtra(EXTRA_DATE, date);
        intent.putExtra(EXTRA_TIME, time);
        intent.putExtra(EXTRA_PARTY, partySize);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_confirmation);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        TextView tvSummary = findViewById(R.id.tvSummary);
        MaterialButton btnBackHome = findViewById(R.id.btnBackHome);

        toolbar.setNavigationOnClickListener(v -> finish());

        String date = getIntent().getStringExtra(EXTRA_DATE);
        String time = getIntent().getStringExtra(EXTRA_TIME);
        int party = getIntent().getIntExtra(EXTRA_PARTY, 0);

        String summary = "Your reservation is confirmed.\n\n"
                + "Date: " + date + "\n"
                + "Time: " + time + "\n"
                + "Party size: " + party;

        tvSummary.setText(summary);

        btnBackHome.setOnClickListener(v -> {
            // For now, go back to guest home
            Intent intent = new Intent(this, GuestHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
