package com.example.questmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QuestScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quest_driver);
        //Set up viewPager for fragments
        ViewPager viewPager = findViewById(R.id.questViewPager);
        QuestPagerAdapter adapter = new QuestPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //Set up tabs for fragments
        TabLayout tabLayout = findViewById(R.id.questTabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}