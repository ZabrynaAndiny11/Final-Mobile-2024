package com.example.ayomakan.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    private static final String BASE_URL = "https://restaurant-api.dicoding.dev/";

    public static Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
