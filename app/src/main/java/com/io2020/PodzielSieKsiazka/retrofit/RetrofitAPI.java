package com.io2020.PodzielSieKsiazka.retrofit;

import com.io2020.PodzielSieKsiazka.schemas.AppUser;
import com.io2020.PodzielSieKsiazka.schemas.Book;
import com.io2020.PodzielSieKsiazka.schemas.GoogleUserBody;
import com.io2020.PodzielSieKsiazka.schemas.LoginResponse;
import com.io2020.PodzielSieKsiazka.schemas.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {
    static final String BASE_URL = "http://podzielsieksiazka.northeurope.cloudapp.azure.com:8080/api/";


    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int id);

    @POST("users/login")
    Call<LoginResponse> loginGoogleUser(@Body() GoogleUserBody body);


    @GET("books")
    Call<List<Book>> getAllBooksList();

    @GET("books/{id}")
    Call<Book> getBookById(@Path("id") int id);

    @POST("books")
    Call<Book> addBook(@Header("Authorization") String authorization, @Body() Book book);
}
