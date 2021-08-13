package com.example.triviaquiz;


import retrofit2.Call;
import retrofit2.http.GET;

public interface Methods {

    @GET("api.php?amount=10")
    Call<Model> getAllData();
}
