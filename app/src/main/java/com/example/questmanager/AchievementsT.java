package com.example.questmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AchievementsT extends ProfileActivity {

    View.OnClickListener achievementsButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //gets the AchievementFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profileViewPager, new AchievementsFragment())
                .commit();
    }
}