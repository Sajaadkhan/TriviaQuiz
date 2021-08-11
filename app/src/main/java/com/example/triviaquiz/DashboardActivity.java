package com.example.triviaquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration config = new AppBarConfiguration.Builder(R.id.homeFragment,
                R.id.challengesFragment, R.id.statsFragment).build();


        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, config);
        NavigationUI.setupWithNavController(navView, navController);
    }
}