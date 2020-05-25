package com.example.backendapptetris.REST;

import com.example.backendapptetris.data.Bruger;
import com.example.backendapptetris.data.Spiller;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JavalinApi {
    @POST("login")
    Call<Bruger> login(@Body Spiller spiller);

    @POST("score")
    Call<Spiller> uploadHighscore(@Body Spiller spiller);
}
