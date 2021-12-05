package com.example.questmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    ArrayList<Achievement> achievementList;
    achievementItemAdapter achievementsAdapter;

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            achievementsAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //setting fragment adapter
        ViewPager profileVP = findViewById(R.id.profileViewPager);
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
        profileVP.setAdapter(fragmentPagerAdapter);

        //setting listView adapter
        achievementList = new ArrayList<Achievement>();
        achievementsAdapter = new achievementItemAdapter(this, achievementList);
        ListView theListOfAchievements = findViewById(R.id.listOfAchievements);
        theListOfAchievements.setAdapter(achievementsAdapter);
        theListOfAchievements.setOnItemClickListener(listener);
    }
}
