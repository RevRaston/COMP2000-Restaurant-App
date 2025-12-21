package com.example.comp2000restaurantapp.data.repository;


import java.util.List;
import java.util.Map;

import com.example.comp2000restaurantapp.data.api.ApiService;

import retrofit2.Call;

public class UserRepository {

    private final ApiService apiService;
    private final String studentId;

    public UserRepository(ApiService apiService, String studentId) {
        this.apiService = apiService;
        this.studentId = studentId;
    }

    public Call<Map<String, String>> createStudentDatabase() {
        return apiService.createStudentDatabase(studentId);
    }

    public Call<Map<String, String>> createUser(Map<String, String> user) {
        return apiService.createUser(studentId, user);
    }

    public Call<Map<String, List<Map<String, String>>>> getAllUsers() {
        return apiService.getAllUsers(studentId);
    }

    public Call<Map<String, Map<String, String>>> getUser(String username) {
        return apiService.getUser(studentId, username);
    }

    public Call<Map<String, String>> updateUser(String username, Map<String, String> user) {
        return apiService.updateUser(studentId, username, user);
    }

    public Call<Map<String, String>> deleteUser(String username) {
        return apiService.deleteUser(studentId, username);
    }
}
