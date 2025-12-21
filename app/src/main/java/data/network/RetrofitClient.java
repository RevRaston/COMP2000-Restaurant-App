package com.example.comp2000restaurantapp.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.comp2000restaurantapp.data.api.ApiService;

public class RetrofitClient {

    // ⚠️ Replace this with the BASE URL from your API document
    private static final String BASE_URL = "http://10.240.72.69/comp2000/coursework/";

    private static Retrofit retrofit;

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}

