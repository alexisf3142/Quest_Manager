package com.example.questmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CompQuestScreenActivity extends AppCompatActivity {

    ArrayList<Quest> compQuestArray;
    CompQuestScreenAdapter compQuestAdapter;
    int compSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_quest_screen);
        compQuestArray = new ArrayList<Quest>();
        getCompQuestArrayFromFile();

        compQuestAdapter = new CompQuestScreenAdapter(this,compQuestArray);
        ListView questListView = findViewById(R.id.compQuestLV);
        questListView.setAdapter(compQuestAdapter); //assign compQuestAdapter to the listView
        compQuestAdapter.notifyDataSetChanged();
    }

    public void getCompQuestArrayFromFile(){
        File theFile =getBaseContext().getFileStreamPath("compQuestList.txt");
        if (theFile.exists()) {
            try {
                FileInputStream fis = this.openFileInput("compQuestList.txt");
                InputStreamReader isReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isReader);
                String theLine = bufferedReader.readLine();
                compSize = Integer.parseInt(theLine); //Get size from file
                for (int i = 0; i < compSize; i++) { //Get quests from file
                    String name, dueDate, dueTime, description;
                    int difficulty;
                    //get quest data
                    theLine = bufferedReader.readLine();
                    name = theLine;
                    theLine = bufferedReader.readLine();
                    dueDate = theLine;
                    theLine = bufferedReader.readLine();
                    dueTime = theLine;
                    theLine = bufferedReader.readLine();
                    description = theLine;
                    theLine = bufferedReader.readLine();
                    difficulty = Integer.parseInt(theLine);
                    //Create quest with read data and add to list
                    //Quest newQuest = new Quest(name, dueDate, dueTime, description, difficulty);
                    //compQuestArray.add(newQuest);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error loading quests", Toast.LENGTH_SHORT).show();
            }
        }
    }
}