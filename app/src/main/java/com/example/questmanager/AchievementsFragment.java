package com.example.questmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AchievementsFragment extends Fragment {

    View rootView;

    View.OnClickListener achievementsButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    public AchievementsFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.achievements_layout, container, false);

        Button achievementsButton = rootView.findViewById(R.id.achievementsButton);
        achievementsButton.setOnClickListener(achievementsButtonListener);

        return rootView;
    }
}
