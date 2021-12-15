package com.example.questmanager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CharacterStatsFragment extends Fragment {

    View rootView;
    Character theCharacter = new Character("","");

//    View.OnClickListener characterStatsButtonListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//        }
//    };

    public CharacterStatsFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.character_stats_layout, container, false);

        //reading file to get character data
        readFile();

        //changing textviews in charStatLayout
        TextView charName = rootView.findViewById(R.id.profileName);
        charName.setText(theCharacter.getName());

        TextView charTitle = rootView.findViewById(R.id.profileTitle);
        charTitle.setText(theCharacter.getTitle());

        TextView charProfession = rootView.findViewById(R.id.profileProfession);
        charProfession.setText(theCharacter.getProfession());

        TextView charWeapon = rootView.findViewById(R.id.equippedWeapon);
        charWeapon.setText(theCharacter.getWeapon());

        TextView charGold = rootView.findViewById(R.id.amountOfGold);
        charGold.setText(String.valueOf(theCharacter.getGold()));

        TextView charStrength = rootView.findViewById(R.id.amountOfStrength);
        charStrength.setText(String.valueOf(theCharacter.getStr()));

        TextView charDexterity = rootView.findViewById(R.id.amountOfDexterity);
        charDexterity.setText(String.valueOf(theCharacter.getDex()));

        TextView charLuck = rootView.findViewById(R.id.amountOfLuck);
        charLuck.setText(String.valueOf(theCharacter.getLck()));

        TextView charIntelligence = rootView.findViewById(R.id.amountOfIntelligence);
        charIntelligence.setText(String.valueOf(theCharacter.getSmt()));

        TextView charPower = rootView.findViewById(R.id.amountOfPower);
        charPower.setText(String.valueOf(theCharacter.getCurpower()));

//        Button charStatsButton = rootView.findViewById(R.id.characterStatsButton);
//        charStatsButton.setOnClickListener(characterStatsButtonListener);

        return rootView;
    }

    public void readFile() {
        File playerData = getActivity().getBaseContext().getFileStreamPath("playerData.txt");
        //looks for the file to which player character data is saved

        if (playerData.exists()) {
            //if the file is found, we will read the character object from it
            try {
                FileInputStream fis = this.getActivity().openFileInput("playerData.txt"); // opens file in read mode
                ObjectInputStream ois = new ObjectInputStream(fis);
                // allows us to read an object from the file, according to stack overflow
                theCharacter = (Character) ois.readObject();
                // next two lines close the read mode file
                ois.close();
                fis.close();

            } catch (Exception e) { // error message
                Toast.makeText(this.getActivity(), "Player data was found, but there was an error reading the file", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
