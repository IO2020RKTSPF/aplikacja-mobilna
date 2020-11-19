package com.io2020.PodzielSieKsiazka.retrofit;

import com.io2020.PodzielSieKsiazka.schemas.Book;
import com.io2020.PodzielSieKsiazka.schemas.GoogleUserBody;
import com.io2020.PodzielSieKsiazka.schemas.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {
    static final String BASE_URL = "http://podzielsieksiazka.northeurope.cloudapp.azure.com:8080/api/";

    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int id);

    @GET("users/login/{id}")
    Call<User> loginGoogleUser(@Path("id") String googleId);

    @POST("users")
    Call<User> registerGoogleUser(@Body() GoogleUserBody googleUserBody);


    @GET("books")
    Call<List<Book>> getAllBooksList();

    @GET("books/{id}")
    Call<Book> getBookById(@Path("id") int id);

    @POST("books")
    Call<Book> addBook(@Body() Book book);
}
