package com.example.triviaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // URL url = new URL("https://opentdb.com/api.php?amount=10");

        Methods methods=RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call =methods.getAllData();

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Log.e("tah","On response: code "+response.code() );

                ArrayList<Model.results> data=response.body().getResults();


                for (Model.results results:data){
                    Log.e("TAG", "onResponse: "+results.getQuestion()+" answer:"+results.getCorrect_answer()+" incorrect: "+results.getIncorrect_answers());
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage());
            }
        });


    }

}