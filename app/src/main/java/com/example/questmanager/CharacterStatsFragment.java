package com.example.questmanager;

import android.content.Context;
import android.content.Intent;
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

    public CharacterStatsFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.character_stats_layout, container, false);

        //reading file to get character data
        readFile();

        //changing TextViews in Character Stats Layout
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

        //setting onClickListeners for each skill point button
        Button strButton = rootView.findViewById(R.id.buttonStrengthSP);
        strButton.setOnClickListener(addStrButtonListener);
        Button dexButton = rootView.findViewById(R.id.buttonDexSP);
        dexButton.setOnClickListener(addDexButtonListener);
        Button luckButton = rootView.findViewById(R.id.buttonLuckSP);
        luckButton.setOnClickListener(addLuckButtonListener);
        Button intButton = rootView.findViewById(R.id.buttonIntelligneceSP);
        intButton.setOnClickListener(addIntButtonListener);

        return rootView;
    }

    View.OnClickListener addStrButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonAddStrengthSkillPoint(view);
        }
    };
    View.OnClickListener addDexButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonAddDexSkillPoint(view);
        }
    };
    View.OnClickListener addLuckButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonAddLuckSkillPoint(view);
        }
    };
    View.OnClickListener addIntButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonAddSmtSkillPoint(view);
        }
    };

    //adds 1 strength skill point
    public void buttonAddStrengthSkillPoint(View view){
        if(theCharacter.getSkillPoints()>0){
            theCharacter.setStr(theCharacter.getStr()+1);
            theCharacter.setSkillPoints(theCharacter.getSkillPoints() - 1);
            TextView charStrength = rootView.findViewById(R.id.amountOfStrength);
            charStrength.setText(String.valueOf(theCharacter.getStr()));
            updateCharacterFile();
        }else{
            Toast.makeText(getActivity(),"You don't have enough skill points.",Toast.LENGTH_SHORT).show();
        }
    }

    //adds 1 dexterity skill point
    public void buttonAddDexSkillPoint(View view){
        if(theCharacter.getSkillPoints()>0){
            theCharacter.setDex(theCharacter.getDex()+1);
            theCharacter.setSkillPoints(theCharacter.getSkillPoints() - 1);
            TextView charDex = rootView.findViewById(R.id.amountOfDexterity);
            charDex.setText(String.valueOf(theCharacter.getDex()));
            updateCharacterFile();
        }else{
            Toast.makeText(getActivity(),"You don't have enough skill points.",Toast.LENGTH_SHORT).show();
        }
    }

    //adds 1 luck skill point
    public void buttonAddLuckSkillPoint(View view){
        if(theCharacter.getSkillPoints()>0){
            theCharacter.setLck(theCharacter.getLck()+1);
            theCharacter.setSkillPoints(theCharacter.getSkillPoints() - 1);
            TextView charLuck = rootView.findViewById(R.id.amountOfLuck);
            charLuck.setText(String.valueOf(theCharacter.getLck()));
            updateCharacterFile();
        }else{
            Toast.makeText(getActivity(),"You don't have enough skill points.",Toast.LENGTH_SHORT).show();
        }
    }

    //adds 1 intelligence skill point
    public void buttonAddSmtSkillPoint(View view){
        if(theCharacter.getSkillPoints()>0){
            theCharacter.setSmt(theCharacter.getSmt()+1);
            theCharacter.setSkillPoints(theCharacter.getSkillPoints() - 1);
            TextView charSmt = rootView.findViewById(R.id.amountOfIntelligence);
            charSmt.setText(String.valueOf(theCharacter.getSmt()));
            updateCharacterFile();
        }else{
            Toast.makeText(getActivity(),"You don't have enough skill points.",Toast.LENGTH_SHORT).show();
        }
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

    public void updateCharacterFile() {
        File playerData = getActivity().getBaseContext().getFileStreamPath("playerData.txt");
        //looks for the file to which player character data is saved
        if (playerData.exists()) {
            try {
                FileOutputStream fos = getActivity().openFileOutput("playerData.txt", Context.MODE_PRIVATE);
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
                Toast.makeText(getActivity(), "Problem with output file", Toast.LENGTH_SHORT).show();
                // placeholder error message
            }
        } else {
            //CHANGE!!!!!
            Toast.makeText(getActivity(), "this is impossible what did you do?", Toast.LENGTH_SHORT).show();
        }
    }
}
