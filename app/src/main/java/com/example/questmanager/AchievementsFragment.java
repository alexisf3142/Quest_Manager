package com.example.questmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AchievementsFragment extends Fragment {

    View rootView;
    //Activity profileActivity;

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
    private Object ProfileActivity;

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

        return rootView;
    }
}