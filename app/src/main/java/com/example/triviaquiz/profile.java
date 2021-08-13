package com.example.triviaquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class profile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView email,profilename,phone;
    private CardView btnSignout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


            email=(TextView)findViewById(R.id.emailP);
            profilename=(TextView)findViewById(R.id.profileName);
            phone=(TextView)findViewById(R.id.phoneP);

        //intialize firebase
        mAuth = FirebaseAuth.getInstance();
        btnSignout=(CardView) findViewById(R.id.btnSignout);
        // firebase state change listener
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    Toast.makeText(getApplicationContext(),"onAuthStateChanged:signed_in: "+user.getEmail(),Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"On auth statechanged:signed out: ",Toast.LENGTH_LONG).show();
                }
            }
        };

        //firebase signout your account listener
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Signing Out....",Toast.LENGTH_LONG).show();
                mAuth.signOut();

                Intent signout=new Intent(getApplicationContext(),SplashActivity.class);

                startActivity(signout);
            }
        });
        //sign out function ends here

    }
    //firebase current user
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void updateUI(FirebaseUser currentUser) {
        //print current user
       email.setText(currentUser.getEmail());
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    profilename.setText(String.valueOf(task.getResult().child("name").getValue()));
                    phone.setText(String.valueOf(task.getResult().child("phoneNumber").getValue()));
                }
            }
        });

    }


}