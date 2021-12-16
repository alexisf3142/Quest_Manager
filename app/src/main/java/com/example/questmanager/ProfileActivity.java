package com.example.questmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    Character theCharacter = new Character("","");
//    ArrayList<Achievement> achievementList;
//    achievementItemAdapter achievementsAdapter;
//
//    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            achievementsAdapter.notifyDataSetChanged();
//        }
//    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //setting fragment adapter
        ViewPager profileVP = findViewById(R.id.profileViewPager);
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
        profileVP.setAdapter(fragmentPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.profileTabs);
        tabLayout.setupWithViewPager(profileVP);

        //setting listView adapter
//        achievementList = new ArrayList<Achievement>();
//        achievementsAdapter = new achievementItemAdapter(this, achievementList);
//        ListView theListOfAchievements = findViewById(R.id.listOfAchievements);
//        theListOfAchievements.setAdapter(achievementsAdapter);
//        theListOfAchievements.setOnItemClickListener(listener);
    }
    public void buttonBackFromProfile (View view){
        Intent backFromProfileIntent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(backFromProfileIntent);
    }
}
