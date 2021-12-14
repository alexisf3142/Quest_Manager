package com.example.questmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

public class MainActivity extends AppCompatActivity {

    Character playerCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readFile(); //calls the function below
        getMainQuestFromFile(); //Gets the main quest
    }

    public void readFile () {
        File playerData = getBaseContext().getFileStreamPath("playerData.txt");
        //looks for the file to which player character data is saved

        if (playerData.exists()) {
            //if the file is found, we will read the character object from it
            try {
                FileInputStream fis = this.openFileInput("playerData.txt"); // opens file in read mode
                ObjectInputStream ois = new ObjectInputStream(fis);
                // allows us to read an object from the file, according to stack overflow
                playerCharacter = (Character) ois.readObject();

                // reads playerCharacter object from the file.


                /* This is a test of the level up function which makes sure it works.
                We won't actually be calling it here.*//*
                playerCharacter.setCurexp(120);
                playerCharacter.levelUp(playerCharacter.getCurexp(), playerCharacter.getMaxexp(), playerCharacter.getMaxpower(), playerCharacter.getLvl());*/

                /* following if set determines which character drawing is visible
                 based on the class the player chose at the start */

                ImageView characterIV = findViewById(R.id.characterImageView);
                ImageView weaponIV = findViewById(R.id.weaponImageView);

                //KNIGHT
                if (playerCharacter.getProfession().equals("Knight")) {
                    //we need to save and read in what weapon they have and assign that here
                    characterIV.setImageResource(R.drawable.knight_human_no_weapon);
                    //what weapon do they have?
                    if (playerCharacter.getWeapon().equals("basic")) {
                        weaponIV.setImageResource(R.drawable.basic_sword_holding);
                    }
                    else if (playerCharacter.getWeapon().equals("fancy")) {
                        weaponIV.setImageResource(R.drawable.fancy_sword_holding);
                    }
                }
                //MAGE
                else if (playerCharacter.getProfession().equals("Mage")) {
                    characterIV.setImageResource(R.drawable.mage_human_no_weapon);
                    //what weapon do they have?
                    if (playerCharacter.getWeapon().equals("basic")) {
                        weaponIV.setImageResource(R.drawable.basic_staff_holding);
                    }
                    else if (playerCharacter.getWeapon().equals("fancy")) {
                        weaponIV.setImageResource(R.drawable.fancy_staff_holding);
                    }
                }
                //RANGER
                else { // since we only have three options this covers the ranger profession
                    characterIV.setImageResource(R.drawable.ranger_elf_no_weapon);
                    //what weapon do they have?
                    if (playerCharacter.getWeapon().equals("basic")) {
                        weaponIV.setImageResource(R.drawable.basic_bow_holding);
                    }
                    else if (playerCharacter.getWeapon().equals("fancy")) {
                        weaponIV.setImageResource(R.drawable.fancy_bow_holding);
                    }
                }

                // next two lines set the name and title text to the player's name and title
                TextView nameTitleTV = findViewById(R.id.nameTitleTextView);
                nameTitleTV.setText(playerCharacter.getName() + "\nthe\n" + playerCharacter.getTitle());

                // next two lines makes the level textview display the proper number
                TextView levelTV = findViewById(R.id.levelTextView);
                levelTV.setText("Level " + playerCharacter.getLvl());

                //setting the progress bars
                ProgressBar powerPB = findViewById(R.id.powerProgressBar);
                ProgressBar expPB = findViewById(R.id.expProgressBar);
                float curpower = playerCharacter.getCurpower();
                float maxpower = playerCharacter.getMaxpower();
                powerPB.setProgress((int)(curpower/maxpower*100));
                float curexp = playerCharacter.getCurexp();
                float maxexp = playerCharacter.getMaxexp();
                expPB.setProgress((int)(curexp/maxexp*100));

                // next two lines close the read mode file
                ois.close();
                fis.close();

            } catch (Exception e) { // error message
                Toast.makeText(this, "Player data was found, but there was an error reading the file", Toast.LENGTH_SHORT).show();
            }
        }

        else {
            /* if there is no player character data file,
             then we presume the user is running the app for the first time
             and take them to the new player screen */
            Intent newPlayerScreen = new Intent(this, NewPlayerActivity.class);
            startActivity(newPlayerScreen);
        }
    }

    //following method takes player to the quest screen when they press the quest button
    public void buttonQuest(View view) {
        Intent questScreenIntent = new Intent(this,QuestScreenActivity.class);
        startActivity(questScreenIntent);
    }

    //following method takes player to the profile screen when they press the profile button
    public void buttonProfile(View view) {
        Intent profileScreenIntent = new Intent(MainActivity.this, ProfileActivity.class);
        MainActivity.this.startActivity(profileScreenIntent);
    }

    //following method takes player to the quest screen when they press the quest button
    public void buttonShop(View view) {
        Intent shopScreenIntent = new Intent(this,ShopScreenActivity.class);
        startActivity(shopScreenIntent);
    }

    public void buttonDungeon(View view) {
        if(playerCharacter.getCurpower() > 0 ){
            Intent dungeonScreenIntent = new Intent(MainActivity.this, DungeonActivity.class);
            MainActivity.this.startActivity(dungeonScreenIntent);}
        else{
            Toast.makeText(getApplicationContext(), "Complete quests to gain power!", Toast.LENGTH_LONG).show();
        }
    }

    //following method takes player to the help screen when they press the help button
    public void buttonHelp(View view) {
        Intent helpScreenIntent = new Intent(MainActivity.this, HelpActivity.class);
        MainActivity.this.startActivity(helpScreenIntent);
    }

    public void getMainQuestFromFile(){
        int size = 0;
        File theFile = getBaseContext().getFileStreamPath("questList.txt");
        TextView mainQuestTV = findViewById(R.id.mainQuestTextView);
        if (theFile.exists()){
            try {
                FileInputStream fis = openFileInput("questList.txt");
                InputStreamReader isReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isReader);
                String theLine = bufferedReader.readLine();
                size = Integer.parseInt(theLine); //Get size from file
                String mainQuest = bufferedReader.readLine();
                if (size > 0){
                    mainQuestTV.setText(mainQuest);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this,"Error loading main quest",Toast.LENGTH_SHORT).show();
            }
        }
        if (size == 0){
            mainQuestTV.setText("No Main Quest");
        }
    }


}