package com.example.comp2000restaurantapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comp2000restaurantapp.data.api.ApiService;
import com.example.comp2000restaurantapp.data.repository.UserRepository;
import com.example.comp2000restaurantapp.data.api.RetrofitClient;

public class MainActivity extends AppCompatActivity {

    // Infrastructure objects (safe to create here for now)
    private ApiService apiService;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Window insets (default Android Studio template)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );
            return insets;
        });

        // ✅ Initialise Retrofit + Repository AFTER onCreate starts
        apiService = RetrofitClient.getApiService();
        userRepository = new UserRepository(apiService, "student123");

        // 🚫 No API calls yet — this is intentional
        // Stage 1.2 will add controlled test calls
    }
}
