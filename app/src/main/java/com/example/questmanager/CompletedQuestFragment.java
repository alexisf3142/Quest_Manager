package com.example.questmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CompletedQuestFragment extends Fragment {

    View rootView;
    Activity fragContext;

    ArrayList<Quest> compQuestArray;
    CompQuestScreenAdapter compQuestAdapter;
    int compSize = 0;

    public CompletedQuestFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_comp_quest_screen, container, false);

        fragContext = getActivity();

        compQuestArray = new ArrayList<Quest>();
        getCompQuestArrayFromFile();

        Button backButton = rootView.findViewById(R.id.buttonBackFromCompQuest);
        backButton.setOnClickListener(homeCompQuestButtonListener);

        compQuestAdapter = new CompQuestScreenAdapter(fragContext,compQuestArray);
        ListView questListView = rootView.findViewById(R.id.compQuestLV);
        questListView.setAdapter(compQuestAdapter); //assign compQuestAdapter to the listView
        compQuestAdapter.notifyDataSetChanged();
        return rootView;
    }
    //Set onClickListener for home button
    View.OnClickListener homeCompQuestButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonBackFromCompQuest(view);
        }
    };
    /**
     * Sends the user back to the home screen
     * @param view the view that was clicked and triggered the method
     */
    public void buttonBackFromCompQuest (View view){
        Intent backFromHelpIntent = new Intent(fragContext, MainActivity.class);
        startActivity(backFromHelpIntent);
    }
    /**
     * Read the file of Completed Quests. Get the size from file and put the quests into compQuestArray
     */
    public void getCompQuestArrayFromFile(){
        File theFile = fragContext.getBaseContext().getFileStreamPath("compQuestList.txt");
        if (theFile.exists()) {
            try {
                FileInputStream fis = fragContext.openFileInput("compQuestList.txt");
                InputStreamReader isReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isReader);
                String theLine = bufferedReader.readLine();
                compSize = Integer.parseInt(theLine); //Get size from file
                for (int i = 0; i < compSize; i++) { //Get quests from file
                    String name, dueDate, dueTime, description, creationDate;
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
                    theLine = bufferedReader.readLine();
                    creationDate = theLine;
                    //Create quest with read data and add to list
                    Quest newQuest = new Quest(name, dueDate, dueTime, description, difficulty,creationDate);
                    compQuestArray.add(newQuest);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(fragContext, "Error loading quests", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
