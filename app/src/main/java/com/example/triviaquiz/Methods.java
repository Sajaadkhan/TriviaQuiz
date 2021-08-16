package com.example.triviaquiz;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
//api.php?amount=10&category=18&difficulty=easy&type=multiple
public interface Methods {

    @GET("api.php?amount=10&type=multiple")
    Call<Model> getAllData(@Query("difficulty") String level,
                           @Query("category") String category);
}
