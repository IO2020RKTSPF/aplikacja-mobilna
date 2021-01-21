package com.io2020.PodzielSieKsiazka.retrofit;

import android.text.TextUtils;
import android.webkit.HttpAuthHandler;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitInstance {
    private final RetrofitAPI retrofitAPI;
    private static RetrofitInstance _instance;

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(RetrofitAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build());


    private RetrofitInstance(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);


        Retrofit retrofit = builder.build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);
    }

    public static void Create(){
        _instance = new RetrofitInstance();
    }

    public static RetrofitInstance GetInstance(){
        return _instance;
    }

    public RetrofitAPI GetAPI(){
        return retrofitAPI;
    }


}
