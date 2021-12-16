package com.example.questmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AchievementsFragment extends Fragment {

    View rootView;
    Character theCharacter = new Character("","");

    ArrayList<Achievement> achievementList;
    achievementItemAdapter achievementsAdapter;

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            achievementsAdapter.notifyDataSetChanged();
        }
    };

    //    View.OnClickListener achievementsButtonListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//        }
//    };

    public AchievementsFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.achievements_layout, container, false);

//        Button achievementsButton = rootView.findViewById(R.id.achievementsButton);
//        achievementsButton.setOnClickListener(achievementsButtonListener);

        //setting listView adapter
        achievementList = new ArrayList<Achievement>();
        achievementsAdapter = new achievementItemAdapter(this.getActivity(), achievementList);
        ListView theListOfAchievements = rootView.findViewById(R.id.listOfAchievements);
        theListOfAchievements.setAdapter(achievementsAdapter);
        theListOfAchievements.setOnItemClickListener(listener);

        checkForQuestAchievements();

        return rootView;
    }

    public void buttonAddTitle(View view){

    }

    public void checkForQuestAchievements(){
        File compQuestFile = this.getActivity().getBaseContext().getFileStreamPath("compQuestList.txt");
        if(compQuestFile.exists()){
            try{
                FileInputStream fis = this.getActivity().openFileInput("compQuestList.txt");
                InputStreamReader isReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isReader);
                String theLine = bufferedReader.readLine();
                int numOfCompQuests = Integer.parseInt(theLine); //Get size from file
                bufferedReader.close();
                isReader.close();
                fis.close();
                if(numOfCompQuests>=20){
                    Achievement comp20Quests = new Achievement("Expert", "Complete 20 quests.", true);
                    achievementList.add(comp20Quests);
                    achievementsAdapter.notifyDataSetChanged();
                    //Toast.makeText(this.getActivity(), "You got an achievement!", Toast.LENGTH_SHORT).show();
                }
                if(numOfCompQuests>=15){
                    Achievement comp15Quests = new Achievement("Advanced", "Complete 15 quests.", true);
                    achievementList.add(comp15Quests);
                    achievementsAdapter.notifyDataSetChanged();
                    //Toast.makeText(this.getActivity(), "You got an achievement!", Toast.LENGTH_SHORT).show();
                }
                if(numOfCompQuests>=10){
                    Achievement comp10Quests = new Achievement("Skilled", "Complete 10 quests.", true);
                    achievementList.add(comp10Quests);
                    achievementsAdapter.notifyDataSetChanged();
                    //Toast.makeText(this.getActivity(), "You got an achievement!", Toast.LENGTH_SHORT).show();
                }
                if(numOfCompQuests>=5){
                    Achievement comp5Quests = new Achievement("Rookie", "Complete 5 quests.", true);
                    achievementList.add(comp5Quests);
                    achievementsAdapter.notifyDataSetChanged();
                    //Toast.makeText(this.getActivity(), "You got an achievement!", Toast.LENGTH_SHORT).show();
                }
            }catch(IOException e){
                Toast.makeText(this.getActivity(), "Error reading completed quests file", Toast.LENGTH_SHORT).show();
            }
        }
    }
}