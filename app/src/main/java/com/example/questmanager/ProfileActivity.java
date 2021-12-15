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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    // following method brings user back to home screen if home button is pressed
    public void buttonBackFromProfile (View view){
        Intent backFromProfileIntent = new Intent(ProfileActivity.this, MainActivity.class);
        ProfileActivity.this.startActivity(backFromProfileIntent);
    }

    public void buttonAddStrengthSkillPoint(View view){
        if(theCharacter.getSkillPoints()>0){
            theCharacter.setStr(theCharacter.getStr()+1);
            TextView charStrength = findViewById(R.id.amountOfStrength);
            charStrength.setText(String.valueOf(theCharacter.getStr()));
            updateCharacterFile();
        }else{
            Toast.makeText(this,"You don't have enough skill points.",Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonAddDexSkillPoint(View view){
        if(theCharacter.getSkillPoints()>0){
            theCharacter.setDex(theCharacter.getDex()+1);
            TextView charDex = findViewById(R.id.amountOfDexterity);
            charDex.setText(String.valueOf(theCharacter.getDex()));
            updateCharacterFile();
        }else{
            Toast.makeText(this,"You don't have enough skill points.",Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonAddLuckSkillPoint(View view){
        if(theCharacter.getSkillPoints()>0){
            theCharacter.setLck(theCharacter.getLck()+1);
            TextView charLuck = findViewById(R.id.amountOfLuck);
            charLuck.setText(String.valueOf(theCharacter.getLck()));
            updateCharacterFile();
        }else{
            Toast.makeText(this,"You don't have enough skill points.",Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonAddSmtSkillPoint(View view){
        if(theCharacter.getSkillPoints()>0){
            theCharacter.setSmt(theCharacter.getSmt()+1);
            TextView charSmt = findViewById(R.id.amountOfIntelligence);
            charSmt.setText(String.valueOf(theCharacter.getSmt()));
            updateCharacterFile();
        }else{
            Toast.makeText(this,"You don't have enough skill points.",Toast.LENGTH_SHORT).show();
        }
    }

    public void updateCharacterFile() {
        File playerData = getBaseContext().getFileStreamPath("playerData.txt");
        //looks for the file to which player character data is saved
        if (playerData.exists()) {
            try {
                FileOutputStream fos = this.openFileOutput("playerData.txt", Context.MODE_PRIVATE);
                // makes the file and opens it in write mode
                ObjectOutputStream os = new ObjectOutputStream(fos);
                // allows us to write an object to the file, according to stack overflow
                os.writeObject(theCharacter);
                // writes playerCharacter object to the file. next two lines close the write mode file
                os.close();
                fos.close();

                // Toast.makeText(this, "It worked!", Toast.LENGTH_SHORT).show();
                // placeholder to move around and make sure each step works properly

            } catch (IOException e) {
                Toast.makeText(this, "Problem with output file", Toast.LENGTH_SHORT).show();
                // placeholder error message
            }
        } else {
            //CHANGE!!!!!
            Toast.makeText(this, "this is impossible what did you do?", Toast.LENGTH_SHORT).show();
        }
    }
}
