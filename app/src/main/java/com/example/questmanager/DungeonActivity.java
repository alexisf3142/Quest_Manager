package com.example.questmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DungeonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dungeon);


    }

    int dunLevel = 0; // int counter to keep track of the dungeon level

    /**
     * Changes from an initial introduction screen into the primary combat screen.
     * @param view
     */
    public void continueButton(View view){
        LinearLayout actionLayout = findViewById(R.id.actionLinearLayout);
        LinearLayout initialLayout = findViewById(R.id.initialLinearLayout);
        TextView mainTextView = findViewById(R.id.mainTextView);
        TextView titleOne = findViewById(R.id.titleOneTextView);
        TextView titleTwo = findViewById(R.id.titleTwoTextView);
        ProgressBar enemyHealth = findViewById(R.id.enemyHealthBar);
        ProgressBar heroHealth = findViewById(R.id.heroHealthBar);

        initialLayout.setVisibility(View.GONE);
        actionLayout.setVisibility(View.VISIBLE);
        enemyHealth.setVisibility(View.VISIBLE);
        heroHealth.setVisibility(View.VISIBLE);

        int resourceId = this.getResources().getIdentifier("@string/level_1","string",this.getPackageName());
        mainTextView.setText(resourceId);

        dunLevel = dunLevel+1;
        titleOne.setText("Dungeon Level");
        titleTwo.setText(String.valueOf(dunLevel));
    }

    /**
     * sends the user back to the main screen.
     * @param view
     */
    public void buttonMainScreen(View view){
        Intent mainScreenIntent = new Intent(this,MainActivity.class);
        startActivity(mainScreenIntent);
    }


}