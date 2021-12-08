package com.example.questmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Random;

public class DungeonActivity extends AppCompatActivity {

    Monster slime; //= new Monster("slime",4,10);
    Monster goblin; //= new Monster("goblin",3,15);
    Monster waterCup; //= new Monster("water cup",6,5);
    Monster dragon; //= new Monster("dragon",10,25);
    Monster martin; //= new Monster("martin",12,40);
    Monster curMonster; //= null;
    Character playerCharacter;// = readFile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dungeon);
        slime = new Monster("slime",4,10);
        goblin = new Monster("goblin",3,15);
        waterCup = new Monster("water cup",6,5);
        dragon = new Monster("dragon",10,25);
        martin = new Monster("martin",12,40);
        curMonster = slime;
        Intent infoIntent = getIntent();
        playerCharacter = (Character) infoIntent.getSerializableExtra("Character");
        readFile();
        setMonProgress();
        setHerProgress();

    }

    public void readFile() {
        File playerData = getBaseContext().getFileStreamPath("playerData.txt");
        //looks for the file to which player character data is saved

        if (playerData.exists()) {
            //if the file is found, we will read the character object from it
            try {
                FileInputStream fis = this.openFileInput("playerData.txt"); // opens file in read mode
                ObjectInputStream ois = new ObjectInputStream(fis);
                // allows us to read an object from the file, according to stack overflow
                playerCharacter = (Character) ois.readObject();
                // next two lines close the read mode file
                ois.close();
                fis.close();

            } catch (Exception e) { // error message
                Toast.makeText(this, "Player data was found, but there was an error reading the file", Toast.LENGTH_SHORT).show();
            }
        }

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
        curMonster = goblin;

    }

    public void attackButton(View view){

        Random r = new Random();
        if(playerCharacter.getProfession().equals("Knight")){
            double temp = (r.nextInt(playerCharacter.getDmg()) + .5*playerCharacter.getStr());
            int dmg = (int)temp;
            curMonster.setCurPow(curMonster.getMonCurPow()-dmg);
            setMonProgress();

            Random r2 = new Random();
            int dmg2 = r2.nextInt(curMonster.getMonDmg());
            playerCharacter.setCurpower(playerCharacter.getCurpower()-dmg2);
            setHerProgress();

            checkDeath();}
        else if(playerCharacter.getProfession().equals("Mage")){
            double temp = (r.nextInt(playerCharacter.getDmg()) + .5*playerCharacter.getSmt());
            int dmg = (int)temp;
            curMonster.setCurPow(curMonster.getMonCurPow()-dmg);
            setMonProgress();

            Random r2 = new Random();
            int dmg2 = r2.nextInt(curMonster.getMonDmg());
            playerCharacter.setCurpower(playerCharacter.getCurpower()-dmg2);
            setHerProgress();

            checkDeath();
        }
        else if(playerCharacter.getProfession().equals("Ranger")){
            double temp = (r.nextInt(playerCharacter.getDmg()) + .5*playerCharacter.getDex());
            int dmg = (int)temp;
            curMonster.setCurPow(curMonster.getMonCurPow()-dmg);
            setMonProgress();

            Random r2 = new Random();
            int dmg2 = r2.nextInt(curMonster.getMonDmg());
            playerCharacter.setCurpower(playerCharacter.getCurpower()-dmg2);
            setHerProgress();

            checkDeath();
        }
    }

    public void zapButton(View view){

        Random r = new Random();
        double temp = (r.nextInt(playerCharacter.getDmg()) + .5*playerCharacter.getSmt());
        int dmg = (int)temp;
        curMonster.setCurPow(curMonster.getMonCurPow()-dmg);
        setMonProgress();

        Random r2 = new Random();
        int dmg2 = r2.nextInt(curMonster.getMonDmg());
        playerCharacter.setCurpower(playerCharacter.getCurpower()-dmg2);
        setHerProgress();

        checkDeath();
    }

    public void shootButton(View view){

        Random r = new Random();
        double temp = (r.nextInt(playerCharacter.getDmg()) + .5*playerCharacter.getDex());
        int dmg = (int)temp;
        curMonster.setCurPow(curMonster.getMonCurPow()-dmg);
        setMonProgress();

        Random r2 = new Random();
        int dmg2 = r2.nextInt(curMonster.getMonDmg());
        playerCharacter.setCurpower(playerCharacter.getCurpower()-dmg2);
        setHerProgress();

        checkDeath();
    }

    private void checkDeath() {
        if(playerCharacter.getCurpower() <= 0){
            returnMainScreen();
            Toast.makeText(getApplicationContext(), "Nice try adventurer... better luck next time", Toast.LENGTH_LONG).show();
        }
        else if(curMonster.getMonCurPow() <= 0){
            playerCharacter.setGold(playerCharacter.getGold()+5);
            dunLevel = dunLevel+1;
            generateMonster();
            setMonProgress();

        }
    }

    private void generateMonster() {
        Random r = new Random();
        int num = r.nextInt(3);

        if(dunLevel%10 == 0){
            curMonster = martin;
        }
        else{
            if(num == 0){
                curMonster = slime;
            }
            else if (num == 1){
                curMonster = goblin;
            }
            else if (num == 2){
                curMonster = waterCup;
            }
            else if (num == 3){
                curMonster = dragon;
            }
            else
                Toast.makeText(getApplicationContext(), "Something Broke", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * sends the user back to the main screen.
     * @param view
     */
    public void buttonMainScreen(View view){
        Intent mainScreenIntent = new Intent(this,MainActivity.class);
        startActivity(mainScreenIntent);
    }

    public void returnMainScreen(){
        Intent mainScreenIntent = new Intent(this,MainActivity.class);
        startActivity(mainScreenIntent);
    }

    //public void getImage(String name){
    //ImageView monster = findViewById(R.id.monsterView);
    //Switch(name){
    //  case "monOne":
    //    int monId = this.getResources().getIdentifier("@drawable/monOne",null,this.getPackageName());
    //  Drawable res = getResources().getDrawable(monId);
    //monster.setImageDrawable(res);
    //break;
    //}
    // }

    public void setMonProgress(){
        ProgressBar enemyHealth = findViewById(R.id.enemyHealthBar);
        enemyHealth.setMax(100);
        float curpower = curMonster.getMonCurPow();
        float maxpower = curMonster.getMonMaxPow();
        int progress = (int)(curpower/maxpower*100);
        enemyHealth.setProgress(progress);
    }

    public void setHerProgress(){
        ProgressBar heroHealth = findViewById(R.id.heroHealthBar);
        heroHealth.setMax(100);
        float curpower = (float)playerCharacter.getCurpower();
        float maxpower = (float)playerCharacter.getMaxpower();
        int progress = (int)(curpower/maxpower*100);
        heroHealth.setProgress(progress);
    }



}