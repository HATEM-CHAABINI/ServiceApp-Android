package com.hatemchaabini.serviceapp.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitClient {
    private static Retrofit instance;

    public static Retrofit getInstance(){
        if(instance == null)
            instance =new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .baseUrl("http://41.226.11.252:11890/")
                    .build();
        return instance;
    }
}
