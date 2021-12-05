package com.example.questmanager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AchievementsT extends ProfileActivity {
    //changed AppCompatActivity to ProfileActivity

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //changed from dummy to activity_profile and dummyLayout to profileViewPager
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profileViewPager, new AchievementsFragment())
                .commit();
    }
}
