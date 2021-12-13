package com.example.questmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AchievementsT extends ProfileActivity {
    //changed AppCompatActivity to ProfileActivity

    View.OnClickListener achievementsButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //changed from dummy to activity_profile and dummyLayout to profileViewPager
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //if we put buttons they would go here!
//        Button achievementsButton = findViewById(R.id.achievementsButton);
//        achievementsButton.setOnClickListener(achievementsButtonListener);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profileViewPager, new AchievementsFragment())
                .commit();
    }
}