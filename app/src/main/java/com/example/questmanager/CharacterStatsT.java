package com.example.questmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.app.Fragment;
import androidx.fragment.app.Fragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CharacterStatsT extends ProfileActivity {

    View.OnClickListener characterStatsButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //gets the CharacterStatsFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profileViewPager, new CharacterStatsFragment())
                .commit();
    }
}
