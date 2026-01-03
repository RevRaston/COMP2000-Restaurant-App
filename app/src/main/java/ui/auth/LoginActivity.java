package com.example.comp2000restaurantapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.auth.AuthManager;
import com.example.comp2000restaurantapp.domain.model.User;
import com.example.comp2000restaurantapp.ui.home.StaffHomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.example.comp2000restaurantapp.ui.auth.GuestSplashActivity;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        findViewById(R.id.btnLogin).setOnClickListener(v -> login());
        findViewById(R.id.btnCreateAccount).setOnClickListener(v -> createAccount());
        findViewById(R.id.btnForgotPassword).setOnClickListener(v -> resetPassword());

        // ✅ QUICK GUEST LOGIN (NOW USES SPLASH)
        findViewById(R.id.btnLoginGuest).setOnClickListener(v -> {
            AuthManager.login(new User("guest", "", "guest"));
            startActivity(new Intent(this, GuestSplashActivity.class));
            finish();
        });

        // ✅ QUICK STAFF LOGIN (UNCHANGED)
        findViewById(R.id.btnLoginStaff).setOnClickListener(v -> {
            AuthManager.login(new User("admin", "", "staff"));
            startActivity(new Intent(this, StaffHomeActivity.class));
            finish();
        });
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    AuthManager.login(new User(email, "", "guest"));
                    startActivity(new Intent(this, GuestSplashActivity.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void createAccount() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || password.length() < 6) {
            Toast.makeText(this, "Enter valid email and password (6+ chars)", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    AuthManager.login(new User(email, "", "guest"));
                    Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, GuestSplashActivity.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void resetPassword() {
        String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter your email first", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(v ->
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
