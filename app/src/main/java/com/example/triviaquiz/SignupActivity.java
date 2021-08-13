package com.example.triviaquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
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
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
     public Button btnsignup;
     public EditText name,email,phone,pass1,pass2;
    public FirebaseAuth mAuth;
    public ProgressBar progressBar;
    private boolean isValid=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        btnsignup=(Button) findViewById(R.id.btnSignup);
        name=(EditText) findViewById(R.id.name_signup);
        email=(EditText) findViewById(R.id.etEmail);
        phone=(EditText) findViewById(R.id.phone_signup);
        pass1=(EditText) findViewById(R.id.txtPassword);
        pass2=(EditText) findViewById(R.id.txtPassword2);
        mAuth = FirebaseAuth.getInstance();
        progressBar=(ProgressBar) findViewById(R.id.progressBarsignin);

        progressBar.setVisibility(View.GONE);


        getSupportActionBar().hide();

        TextView txtSignIn = (TextView)findViewById(R.id.txtSignIn);
        txtSignIn.setMovementMethod(LinkMovementMethod.getInstance());
        txtSignIn.setLinkTextColor(Color.BLUE);
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(SignupActivity.this, SplashActivity.class);
                startActivity(signUpIntent);
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name1 = name.getText().toString().trim();
        String email1 = email.getText().toString().trim();
        String password1 = pass1.getText().toString().trim();
        String password2 = pass2.getText().toString().trim();

        String phone1 = phone.getText().toString().trim();

        if(name1.isEmpty()){
            isValid=false;
            name.setError("Required");
            name.requestFocus();
        }
        else if(email1.isEmpty()){
            isValid=false;
            email.setError("required");
            email.requestFocus();
        }
       else if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            isValid=false;
            email.setError("not a valid email");
            email.requestFocus();
        }
        else if(phone1.isEmpty()){
            isValid=false;
            phone.setError("Required");
            phone.requestFocus();
        }
       else if(password1.isEmpty() ){
            isValid=false;
            pass1.setError("required");
            pass1.requestFocus();

        }
       else if(password1.length()<8){
            isValid=false;
            pass1.setError("length should be greater than or equals 8");
            pass1.requestFocus();
        }
      else  if(password2.isEmpty()){
            isValid=false;
            pass2.setError("required");
            pass2.requestFocus();
        }
       else if(!password1.equals(password2)){
            isValid=false;
            pass2.setError("Both password should be same");
            pass2.requestFocus();
        }

       else{isValid=true;}


        if (isValid) {


                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User usr = new User(name1, email1, phone1);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                                            .setValue(usr).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SignupActivity.this, "User has been successfully registered", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                Intent todashboard=new Intent(getApplicationContext(),DashboardActivity.class);
                                                startActivity(todashboard);
                                            } else {
                                                Toast.makeText(SignupActivity.this, "User failed to register", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(SignupActivity.this, "User failed to register", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });



        }


    }
}