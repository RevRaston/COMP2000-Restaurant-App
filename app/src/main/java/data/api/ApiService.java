package com.example.comp2000restaurantapp.data.api;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // 1. Create student database
    @POST("create_student/{studentId}")
    Call<Map<String, String>> createStudentDatabase(
            @Path("studentId") String studentId
    );

    // 2. Create user
    @POST("create_user/{studentId}")
    Call<Map<String, String>> createUser(
            @Path("studentId") String studentId,
            @Body Map<String, String> user
    );

    // 3. Read all users
    @GET("read_all_users/{studentId}")
    Call<Map<String, List<Map<String, String>>>> getAllUsers(
            @Path("studentId") String studentId
    );

    // 4. Read specific user
    @GET("read_user/{studentId}/{username}")
    Call<Map<String, Map<String, String>>> getUser(
            @Path("studentId") String studentId,
            @Path("username") String username
    );

    // 5. Update user
    @PUT("update_user/{studentId}/{username}")
    Call<Map<String, String>> updateUser(
            @Path("studentId") String studentId,
            @Path("username") String username,
            @Body Map<String, String> user
    );

    // 6. Delete user
    @DELETE("delete_user/{studentId}/{username}")
    Call<Map<String, String>> deleteUser(
            @Path("studentId") String studentId,
            @Path("username") String username
    );
}

