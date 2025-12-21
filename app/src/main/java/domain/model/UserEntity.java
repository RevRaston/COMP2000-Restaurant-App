package com.example.comp2000restaurantapp.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey
    @NonNull
    public String username;

    public String password;
    public String role;

    public UserEntity(@NonNull String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
