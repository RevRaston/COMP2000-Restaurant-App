package com.example.comp2000restaurantapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.data.api.ApiService;
import com.example.comp2000restaurantapp.data.network.RetrofitClient;
import com.example.comp2000restaurantapp.data.repository.UserRepository;
import com.example.comp2000restaurantapp.domain.auth.AuthManager;
import com.example.comp2000restaurantapp.domain.model.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 🔹 Initialise API + Repository
        ApiService apiService = RetrofitClient.getApiService();
        UserRepository userRepository =
                new UserRepository(apiService, "student123");

        // 🔹 Smoke test: create student database
        userRepository.createStudentDatabase().enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(
                    Call<Map<String, String>> call,
                    Response<Map<String, String>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Database created: " + response.body());
                } else {
                    Log.e(TAG, "Create DB failed. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Log.e(TAG, "API call failed", t);
            }
        });

        // 🔹 AuthManager test (Stage 2 foundation)
        User testUser = new User("admin", "password", "staff");
        AuthManager.login(testUser);

        Log.d(TAG, "Is logged in: " + AuthManager.isLoggedIn());
        Log.d(TAG, "Is staff: " + AuthManager.isStaff());
    }
}
