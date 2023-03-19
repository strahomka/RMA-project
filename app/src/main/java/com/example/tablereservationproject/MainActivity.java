package com.example.tablereservationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tablereservationproject.fragments.AboutFragment;
import com.example.tablereservationproject.fragments.HomeFragment;
import com.example.tablereservationproject.fragments.LoginFragment;
import com.example.tablereservationproject.fragments.ProfileFragment;
import com.example.tablereservationproject.fragments.ReservationFragment;
import com.example.tablereservationproject.util.Login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragmentContainerView, HomeFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context appContext = getApplicationContext();
                SharedPreferences sharedPreferences = appContext.getSharedPreferences("userIdPrefs", Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("userId", "");
                System.out.println(userId);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragmentContainerView, AboutFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button resButton = findViewById(R.id.reservationButton);
        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getSupportFragmentManager();
                if(Login.loggedIn) {
                    fm.beginTransaction()
                            .replace(R.id.fragmentContainerView, ReservationFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }else{
                    fm.beginTransaction()
                            .replace(R.id.fragmentContainerView, LoginFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setVisibility(View.GONE);
        profileButton.setEnabled(false);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragmentContainerView, ProfileFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
}

