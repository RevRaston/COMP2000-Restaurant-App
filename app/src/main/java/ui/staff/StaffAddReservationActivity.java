package com.example.comp2000restaurantapp.ui.staff;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StaffAddReservationActivity extends AppCompatActivity {

    private EditText etName, etDate, etTime, etPartySize, etNotes;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_add_reservation);

        db = FirebaseFirestore.getInstance();

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        etPartySize = findViewById(R.id.etPartySize);
        etNotes = findViewById(R.id.etNotes);

        MaterialButton btnSave = findViewById(R.id.btnSave);

        etDate.setOnClickListener(v -> showDatePicker());
        etTime.setOnClickListener(v -> showTimePicker());

        btnSave.setOnClickListener(v -> saveReservation());
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this,
                (v, y, m, d) -> etDate.setText(d + "/" + (m + 1) + "/" + y),
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void showTimePicker() {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(this,
                (v, h, m) -> etTime.setText(String.format("%02d:%02d", h, m)),
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
        ).show();
    }

    private void saveReservation() {
        String name = etName.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();
        String partyStr = etPartySize.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(date)
                || TextUtils.isEmpty(time) || TextUtils.isEmpty(partyStr)) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int partySize = Integer.parseInt(partyStr);

        Map<String, Object> data = new HashMap<>();
        data.put("guestName", name);
        data.put("date", date);
        data.put("time", time);
        data.put("partySize", partySize);
        data.put("notes", notes);
        data.put("completed", false);
        data.put("createdByStaff", true);

        db.collection("reservations")
                .add(data)
                .addOnSuccessListener(doc -> {
                    Toast.makeText(this, "Reservation added", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }
}
