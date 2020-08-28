package com.hatemchaabini.serviceapp.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofits {
    private static Retrofit instance;

    public static Retrofit getInstance(){
        if(instance == null)
            instance =new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://41.226.11.252:11890/")
                    .build();
        return instance;
    }
}
