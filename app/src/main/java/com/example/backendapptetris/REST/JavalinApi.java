package com.example.backendapptetris.REST;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JavalinApi {
    @POST("login")
    Call<String> createPost(@Query("studienr") String studienr, @Query("password") String password);
}
