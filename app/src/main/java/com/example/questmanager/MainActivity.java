package com.example.questmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent firstTimeIntent = getIntent();
        // this is for when the button to make new character is pressed
        // and the player is brought to the home screen for the first time

        readFile(); // calls the function below
    }

    public void readFile () {
        File playerData = getBaseContext().getFileStreamPath("playerData.txt");
        // looks for the file to which player character data is saved

        if (playerData.exists()) {
            // if the file is found, we will read the character object from it
            try {
                FileInputStream fis = this.openFileInput("playerData.txt"); // opens file in read mode
                ObjectInputStream ois = new ObjectInputStream(fis);
                // allows us to read an object from the file, according to stack overflow
                Character playerCharacter = (Character) ois.readObject();
                // reads playerCharacter object from the file.

                // next two lines set the name and title text to the player's name and title
                TextView nameTitleTV = findViewById(R.id.nameTitleTextView);
                nameTitleTV.setText(playerCharacter.getName() + " the " + playerCharacter.getTitle());

                // next two lines makes the level textview display the proper number
                TextView levelTV = findViewById(R.id.levelNumberTextView);
                levelTV.setText(String.valueOf(playerCharacter.getLvl()));

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

    public void buttonQuest(View view){
        Intent questScreenIntent = new Intent(this,QuestScreenActivity.class);
        startActivity(questScreenIntent);
    }

}