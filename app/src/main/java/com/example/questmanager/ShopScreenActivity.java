package com.example.questmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ShopScreenActivity extends AppCompatActivity {

    ArrayList<ShopItem> theList = new ArrayList<ShopItem>();
    ShopAdapter itemAdapter;
    ListView shopListView;
    ShopItem basicSword, basicStaff, basicBow;
    ShopItem fancySword, fancyStaff, fancyBow;
    ShopItem extravagantSword, extravagantStaff, extravagantBow;
    //String[] weaponsOwned;
    ArrayList<String> weaponsOwned = new ArrayList<String>();
    Character playerCharacter;


    //onItemClickListener
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            itemAdapter.setMostRecentlyClickedPosition(i);
            itemAdapter.notifyDataSetChanged();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //get the intent that got us here
        Intent callerIntent = getIntent();

        shopListView = findViewById(R.id.shopListView);
        itemAdapter = new ShopAdapter(this, theList);
        shopListView.setAdapter(itemAdapter);
        shopListView.setOnItemClickListener(listener);


        basicSword = new ShopItem(0, "basic sword", "basic", "Knight", null);
        fancySword = new ShopItem(200, "fancy sword", "fancy", "Knight", null);
        extravagantSword = new ShopItem(500, "extravagant sword", "extravagant", "Knight", null);

        basicStaff = new ShopItem(0, "basic staff", "basic", "Mage", null);
        fancyStaff = new ShopItem(200, "fancy staff", "fancy", "Mage", null);
        extravagantStaff = new ShopItem(500, "extravagant staff", "extravagant", "Mage", null);

        basicBow = new ShopItem(0, "basic bow", "basic", "Ranger", null);
        fancyBow = new ShopItem(200, "fancy bow", "fancy", "Ranger", null);
        extravagantBow = new ShopItem(500, "extravagant bow", "extravagant", "Ranger", null);

        readFile();
        readShopFile();

        //implement read and write file

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
                //update the gold
                playerCharacter.setGold(500);
                TextView goldValueTV = findViewById(R.id.goldValueTextView);
                goldValueTV.setText(String.valueOf(playerCharacter.getGold()));

                // next two lines close the read mode file
                ois.close();
                fis.close();

            } catch (Exception e) { // error message
                Toast.makeText(this, "Player data was found, but there was an error reading the file", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void readShopFile() {
        File shopData = getBaseContext().getFileStreamPath("shopData.txt");
        //looks for the file to which player character data is saved

        if (shopData.exists()) {
            //if the file is found, we will read the already purchase shop items from it
            try {
                FileInputStream fis = this.openFileInput("shopData.txt");
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                BufferedReader bR = new BufferedReader(inputStreamReader);
                String theLine = bR.readLine();
                String tempString;
                while (theLine != null) {
                    tempString = theLine;
                    //do stuff
                    weaponsOwned.add(theLine);
                    theLine = bR.readLine();
                }
                //close the read mode file
                fis.close();

            } catch (Exception e) { // error message
                Toast.makeText(this, "ahhhhhhhhhShop data was found, but there was an error reading the file", Toast.LENGTH_LONG).show();
            }
        }

        //show all the weapons
        //KNIGHT
        if (playerCharacter.getProfession().equals("Knight")) {
            KnightStoreItems();
        }
        //MAGE
        else if (playerCharacter.getProfession().equals("Mage")) {
            MageStoreItems();
        }
        //RANGER
        else {
            RangerStoreItems();
        }

    }

    public void updateCharacterFile() {
        File shopData = getBaseContext().getFileStreamPath("shopData.txt");
        //looks for the file to which player character data is saved
        if (shopData.exists()) {
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
            //cry
        }
    }

    public void updateFile() {
        try {
            FileOutputStream fos = this.openFileOutput("shopData.txt", Context.MODE_PRIVATE);
            for (int i = 0; i < theList.size(); i++) {
                try {
                    fos.write(theList.get(i).getWeaponType().getBytes());
                    fos.write("\n".getBytes());
                } catch (Exception e) {
                    Toast exceptionToast = Toast.makeText(this, "problem with writing to output file, shopData.txt", Toast.LENGTH_LONG);
                    exceptionToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 0);
                    exceptionToast.show();
                }
            }
        } catch (Exception e) {
            Toast exceptionToast = Toast.makeText(this, "problem with updating output file, shopData.txt", Toast.LENGTH_LONG);
            exceptionToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 0);
            exceptionToast.show();
        }
    }

    public void KnightStoreItems() {
        if (!weaponsOwned.isEmpty()) {
            for (int i = 0; i < weaponsOwned.size(); i++) {
                if (weaponsOwned.get(i).equals("basic")) {
                    basicSword.setPurchased(true);
                }
                if (weaponsOwned.get(i).equals("fancy")) {
                    fancySword.setPurchased(true);
                }
                if (weaponsOwned.get(i).equals("extravagant")) {
                    extravagantSword.setPurchased(true);
                }
            }
        }

        //basic sword will always be purchased since you get it at the start
        basicSword.setPurchased(true);
        theList.add(basicSword);
        theList.add(fancySword);
        //theList.add(extravagantSword);
    }

    public void MageStoreItems() {
        if (!weaponsOwned.isEmpty()) {
            for (int i = 0; i < weaponsOwned.size(); i++) {
                if (weaponsOwned.get(i).equals("basic")) {
                    basicStaff.setPurchased(true);
                }
                if (weaponsOwned.get(i).equals("fancy")) {
                    fancyStaff.setPurchased(true);
                }
                if (weaponsOwned.get(i).equals("extravagant")) {
                    extravagantStaff.setPurchased(true);
                }
            }
        }
        //basic staff will always be purchased since you get it at the start
        basicStaff.setPurchased(true);
        theList.add(basicStaff);
        theList.add(fancyStaff);
        //theList.add(extravagantStaff);
    }

    public void RangerStoreItems() {
        if (!weaponsOwned.isEmpty()) {
            for (int i = 0; i < weaponsOwned.size(); i++) {
                if (weaponsOwned.get(i).equals("basic")) {
                    basicBow.setPurchased(true);
                }
                if (weaponsOwned.get(i).equals("fancy")) {
                    fancyBow.setPurchased(true);
                }
                if (weaponsOwned.get(i).equals("extravagant")) {
                    extravagantBow.setPurchased(true);
                }
            }
        }
        //basic bow will always be purchased since you get it at the start
        basicBow.setPurchased(true);
        theList.add(basicBow);
        theList.add(fancyBow);
        theList.add(extravagantBow);
    }

    public void purchaseButton(View view) {
        ImageView nitramIV = findViewById(R.id.nitramImageView);
        //check if the player has enough gold
        if (playerCharacter.getGold() >= itemAdapter.currentItem.getPrice()) {
            nitramIV.setImageResource(R.drawable.nitram_thanks);
            //setPurchased, take the gold away
            itemAdapter.currentItem.setPurchased(true);
            itemAdapter.currentItem.setPrice(0);
            playerCharacter.setGold(playerCharacter.getGold() - itemAdapter.currentItem.getPrice());
            //update the UI
            TextView goldValueTV = findViewById(R.id.goldValueTextView);
            goldValueTV.setText(String.valueOf(playerCharacter.getGold()));
            //set their weapon to the new one they just bought
            itemAdapter.setMostRecentlyClickedPosition(itemAdapter.getMostRecentlyClickedPosition());
            playerCharacter.setWeapon(itemAdapter.currentItem.getWeaponType());
            itemAdapter.notifyDataSetChanged();
            updateFile();
            updateCharacterFile();
        }
        else {
            //if the player does not have enough gold
            nitramIV.setImageResource(R.drawable.nitram_not_enough_gold);
        }
    }

    public void equipButton(View view) {
        itemAdapter.setMostRecentlyClickedPosition(itemAdapter.getMostRecentlyClickedPosition());
        playerCharacter.setWeapon(itemAdapter.currentItem.getWeaponType());
        updateFile();
        updateCharacterFile();

    }
    // following method brings user back to home screen if home button is pressed
    public void buttonBackFromHelp (View view){
        Intent backFromShopIntent = new Intent(ShopScreenActivity.this, MainActivity.class);
        ShopScreenActivity.this.startActivity(backFromShopIntent);
    }
}

