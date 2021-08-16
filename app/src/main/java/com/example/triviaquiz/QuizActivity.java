package com.example.triviaquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTV,questionNumberTV;
    private Button optionBtn,optionBtn2,optionBtn3,optionBtn4;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public String profilename;

    Random random;

    int currentScore=0,questionAttemted=1,currentPos;
    public ArrayList<Model.results> data;
    public ArrayList<String> options;
    String level,category,categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mAuth = FirebaseAuth.getInstance();
        questionTV=findViewById(R.id.idTVQuestion);
        questionNumberTV=findViewById(R.id.idTVQuestionAttemted);
        optionBtn=findViewById(R.id.idBtnOption);
        optionBtn2=findViewById(R.id.idBtnOption2);
        optionBtn3=findViewById(R.id.idBtnOption3);
        optionBtn4=findViewById(R.id.idBtnOption4);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    //Toast.makeText(getApplicationContext(),"onAuthStateChanged:signed_in: "+user.getEmail(),Toast.LENGTH_LONG).show();
                }
                else{
                    //Toast.makeText(getApplicationContext(),"On auth statechanged:signed out: ",Toast.LENGTH_LONG).show();
                }
            }
        };


        //getting  data from intent
        Intent homeIntent = getIntent();
        level = homeIntent.getStringExtra("Level");
        category = homeIntent.getStringExtra("Category");
        categoryName=homeIntent.getStringExtra("categoryName");

            //calling Methods interface to requst api data
        //api.php?amount=10&category=18&difficulty=easy&type=multiple
        Methods methods=RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call =methods.getAllData(level,category);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Log.e("tah","On response: code "+response.code() );

                data=response.body().getResults();

                random=new Random();
                currentPos=random.nextInt(10);
                setDataToViews(currentPos);

            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage());
            }
        });

        //get current user name

        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    profilename= (String) task.getResult().child("name").getValue();


                }
            }
        });



        //option click listeners
        optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(currentPos).getCorrect_answer().trim().toLowerCase().equals(optionBtn.getText().toString().trim().toLowerCase())){
                    currentScore++;
                }
                questionAttemted++;
                currentPos=random.nextInt(data.size());
                setDataToViews(currentPos);
            }

        });


        optionBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(currentPos).getCorrect_answer().trim().toLowerCase().equals(optionBtn2.getText().toString().trim().toLowerCase())){
                    currentScore++;
                }
                questionAttemted++;
                currentPos=random.nextInt(data.size());
                setDataToViews(currentPos);
            }

        });

        optionBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(currentPos).getCorrect_answer().trim().toLowerCase().equals(optionBtn3.getText().toString().trim().toLowerCase())){
                    currentScore++;
                }
                questionAttemted++;
                currentPos=random.nextInt(data.size());
                setDataToViews(currentPos);
            }

        });

        optionBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(currentPos).getCorrect_answer().trim().toLowerCase().equals(optionBtn4.getText().toString().trim().toLowerCase())){
                    currentScore++;
                }
                questionAttemted++;
                currentPos=random.nextInt(data.size());
                setDataToViews(currentPos);
            }

        });


    }


//score board using BottomsheetDialog
    private void showScoreDialog(){
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(QuizActivity.this);
        View bottomsheetview= LayoutInflater.from(getApplicationContext()).inflate(R.layout.score_board,(LinearLayout)findViewById(R.id.lscore));
        TextView scoreTv=bottomsheetview.findViewById(R.id.idTvScore);
        Button restartQuiz=bottomsheetview.findViewById(R.id.idRestart);
        Button home=bottomsheetview.findViewById(R.id.gotohome);
        scoreTv.setText("Your score is \n" +currentScore + "/10");


        //restart quiz button
        restartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPos=random.nextInt(10);
                setDataToViews(currentPos);
                questionAttemted=1;
                currentScore=0;
                bottomSheetDialog.dismiss();
            }
        });
        //goto home button
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome =new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(gotohome);
            }
        });
            //preventing user to cancel dialog
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setContentView(bottomsheetview);
        bottomSheetDialog.show();

    }

        //setting the values to quiz options/questions
    private void setDataToViews(int currentPos) {

       options=new ArrayList<>();
       try{
       options.add(data.get(currentPos).getCorrect_answer());
       options.add(data.get(currentPos).getIncorrect_answers().get(0));
        options.add(data.get(currentPos).getIncorrect_answers().get(1));
        options.add(data.get(currentPos).getIncorrect_answers().get(2));
       }
       catch (Exception e){e.getMessage();}
        Collections.shuffle(options);

        questionNumberTV.setText("Questions Attempted: "+questionAttemted+"/10");
        if(questionAttemted==10){


            FirebaseUser currentUser = mAuth.getCurrentUser();
            QuizScore quizScore = new QuizScore(profilename, categoryName, level, currentScore);
            FirebaseDatabase.getInstance().getReference("QuizScore")
                    .child(profilename)
                    .child(categoryName)
                    .setValue(quizScore).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        showScoreDialog();
                    } else {
                        Toast.makeText(QuizActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else{  //using Jsoup to remove html encoding in the response data
           try {
               questionTV.setText(Jsoup.parse(data.get(currentPos).getQuestion()).text());
               optionBtn.setText(Jsoup.parse(options.get(0)).text());
               optionBtn2.setText(Jsoup.parse(options.get(1)).text());
               optionBtn3.setText(Jsoup.parse(options.get(2)).text());
               optionBtn4.setText(Jsoup.parse(options.get(3)).text());
           }catch (Exception e){e.getMessage();}
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
