package com.example.questmanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

public class CharacterStatsFragment extends Fragment {

    View rootView;

    View.OnClickListener characterStatsButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    public CharacterStatsFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.character_stats_layout, container, false);

        Button charStatsButton = rootView.findViewById(R.id.characterStatsButton);
        charStatsButton.setOnClickListener(characterStatsButtonListener);

        return rootView;
    }
}
