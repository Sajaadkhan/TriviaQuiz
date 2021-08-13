package com.example.triviaquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    //private EditText email,password;
     ProgressBar progressBar;
     private boolean isvalid=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
            //progress bar
        progressBar=(ProgressBar) findViewById(R.id.progressBarSignup);
        progressBar.setVisibility(View.GONE);
        //initialize firebase
        mAuth=FirebaseAuth.getInstance();

       final EditText email=(EditText) findViewById(R.id.etEmail);
       final EditText password=(EditText) findViewById(R.id.txtPassword);

        TextView txtSignUp = (TextView)findViewById(R.id.txtSignUp);
        txtSignUp.setMovementMethod(LinkMovementMethod.getInstance());
        txtSignUp.setLinkTextColor(Color.BLUE);

        //signup text click listener
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent signUpIntent = new Intent(SplashActivity.this, SignupActivity.class);

                startActivity(signUpIntent);


            }
        });

        //sign in button click listener

        Button btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validations for sign in fields
                if(email.getText().toString().isEmpty()){
                    isvalid=false;
                    email.setError("Required");
                    email.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                            isvalid=false;
                            email.setError("Not a valid email");
                            email.requestFocus();
                }
                else if(password.getText().toString().isEmpty()){
                    isvalid=false;
                    password.setError("Required");
                    password.requestFocus();
                }
                else if(password.getText().toString().length()<8){
                    isvalid=false;
                    password.setError("Length should be greater than or equals to 8");

                    password.requestFocus();
                }
                else{
                    isvalid=true;
                }
                // sign in with email if fields are valid
                if(isvalid) {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                Intent dashboardIntent = new Intent(SplashActivity.this, DashboardActivity.class);
                                startActivity(dashboardIntent);

                            } else {
                                Toast.makeText(getApplicationContext(), "You are not registered", Toast.LENGTH_LONG).show();
                                email.setError("You are not Registered");
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    });
                }

            }
        });

    }
}