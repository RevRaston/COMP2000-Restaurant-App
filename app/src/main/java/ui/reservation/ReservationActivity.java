package com.example.comp2000restaurantapp.ui.reservation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.reservation.Reservation;
import com.example.comp2000restaurantapp.domain.reservation.ReservationStore;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class ReservationActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etDate;
    private EditText etTime;
    private EditText etPartySize;
    private EditText etNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        etPartySize = findViewById(R.id.etPartySize);
        etNotes = findViewById(R.id.etNotes);
        MaterialButton btnConfirm = findViewById(R.id.btnConfirm);

        toolbar.setNavigationOnClickListener(v -> finish());

        etDate.setOnClickListener(v -> showDatePicker());
        etTime.setOnClickListener(v -> showTimePicker());

        btnConfirm.setOnClickListener(v -> confirmReservation());
    }

    private void showDatePicker() {
        Calendar today = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) ->
                        etDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year),
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
        );

        // Prevent past dates
        dialog.getDatePicker().setMinDate(today.getTimeInMillis());

        dialog.show();
    }

    private void showTimePicker() {
        Calendar now = Calendar.getInstance();

        TimePickerDialog dialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    String formatted = String.format("%02d:%02d", hourOfDay, minute);
                    etTime.setText(formatted);
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );

        dialog.show();
    }

    private void confirmReservation() {
        String name = etName.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();
        String partyStr = etPartySize.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        if (TextUtils.isEmpty(date)) {
            etDate.setError("Please choose a date");
            etDate.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(time)) {
            etTime.setError("Please choose a time");
            etTime.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(partyStr)) {
            etPartySize.setError("Enter party size");
            etPartySize.requestFocus();
            return;
        }

        int partySize;
        try {
            partySize = Integer.parseInt(partyStr);
        } catch (NumberFormatException e) {
            etPartySize.setError("Invalid number");
            etPartySize.requestFocus();
            return;
        }

        if (partySize <= 0) {
            etPartySize.setError("Party size must be at least 1");
            etPartySize.requestFocus();
            return;
        }

        if (partySize > 12) { // arbitrary cap
            etPartySize.setError("Please call for groups over 12");
            etPartySize.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(name)) {
            name = "Guest";
        }

        long id = System.currentTimeMillis();

        Reservation reservation = new Reservation(
                id,
                name,
                date,
                time,
                partySize,
                notes
        );

        ReservationStore.saveReservation(this, reservation);

        Toast.makeText(this, "Reservation saved!", Toast.LENGTH_SHORT).show();

        // For now, just close and go back
        finish();
    }
}
