package com.example.oathapp.requesting;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VKParser {
    Retrofit retrofit;
    private final userInterface aPie;

    public VKParser() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.vk.com/method/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aPie = retrofit.create(userInterface.class);
    }

    public userInterface getaPie() {
        return aPie;
    }
}
