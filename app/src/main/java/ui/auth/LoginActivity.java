package ui.auth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}

AuthManager.login(user);

if (AuthManager.isStaff()) {
startActivity(new Intent(this, StaffHomeActivity.class));
        } else {
startActivity(new Intent(this, GuestHomeActivity.class));
        }

finish();
