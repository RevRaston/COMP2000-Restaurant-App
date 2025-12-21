package com.example.comp2000restaurantapp.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // ⚠️ Replace this with the BASE URL from the API PDF
    // Must end with a trailing slash
    private static final String BASE_URL = "https://YOUR_API_BASE_URL_HERE/";

    private static Retrofit retrofit;

    // Private constructor = no accidental instantiation
    private RetrofitClient() {}

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}

