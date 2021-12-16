package com.example.questmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class NewPlayerActivity extends AppCompatActivity {

    String character_name, new_profession, weapon_type; // declares strings to save character's class and name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newplayer); // sets layout to new player screen

        Intent firstTimeIntent = getIntent();
        // this is for the intent that runs this screen from readFile in MainActivity
    }

    //  method to retrieve character's name from the editText
    public String getCharacterName() {
        EditText editTextUserName = findViewById(R.id.editTextUserName);
        // grabs the user's name from the editText
        return editTextUserName.getText().toString();
        // saves the user's inputted name as the character's name
    }

/*  Following three methods set profession string to the class associated with the clicked button.
    As long as the name field is not empty, they also call the function to make both final textView and the confirm button visible*/

    public void setProfToKnight (View view) {
        new_profession = "Knight"; // sets player character's profession to Knight
        weapon_type = "basic"; //sets the weapon to the default "basic" type

        character_name = getCharacterName(); // calls function that gets character's name from user
        if (!character_name.isEmpty()) {
            makeConfirmVisible();
            // if there is a name, makes visible what user needs to move to next phase
        }
    }
    public void setProfToMage (View view) {
        new_profession = "Mage"; // sets player character's profession to Mage
        weapon_type = "basic"; //sets the weapon to the default "basic" type

        character_name = getCharacterName(); // calls function that gets character's name from user
        if (!character_name.isEmpty()) {
            makeConfirmVisible();
            // if there is a name, makes visible what user needs to move to next phase
        }
    }
    public void setProfToRanger (View view) {
        new_profession = "Ranger"; // sets player character's profession to Ranger
        weapon_type = "basic"; //sets the weapon to the default "basic" type

        character_name = getCharacterName(); // calls function that gets character's name from user
        if (!character_name.isEmpty()) {
            makeConfirmVisible();
            // if there is a name, makes visible what user needs to move to next phase
        }
    }

//  method to make both final textView and the confirm button visible once name and class are chosen
    public void makeConfirmVisible () {
        TextView confirmTV = findViewById(R.id.textViewConfirmCharacter); // finds final textView
        Button confirmButton = findViewById(R.id.buttonConfirmCharacter); // finds confirm button
        confirmTV.setVisibility(View.VISIBLE); // changes textView from invisible to visible
        confirmButton.setVisibility(View.VISIBLE); // changes confirm button from invisible to visible
    }

/*
* Following method is associated with the confirm character button.
* When it is clicked, it creates a character, creates the playerData file, saves character to file,
* and brings the user from this screen to the home screen.
*/
    public void buttonConfirmCharacter (View view) {
        character_name = getCharacterName(); // this may be redundant, but to be safe...
        Character playerCharacter = new Character(character_name, new_profession);
        // makes new character with the name the user entered and the class they chose
        playerCharacter.setWeapon("basic"); // sets player's weapon to the base one of their class

        try {
            FileOutputStream fos = this.openFileOutput("playerData.txt", Context.MODE_PRIVATE);
            // makes file to save character data and opens it in write mode
            ObjectOutputStream os = new ObjectOutputStream(fos);
            // allows us to write an object to the file, according to stack overflow
            os.writeObject(playerCharacter);
            // writes playerCharacter object to the file. next two lines close the write mode file
            os.close();
            fos.close();

        } catch (IOException e) {
            Toast.makeText(this, "Problem saving data to file", Toast.LENGTH_SHORT).show();
            // error message
        }

        // lastly, this intent brings the user to the home screen
        Intent switchScreens = new Intent(NewPlayerActivity.this, MainActivity.class);
        NewPlayerActivity.this.startActivity(switchScreens);
    }
}
