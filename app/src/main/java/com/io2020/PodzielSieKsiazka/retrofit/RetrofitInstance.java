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
    private static RetrofitAPI retrofitAPI;
    private static Retrofit retrofit;
    private static RetrofitInstance _instance;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(RetrofitAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());


    private RetrofitInstance(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);


        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(RetrofitAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);
    }

    public static void Create(){
        _instance = new RetrofitInstance();
    }

    public static RetrofitInstance GetInstance(){
        return _instance;
    }

    public static RetrofitAPI GetAPI(){
        return _instance.retrofitAPI;
    }


}
