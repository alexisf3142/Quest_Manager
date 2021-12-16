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

    public AchievementsFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.achievements_layout, container, false);

        //setting listView adapter
        achievementList = new ArrayList<Achievement>();
        achievementsAdapter = new achievementItemAdapter(this.getActivity(), achievementList);
        ListView theListOfAchievements = rootView.findViewById(R.id.listOfAchievements);
        theListOfAchievements.setAdapter(achievementsAdapter);
        theListOfAchievements.setOnItemClickListener(listener);

        //checks if user has completed enough quests to earn an achievement
        checkForQuestAchievements();

        return rootView;
    }

    public void checkForQuestAchievements(){
        //find completed quest file
        File compQuestFile = this.getActivity().getBaseContext().getFileStreamPath("compQuestList.txt");
        if(compQuestFile.exists()){
            try{
                //read the first line of it to get the number of completed quests
                FileInputStream fis = this.getActivity().openFileInput("compQuestList.txt");
                InputStreamReader isReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isReader);
                String theLine = bufferedReader.readLine();
                int numOfCompQuests = Integer.parseInt(theLine); //Get size from file
                bufferedReader.close();
                isReader.close();
                fis.close();
                //making appropriate achievement and adding it to adapter
                if(numOfCompQuests>=20){
                    Achievement comp20Quests = new Achievement("Expert", "Complete 20 quests.", true);
                    achievementList.add(comp20Quests);
                    achievementsAdapter.notifyDataSetChanged();
                }
                if(numOfCompQuests>=15){
                    Achievement comp15Quests = new Achievement("Advanced", "Complete 15 quests.", true);
                    achievementList.add(comp15Quests);
                    achievementsAdapter.notifyDataSetChanged();
                }
                if(numOfCompQuests>=10){
                    Achievement comp10Quests = new Achievement("Skilled", "Complete 10 quests.", true);
                    achievementList.add(comp10Quests);
                    achievementsAdapter.notifyDataSetChanged();
                }
                if(numOfCompQuests>=5){
                    Achievement comp5Quests = new Achievement("Rookie", "Complete 5 quests.", true);
                    achievementList.add(comp5Quests);
                    achievementsAdapter.notifyDataSetChanged();
                }
            }catch(IOException e){
                Toast.makeText(this.getActivity(), "Error reading completed quests file", Toast.LENGTH_SHORT).show();
            }
        }
    }
}