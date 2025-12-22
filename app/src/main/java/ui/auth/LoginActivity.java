package ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.auth.AuthManager;
import com.example.comp2000restaurantapp.domain.model.User;

import ui.guest.GuestHomeActivity;
import ui.staff.StaffHomeActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TEMP login (Stage 2 — no UI yet)
        User user = new User("admin", "password", "staff");
        AuthManager.login(user);

        if (AuthManager.isStaff()) {
            startActivity(new Intent(this, StaffHomeActivity.class));
        } else {
            startActivity(new Intent(this, GuestHomeActivity.class));
        }

        finish();
    }
}

