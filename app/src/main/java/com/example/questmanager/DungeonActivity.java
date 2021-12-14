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
    Monster toothBrush; //= new Monster("toothBrush",3,15);
    Monster waterCup; //= new Monster("water cup",6,5);
    Monster dragon; //= new Monster("dragon",10,25);
    Monster washingMachine; //=new Monster("washingMachine",5,10);
    Monster martin; //= new Monster("martin",12,40);
    Monster curMonster; //= null;
    Character playerCharacter;// = readFile();
    Random r = new Random(System.currentTimeMillis());
    int monsterKills, MartinKills;
    int mainStat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dungeon);
        slime = new Monster("slime", 4, 10);
        toothBrush = new Monster("goblin", 3, 15);
        waterCup = new Monster("water cup", 6, 5);
        dragon = new Monster("dragon", 10, 25);
        martin = new Monster("martin", 12, 40);
        washingMachine = new Monster("washingMachine", 5, 10);
        curMonster = slime;
        Intent infoIntent = getIntent();
        playerCharacter = (Character) infoIntent.getSerializableExtra("Character");
        readFile();
        getMainStat();
        setMonProgress();
        setHerProgress();

    }

    public void getMainStat(){
        String profession = playerCharacter.getProfession();
        if (profession.equals("Knight")){
            mainStat = playerCharacter.getStr();
        }
        else if(profession.equals("Mage")){
            mainStat = playerCharacter.getSmt();
        }
        else{
            mainStat = playerCharacter.getDex();
        }
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

    int dunLevel = 1; // int counter to keep track of the dungeon level

    /**
     * Changes from an initial introduction screen into the primary combat screen.
     *
     * @param view
     */
    public void continueButton(View view) {
        LinearLayout actionLayout = findViewById(R.id.actionLinearLayout);
        LinearLayout initialLayout = findViewById(R.id.initialLinearLayout);
        TextView titleOne = findViewById(R.id.titleOneTextView);
        TextView titleTwo = findViewById(R.id.titleTwoTextView);
        ProgressBar enemyHealth = findViewById(R.id.enemyHealthBar);
        ProgressBar heroHealth = findViewById(R.id.heroHealthBar);

        initialLayout.setVisibility(View.GONE);
        actionLayout.setVisibility(View.VISIBLE);
        enemyHealth.setVisibility(View.VISIBLE);
        heroHealth.setVisibility(View.VISIBLE);

        setMain();

        titleOne.setText("Dungeon Level");
        titleTwo.setText(String.valueOf(dunLevel));
        generateMonster();

    }

    public void attackButton(View view) {
        double herAtt = (r.nextInt(playerCharacter.getDmg()) + .5 * mainStat);
        if (playerCharacter.isCharged()){
            herAtt = herAtt *2;
        }
        int herDmg = (int) herAtt;

            String move = monsterMove();

            if (move.equals("attack")) {
                int monDmg = r.nextInt(curMonster.getMonDmg());
                if (curMonster.isCharged()){
                    monDmg = monDmg * 2;
                }
                curMonster.setCurPow(curMonster.getMonCurPow() - herDmg);
                setMonProgress();
                playerCharacter.setCurpower(playerCharacter.getCurpower() - monDmg);
                setHerProgress();
                checkDeath();
            }

            if (move.equals("defend")) {
                playerCharacter.setCurpower(playerCharacter.getCurpower() - herDmg);
                setHerProgress();
                checkDeath();
            }
            if (move.equals("charge")) {
                herDmg = herDmg * 2;
                curMonster.setCurPow(curMonster.getMonCurPow() - herDmg);
                setMonProgress();
                checkDeath();
            }
    }

    public void defendButton(View view) {



        if (playerCharacter.getProfession().equals("Knight")) {

            String move = monsterMove();

            if (move.equals("attack")) {
                Random r2 = new Random();
                int dmg2;
                if (curMonster.isCharged()){
                    dmg2 = r2.nextInt(curMonster.getMonDmg()) * 2;
                    playerCharacter.setCurpower(playerCharacter.getCurpower() - dmg2);
                    setHerProgress();

                }
                else{
                    dmg2 = r2.nextInt(curMonster.getMonDmg());
                    curMonster.setCurPow(curMonster.getMonCurPow() - dmg2);
                    setMonProgress();
                }
                checkDeath();
            }

            if (move.equals("defend")) {
                Toast.makeText(getApplicationContext(), "You stare at each other", Toast.LENGTH_LONG).show();
                checkDeath();
            }

            if (move.equals("charge")) {
                curMonster.setCharged(true);
                checkDeath();
            }
        } else if (playerCharacter.getProfession().equals("Mage")) {

            String move = monsterMove();

            if (move.equals("attack")) {
                Random r2 = new Random();
                int dmg2;
                if (curMonster.isCharged()){
                    dmg2 = r2.nextInt(curMonster.getMonDmg()) * 2;
                    playerCharacter.setCurpower(playerCharacter.getCurpower() - dmg2);
                    setHerProgress();

                }
                else{
                    dmg2 = r2.nextInt(curMonster.getMonDmg());
                    curMonster.setCurPow(curMonster.getMonCurPow() - dmg2);
                    setMonProgress();
                }
                checkDeath();
            }

            if (move.equals("defend")) {
                Toast.makeText(getApplicationContext(), "You stare at each other", Toast.LENGTH_LONG).show();
                checkDeath();
            }

            if (move.equals("charge")) {
                curMonster.setCharged(true);
                checkDeath();
            }

        } else if (playerCharacter.getProfession().equals("Ranger")) {

            String move = monsterMove();

            if (move.equals("attack")) {
                Random r2 = new Random();
                int dmg2;
                if (curMonster.isCharged()){
                    dmg2 = r2.nextInt(curMonster.getMonDmg()) * 2;
                    playerCharacter.setCurpower(playerCharacter.getCurpower() - dmg2);
                    setHerProgress();

                }
                else{
                    dmg2 = r2.nextInt(curMonster.getMonDmg());
                    curMonster.setCurPow(curMonster.getMonCurPow() - dmg2);
                    setMonProgress();
                }
                checkDeath();
            }

            if (move.equals("defend")) {
                Toast.makeText(getApplicationContext(), "You stare at each other", Toast.LENGTH_LONG).show();
                checkDeath();
            }

            if (move.equals("charge")) {
                curMonster.setCharged(true);
                checkDeath();
            }
        }
    }

    public void chargeButton(View view) {


        if (playerCharacter.getProfession().equals("Knight")) {

            String move = monsterMove();

            if (move.equals("attack")) {
                Random r2 = new Random();
                int dmg2;
                if (curMonster.isCharged()){
                    dmg2 = (r2.nextInt(curMonster.getMonDmg()) * 2)*2;
                    playerCharacter.setCurpower(playerCharacter.getCurpower() - dmg2);
                    setHerProgress();

                }
                else{
                    dmg2 = (r2.nextInt(curMonster.getMonDmg()))*2;
                    playerCharacter.setCurpower(playerCharacter.getCurpower() - dmg2);
                    setHerProgress();
                }
                checkDeath();
            }

            if (move.equals("defend")) {
                Toast.makeText(getApplicationContext(), "You watch your opponent defend themselves", Toast.LENGTH_LONG).show();
                checkDeath();
            }

            if (move.equals("charge")) {
                curMonster.setCharged(true);
                Toast.makeText(getApplicationContext(), "You stare at one another", Toast.LENGTH_LONG).show();
                checkDeath();
            }
        } else if (playerCharacter.getProfession().equals("Mage")) {

            String move = monsterMove();

            if (move.equals("attack")) {
                Random r2 = new Random();
                int dmg2;
                if (curMonster.isCharged()){
                    dmg2 = (r2.nextInt(curMonster.getMonDmg()) * 2)*2;
                    playerCharacter.setCurpower(playerCharacter.getCurpower() - dmg2);
                    setHerProgress();

                }
                else{
                    dmg2 = (r2.nextInt(curMonster.getMonDmg()))*2;
                    playerCharacter.setCurpower(playerCharacter.getCurpower() - dmg2);
                    setHerProgress();
                }
                checkDeath();
            }

            if (move.equals("defend")) {
                Toast.makeText(getApplicationContext(), "You watch your opponent defend themselves", Toast.LENGTH_LONG).show();
            }

            if (move.equals("charge")) {
                curMonster.setCharged(true);
                Toast.makeText(getApplicationContext(), "You stare at one another", Toast.LENGTH_LONG).show();
            }

        } else if (playerCharacter.getProfession().equals("Ranger")) {

            String move = monsterMove();

            if (move.equals("attack")) {
                Random r2 = new Random();
                int dmg2;
                if (curMonster.isCharged()){
                    dmg2 = (r2.nextInt(curMonster.getMonDmg()) * 2)*2;
                    playerCharacter.setCurpower(playerCharacter.getCurpower() - dmg2);
                    setHerProgress();

                }
                else{
                    dmg2 = (r2.nextInt(curMonster.getMonDmg()))*2;
                    playerCharacter.setCurpower(playerCharacter.getCurpower() - dmg2);
                    setHerProgress();
                }
                checkDeath();
            }

            if (move.equals("defend")) {
                Toast.makeText(getApplicationContext(), "You watch your opponent defend themselves", Toast.LENGTH_LONG).show();
            }

            if (move.equals("charge")) {
                curMonster.setCharged(true);
                Toast.makeText(getApplicationContext(), "You stare at one another", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void checkDeath() {
        if (playerCharacter.getCurpower() <= 0) {
            playerCharacter.setCharged(false);
            returnMainScreen();
            Toast.makeText(getApplicationContext(), "Nice try adventurer... better luck next time", Toast.LENGTH_LONG).show();
        } else if (curMonster.getMonCurPow() <= 0) {
            playerCharacter.setGold(playerCharacter.getGold() + 5);
            curMonster.setCharged(false);
            dunLevel = dunLevel + 1;
            TextView header = findViewById(R.id.titleTwoTextView);
            TextView body = findViewById(R.id.mainTextView);
            setMain();

            header.setText(String.valueOf(dunLevel));
            generateMonster();
            ProgressBar monHealth = findViewById(R.id.enemyHealthBar);
            monHealth.setProgress(100);

        }
    }

    private void generateMonster() {

        int num = r.nextInt(3);
        ImageView monsterIV = findViewById(R.id.monsterView);

        if (dunLevel % 10 == 0) {
            curMonster = martin;
            setMonProgress();
        } else {
            if (num == 0) {
                curMonster = slime;
                monsterIV.setImageResource(R.drawable.slime_monster);
                setMonProgress();
            } else if (num == 1) {
                curMonster = toothBrush;
                monsterIV.setImageResource(R.drawable.toothbrush_monster);
                setMonProgress();
            } else if (num == 2) {
                curMonster = waterCup;
                monsterIV.setImageResource(R.drawable.water_monster);
                setMonProgress();
            } else if (num == 3) {
                curMonster = dragon;
                monsterIV.setImageResource(R.drawable.tiny_dragon_monster);
                setMonProgress();
            }else if (num == 4) {
                curMonster = washingMachine;
                monsterIV.setImageResource(R.drawable.washing_machine_monster);
                setMonProgress();
            }
            else
                Toast.makeText(getApplicationContext(), "Something Broke", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * sends the user back to the main screen.
     *
     * @param view
     */
    public void buttonMainScreen(View view) {
        Intent mainScreenIntent = new Intent(this, MainActivity.class);
        startActivity(mainScreenIntent);
    }

    public void returnMainScreen() {
        Intent mainScreenIntent = new Intent(this, MainActivity.class);
        startActivity(mainScreenIntent);
    }

    public void setMonProgress() {
        ProgressBar enemyHealth = findViewById(R.id.enemyHealthBar);
        enemyHealth.setMax(100);
        float curpower = curMonster.getMonCurPow();
        float maxpower = curMonster.getMonMaxPow();
        int progress = (int) (curpower / maxpower * 100);
        enemyHealth.setProgress(progress);
    }

    public void setHerProgress() {
        ProgressBar heroHealth = findViewById(R.id.heroHealthBar);
        heroHealth.setMax(100);
        float curpower = (float) playerCharacter.getCurpower();
        float maxpower = (float) playerCharacter.getMaxpower();
        int progress = (int) (curpower / maxpower * 100);
        heroHealth.setProgress(progress);
    }

    public void setMain(){
        TextView mainTextView = findViewById(R.id.mainTextView);
        int num = r.nextInt(8);

        if(num == 0){
            int resourceId = this.getResources().getIdentifier("@string/level_1", "string", this.getPackageName());
            mainTextView.setText(resourceId);
        }
        else if(num == 1){
            int resourceId = this.getResources().getIdentifier("@string/level_2", "string", this.getPackageName());
            mainTextView.setText(resourceId);
        }
        else if(num == 2){
            int resourceId = this.getResources().getIdentifier("@string/level_3", "string", this.getPackageName());
            mainTextView.setText(resourceId);
        }
        else if(num == 3){
            int resourceId = this.getResources().getIdentifier("@string/level_4", "string", this.getPackageName());
            mainTextView.setText(resourceId);
        }
        else if(num == 4){
            int resourceId = this.getResources().getIdentifier("@string/level_5", "string", this.getPackageName());
            mainTextView.setText(resourceId);
        }
        else if(num == 5){
            int resourceId = this.getResources().getIdentifier("@string/level_6", "string", this.getPackageName());
            mainTextView.setText(resourceId);
        }
        else if(num == 6){
            int resourceId = this.getResources().getIdentifier("@string/level_7", "string", this.getPackageName());
            mainTextView.setText(resourceId);
        }
        else if(num == 7){
            int resourceId = this.getResources().getIdentifier("@string/level_8", "string", this.getPackageName());
            mainTextView.setText(resourceId);
        }
        else if(num == 8){
            int resourceId = this.getResources().getIdentifier("@string/level_9", "string", this.getPackageName());
            mainTextView.setText(resourceId);
        }
        else{
            Toast.makeText(getApplicationContext(), "OH GOD NO", Toast.LENGTH_SHORT).show();
        }



    }

    private String monsterMove(){

        String Move;
        int num = r.nextInt(10)+1;
        if (num <= 4) {
            Move = "attack";
        } else if (num <= 7) {
            Move = "defend";
        } else {
            Move = "charge";
        }
        return Move;
    }
}