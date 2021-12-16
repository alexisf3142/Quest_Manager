package com.example.questmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class DungeonActivity extends AppCompatActivity {

    Monster slime; //= new Monster("slime",4,10);
    Monster toothBrush; //= new Monster("toothBrush",3,15);
    Monster waterCup; //= new Monster("water cup",6,5);
    Monster dragon; //= new Monster("dragon",10,25);
    Monster washingMachine; //=new Monster("washingMachine",5,10);
    Monster martin; //= new Monster("martin",12,40);
    Monster ghost; //= new Monster("ghost",6,20)
    Monster tree;//= new Monster("tree",8,23)
    Monster goblin;//= new Monster("goblin",3,10)
    Monster werewolf;//= new Monster("werewolf",9,19)
    Monster curMonster; //= null;
    Character playerCharacter;// = readFile();
    Random r = new Random(System.currentTimeMillis());
    int mainStat = 0; // main combat stat depending on class
    int dunLevel = 1; // int counter to keep track of the dungeon level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dungeon);

        //Propagates all of the monster classes.
        slime = new Monster("slime", 4, 10,R.drawable.slime_monster,R.drawable.dead_slime_monster);
        toothBrush = new Monster("goblin", 3, 15,R.drawable.toothbrush_monster,R.drawable.dead_toothbrush_monster);
        waterCup = new Monster("water cup", 6, 5,R.drawable.water_monster,R.drawable.dead_water_monster);
        dragon = new Monster("dragon", 10, 25,R.drawable.tiny_dragon_monster,R.drawable.dead_tiny_dragon_monster);
        martin = new Monster("martin", 12, 40,R.drawable.martin_monster,R.drawable.martin_monster);
        washingMachine = new Monster("washingMachine", 5, 10,R.drawable.washing_machine_monster,R.drawable.dead_washing_machine_monster);
        ghost = new Monster("ghost",6,20,R.drawable.ghost_monster,R.drawable.dead_ghost_monster);
        tree = new Monster("tree",8,23,R.drawable.tree_monster,R.drawable.dead_tree_monster);
        goblin = new Monster("goblin",3,10,R.drawable.goblin_monster,R.drawable.dead_goblin_monster);
        werewolf = new Monster("werewolf",9,19,R.drawable.werewolf_monster,R.drawable.dead_werewolf_monster);


        curMonster = slime; // starts with basic monster
        readFile(); // read file to get Character info
        getMainStat(); //finds main combat stat
        setMonProgress(); //sets monster health bar
        setHerProgress(); //sets hero health bar

    }

    /**
     * This function checks the class and gets whatever stat helps determine damage
     */
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

    /**
     * This reads in the character file
     */
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

    /**
     * this writes to the file that stores all the character data
     */
    public void updateCharacterFile() {
        File playerData = getBaseContext().getFileStreamPath("playerData.txt");
        //looks for the file to which player character data is saved
        if (playerData.exists()) {
            try {
                FileOutputStream fos = this.openFileOutput("playerData.txt", Context.MODE_PRIVATE);
                // makes the file and opens it in write mode
                ObjectOutputStream os = new ObjectOutputStream(fos);
                // allows us to write an object to the file, according to stack overflow
                os.writeObject(playerCharacter);
                // writes playerCharacter object to the file. next two lines close the write mode file
                os.close();
                fos.close();

                // Toast.makeText(this, "It worked!", Toast.LENGTH_SHORT).show();
                // placeholder to move around and make sure each step works properly

            } catch (IOException e) {
                Toast.makeText(this, "Problem with output file", Toast.LENGTH_SHORT).show();
                // placeholder error message
            }
        } else {
            //CHANGE!!!!!
            Toast.makeText(this, "this is impossible what did you do?", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Changes from an initial introduction screen into the primary combat screen.
     *
     * @param view
     */
    public void continueButton(View view) {

        //Code below here manages changing from into screen to combat screen.
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



        //changes header
        titleOne.setText("Dungeon Level");
        titleTwo.setText(String.valueOf(dunLevel));
        generateMonster(); //generates the monster
        setMain(); //sets the main flavor text
        setMonProgress(); // sets the monster health bar.
    }

    /**
     * This button does the attack.
     * When ever the character makes an attack the enemy also takes an action through a function
     * call to monstermove().There is also a function call to checkDeath() to see if the character
     * or monster have died.
     * @param view
     */
    public void attackButton(View view) {

        // code below here sets up the combat tracker
        TextView flavorText = findViewById(R.id.mainTextView);
        TextView heroTitle = findViewById(R.id.heroTextView);
        TextView heroAction = findViewById(R.id.heroAction);
        TextView monTitle =findViewById(R.id.monsterTextView);
        TextView monAction = findViewById(R.id.monsterAction);

        flavorText.setVisibility(View.INVISIBLE);
        heroTitle.setVisibility(View.VISIBLE);
        heroAction.setVisibility(View.VISIBLE);
        heroAction.setText("Attack");
        monTitle.setVisibility(View.VISIBLE);
        monAction.setVisibility(View.VISIBLE);

        boolean chargedAttack = false;
        double herAtt = (r.nextInt(playerCharacter.getDmg()) + .5 * mainStat);
        if (playerCharacter.isCharged()){
            herAtt = herAtt * (2 + (playerCharacter.getStr()/20.0));
            chargedAttack = true;
            playerCharacter.setCharged(false);
        }
        int herDmg = (int) herAtt;

        String move = monsterMove();

        //if else loop to see what attack the monster is doing so the proper operations are being done
        if (move.equals("attack")) {
            monAction.setText("Attack");
            int monDmg = r.nextInt(curMonster.getMonDmg());
            if (curMonster.isCharged()){
                monDmg = monDmg * 2;
                curMonster.setCharged(false);
            }
            curMonster.setCurPow(curMonster.getMonCurPow() - herDmg);
            setMonProgress();
            playerCharacter.setCurpower(playerCharacter.getCurpower() - monDmg);
            setHerProgress();
            checkDeath();
        }

        if (move.equals("defend")) {
            monAction.setText("Defend");
            if (chargedAttack){
                curMonster.setCurPow(curMonster.getMonCurPow() - herDmg);
                setMonProgress();
            }
            else {
                playerCharacter.setCurpower(playerCharacter.getCurpower() - herDmg);
                setHerProgress();
            }
            checkDeath();
        }
        if (move.equals("charge")) {
            monAction.setText("Charging Attack");
            herDmg = herDmg * 2;
            curMonster.setCurPow(curMonster.getMonCurPow() - herDmg);
            setMonProgress();
            checkDeath();
        }
    }

    /**
     * This button makes the character defend.
     * When ever the character defends the enemy also takes an action through a function
     * call to monstermove().There is also a function call to checkDeath() to see if the character
     * or monster have died.
     * @param view
     */
    public void defendButton(View view) {

        //sets up combat tracker.
        TextView flavorText = findViewById(R.id.mainTextView);
        TextView heroTitle = findViewById(R.id.heroTextView);
        TextView heroAction = findViewById(R.id.heroAction);
        TextView monTitle =findViewById(R.id.monsterTextView);
        TextView monAction = findViewById(R.id.monsterAction);

        flavorText.setVisibility(View.INVISIBLE);
        heroTitle.setVisibility(View.VISIBLE);
        heroAction.setVisibility(View.VISIBLE);
        heroAction.setText("Defend");
        monTitle.setVisibility(View.VISIBLE);
        monAction.setVisibility(View.VISIBLE);

        String move = monsterMove();

        // if else to check what action the enemy is doing and then executing that action.
        if (move.equals("attack")) {
            boolean chargedAttack = false;
            monAction.setText("Attack");
            double monDmg = r.nextInt(curMonster.getMonDmg());
            monDmg = monDmg + monDmg * playerCharacter.getDex()/20.0;
            int dmg2 = (int) monDmg;
            if (curMonster.isCharged()){
                dmg2 = dmg2 * 2;
                curMonster.setCharged(false);
                chargedAttack = true;
            }
            if (chargedAttack){
                playerCharacter.setCurpower(playerCharacter.getCurpower() - dmg2);
                setHerProgress();
            }
            else {
                curMonster.setCurPow(curMonster.getMonCurPow() - dmg2);
                setMonProgress();
            }
            checkDeath();
        }

        if (move.equals("defend")) {
            monAction.setText("Defend");
            Toast.makeText(getApplicationContext(), "You stare at each other", Toast.LENGTH_LONG).show();
            checkDeath();
        }

        if (move.equals("charge")) {
            monAction.setText("Charging Attack");
            curMonster.setCharged(true);
            checkDeath();
        }
    }

    /**
     * This button makes the character charge an attack.
     * When ever the character charges an attack the enemy also takes an action through a function
     * call to monstermove(). There is also a function call to checkDeath() to see if the character
     * or monster have died.
     * @param view
     */
    public void chargeButton(View view) {

        //sets up the combat tracker
        TextView flavorText = findViewById(R.id.mainTextView);
        TextView heroTitle = findViewById(R.id.heroTextView);
        TextView heroAction = findViewById(R.id.heroAction);
        TextView monTitle =findViewById(R.id.monsterTextView);
        TextView monAction = findViewById(R.id.monsterAction);

        flavorText.setVisibility(View.INVISIBLE);
        heroTitle.setVisibility(View.VISIBLE);
        heroAction.setVisibility(View.VISIBLE);
        heroAction.setText("Charging Attack");
        monTitle.setVisibility(View.VISIBLE);
        monAction.setVisibility(View.VISIBLE);

        String move = monsterMove();

        // set of if statements to see what the enemy is doing and executing that action.
        playerCharacter.setCharged(true);
        if (move.equals("attack")) {
            monAction.setText("Attack");
            int dmg2;
            dmg2 = r.nextInt(curMonster.getMonDmg()) * 2;
            if (curMonster.isCharged()){
                dmg2 = dmg2 * 2;
                curMonster.setCharged(false);
            }
            playerCharacter.setCurpower(playerCharacter.getCurpower() - dmg2);
            setHerProgress();
            checkDeath();
        }

        if (move.equals("defend")) {
            monAction.setText("Defend");
            Toast.makeText(getApplicationContext(), "You watch your opponent defend themselves", Toast.LENGTH_LONG).show();
            checkDeath();
        }

        if (move.equals("charge")) {
            monAction.setText("Charging Attack");
            curMonster.setCharged(true);
            Toast.makeText(getApplicationContext(), "You stare at one another", Toast.LENGTH_LONG).show();
            checkDeath();
        }
    }

    /**
     * This function checks wether the character or monster have died. If the character has died
     * then it sends the user back to the home screen. If the monster dies then it moves the user to
     * a defeated monster screen.
     */
    private void checkDeath() {
        double goldGained = playerCharacter.getGold() ;
        // if player dies sends to home screen
        if (playerCharacter.getCurpower() <= 0) {
            playerCharacter.setCharged(false); //makes sure you can't bank charged attacks
            playerCharacter.setCurpower(0); // doesn't let the player have negative health
            updateCharacterFile(); // updates char file
            returnMainScreen();
            Toast.makeText(getApplicationContext(), "Nice try adventurer... better luck next time", Toast.LENGTH_LONG).show();
        }
        // if the monster dies gives players gold and gets new monster
        else if (curMonster.getMonCurPow() <= 0) {

            if(curMonster==martin){
                goldGained = playerCharacter.getGold() + 20 + 5 * playerCharacter.getLck()/20.0;}
            else{
                goldGained = playerCharacter.getGold() + 5 + 5 * playerCharacter.getLck()/20.0;}
            playerCharacter.setGold((int)goldGained);
            updateCharacterFile();
            curMonster.setCharged(false);
            dunLevel = dunLevel + 1;
            deathScreen();
        }



    }

    /**
     * This function generates the next monster for the user to fight.
     */
    private void generateMonster() {

        // num used to select random monster
        int num = r.nextInt(9);
        ImageView monsterIV = findViewById(R.id.monsterView);
        curMonster.setCharged(false);
        curMonster.setCurPow(curMonster.getMonMaxPow());
        // tracks wether to display boss fight

        if (dunLevel % 10 == 0) {
            curMonster = martin;
            setMonProgress();
        } else {
            if (num == 0) {
                curMonster = slime;
            } else if (num == 1) {
                curMonster = toothBrush;
            } else if (num == 2) {
                curMonster = waterCup;
            } else if (num == 3) {
                curMonster = dragon;
            }else if (num == 4) {
                curMonster = washingMachine;
            }else if (num == 5) {
                curMonster = ghost;
            }else if (num == 6) {
                curMonster = tree;
            }else if (num == 7) {
                curMonster = goblin;
            }else if (num == 8) {
                curMonster = werewolf;
            }
            else
                Toast.makeText(getApplicationContext(), "Something Broke", Toast.LENGTH_SHORT).show();
            monsterIV.setImageResource(curMonster.getPic());
            setMonProgress();
        }


    }

    /**
     * sends the user back to the main screen.
     * @param view
     */
    public void buttonMainScreen(View view) {
        Intent mainScreenIntent = new Intent(this, MainActivity.class);
        startActivity(mainScreenIntent);
    }

    /**
     * This also ends the user back to the main screen, but it isn't meant to be used on a button,
     * but is called when the user dies.
     */
    public void returnMainScreen() {
        Intent mainScreenIntent = new Intent(this, MainActivity.class);
        startActivity(mainScreenIntent);
    }

    /**
     * This function sets the monster health bar.
     */
    public void setMonProgress() {
        ProgressBar enemyHealth = findViewById(R.id.enemyHealthBar);
        enemyHealth.setMax(100);
        float curpower = curMonster.getMonCurPow();
        float maxpower = curMonster.getMonMaxPow();
        int progress = (int) (curpower / maxpower * 100);
        enemyHealth.setProgress(progress);
    }

    /**
     * This function sets the hero health bar.
     */
    public void setHerProgress() {
        ProgressBar heroHealth = findViewById(R.id.heroHealthBar);
        heroHealth.setMax(100);
        float curpower = (float) playerCharacter.getCurpower();
        float maxpower = (float) playerCharacter.getMaxpower();
        int progress = (int) (curpower / maxpower * 100);
        heroHealth.setProgress(progress);
    }

    /**
     * This function selects the proper flavor text to display to the user.
     */
    public void setMain(){
        TextView mainTextView = findViewById(R.id.mainTextView);
        TextView heroTitle = findViewById(R.id.heroTextView);
        TextView monTitle = findViewById(R.id.monsterTextView);
        TextView heroAction = findViewById(R.id.heroAction);
        TextView monAction = findViewById(R.id.monsterAction);
        heroTitle.setVisibility(View.GONE);
        monTitle.setVisibility(View.GONE);
        heroAction.setVisibility(View.GONE);
        monAction.setVisibility(View.GONE);

        mainTextView.setVisibility(View.VISIBLE);

        //random number to have random flavor text
        int num = r.nextInt(9);

        // tracks whether to display boss screen or not
        if(curMonster == martin){
            int resourceId = this.getResources().getIdentifier("@string/level_boss", "string", this.getPackageName());
            mainTextView.setText(resourceId);
        }
        else{
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
            }}



    }

    /**
     * This function determines what sort of move the monster is going to make during combat.
     * @return A string with the name of the attack.
     */
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

    /**
     * This is the death screen that appears when a monster is killed.
     */
    public void deathScreen(){
        //Manages displaying or hiding all of the required text views.
        TextView heroTitle = findViewById(R.id.heroTextView);
        TextView monTitle = findViewById(R.id.monsterTextView);
        TextView heroAction = findViewById(R.id.heroAction);
        TextView monAction = findViewById(R.id.monsterAction);
        heroTitle.setVisibility(View.GONE);
        monTitle.setVisibility(View.GONE);
        heroAction.setVisibility(View.GONE);
        monAction.setVisibility(View.GONE);
        TextView flavorText = findViewById(R.id.mainTextView);
        flavorText.setVisibility(View.VISIBLE);
        ImageView monIV = findViewById(R.id.monsterView);
        LinearLayout combatView = findViewById(R.id.actionLinearLayout);
        LinearLayout initialView = findViewById(R.id.initialLinearLayout);
        //shows whatever death screen is intended.
        if(curMonster == martin){
            int resourceId = this.getResources().getIdentifier("@string/boss_end", "string", this.getPackageName());
            flavorText.setText(resourceId);
        }
        else{
            int resourceId = this.getResources().getIdentifier("@string/level_end", "string", this.getPackageName());
            flavorText.setText(resourceId);}

        monIV.setImageResource(curMonster.getDeathPic());
        combatView.setVisibility(View.GONE);
        initialView.setVisibility(View.VISIBLE);

    }
}