package com.example.comp2000restaurantapp.data.repository;

import com.example.comp2000restaurantapp.data.api.ApiService;
import com.example.comp2000restaurantapp.domain.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class UserRepository {

    private final ApiService apiService;
    private final String studentId;

    public UserRepository(ApiService apiService, String studentId) {
        this.apiService = apiService;
        this.studentId = studentId;
    }

    /* -------------------------
       RAW API CALLS (baseline)
       ------------------------- */

    public Call<Map<String, String>> createStudentDatabase() {
        return apiService.createStudentDatabase(studentId);
    }

    public Call<Map<String, String>> createUser(User user) {
        return apiService.createUser(studentId, userToMap(user));
    }

    public Call<Map<String, List<Map<String, String>>>> getAllUsers() {
        return apiService.getAllUsers(studentId);
    }

    public Call<Map<String, Map<String, String>>> getUserRaw(String username) {
        return apiService.getUser(studentId, username);
    }

    public Call<Map<String, String>> updateUser(String username, User user) {
        return apiService.updateUser(studentId, username, userToMap(user));
    }

    public Call<Map<String, String>> deleteUser(String username) {
        return apiService.deleteUser(studentId, username);
    }

    /* -------------------------
       MAPPING HELPERS
       ------------------------- */

    private User mapToUser(Map<String, String> data) {
        return new User(
                data.get("username"),
                data.get("password"),
                data.get("role")
        );
    }

    private Map<String, String> userToMap(User user) {
        Map<String, String> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        map.put("role", user.getRole());
        return map;
    }
}
