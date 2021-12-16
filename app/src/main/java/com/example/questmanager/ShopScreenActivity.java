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


    //important variables
    ArrayList<ShopItem> theList = new ArrayList<ShopItem>(); //list of weapons in shop
    ArrayList<String> weaponsOwned = new ArrayList<String>(); //list of weapons character/user owns
    ListView shopListView;
    ShopAdapter itemAdapter;
    ShopItem basicSword, basicStaff, basicBow;
    ShopItem fancySword, fancyStaff, fancyBow;
    ShopItem extravagantSword, extravagantStaff, extravagantBow;
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

        //initialize the listView and everything related
        shopListView = findViewById(R.id.shopListView);
        itemAdapter = new ShopAdapter(this, theList);
        shopListView.setAdapter(itemAdapter);
        shopListView.setOnItemClickListener(listener);

        //read the character class, and the shop file
        readCharacterFile();
        readShopFile();

    }

    /**
     * this reads the file that stores all the character data into playerCharacter
     */
    public void readCharacterFile() {
        File playerData = getBaseContext().getFileStreamPath("playerData.txt");
        //looks for the file to which player character data is saved
        if (playerData.exists()) {
            //if the file is found, we will read the character object from it
            try {
                FileInputStream fis = this.openFileInput("playerData.txt"); // opens file in read mode
                ObjectInputStream ois = new ObjectInputStream(fis);
                //allows us to read an object from the file
                playerCharacter = (Character) ois.readObject();
                TextView goldValueTV = findViewById(R.id.goldValueTextView);
                goldValueTV.setText(String.valueOf(playerCharacter.getGold()));
                //next two lines close the read mode file
                ois.close();
                fis.close();

            } catch (Exception e) { //error message
                Toast.makeText(this, "Player data was found, but there was an error reading the file", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * this reads the file that stores all the shop data of the purchased items
     */
    public void readShopFile() {
        File shopData = getBaseContext().getFileStreamPath("shopData.txt");
        //looks for the file to which shop data is saved
        if (shopData.exists()) {
            //if the file is found, we will read the purchased shop items from it
            try {
                FileInputStream fis = this.openFileInput("shopData.txt");
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                BufferedReader bR = new BufferedReader(inputStreamReader);
                String theLine = bR.readLine();
                String tempString;
                while (theLine != null) {
                    tempString = theLine;
                    weaponsOwned.add(theLine);
                    theLine = bR.readLine();
                }
                //close the read file
                fis.close();

            } catch (Exception e) { //error message
                Toast.makeText(this, "Shop data was found, but there was an error reading the file", Toast.LENGTH_LONG).show();
            }
        }
        //KNIGHT + SWORD
        if (playerCharacter.getProfession().equals("Knight")) {
            KnightStoreItems();
        }
        //MAGE + STAFF
        if (playerCharacter.getProfession().equals("Mage")) {
            MageStoreItems();
        }
        //RANGER + BOW
        if (playerCharacter.getProfession().equals("Ranger")) {
            RangerStoreItems();
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
                //makes the file and opens it in write mode
                ObjectOutputStream os = new ObjectOutputStream(fos);
                //allows us to write an object to the file, according to stack overflow
                os.writeObject(playerCharacter);
                //writes playerCharacter object to the file. next two lines close the write mode file
                os.close();
                fos.close();


            } catch (IOException e) {
                Toast.makeText(this, "Problem with output file", Toast.LENGTH_SHORT).show();
                // placeholder error message
            }
        } else {
            //this should never happen
            Toast.makeText(this, "Problem with output to playerData, the file does not exist...", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * this writes to the file that stores all the shop data
     */
    public void updateShopFile() {
        try {
            FileOutputStream fos = this.openFileOutput("shopData.txt", Context.MODE_PRIVATE);
            for (int i = 0; i < weaponsOwned.size(); i++) {
                try {
                    //this is writing the weapon types that they own!
                    //"basic" , "fancy", "extravagant"
                    fos.write(weaponsOwned.get(i).getBytes());
                    //fos.write(theList.get(i).getWeaponType().getBytes());
                    fos.write("\n".getBytes());
                } catch (Exception e) {
                    Toast exceptionToast = Toast.makeText(this, "problem with updating output file, shopData.txt", Toast.LENGTH_LONG);
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

    /**
     * KNIGHT this analyzes the data from shopData file and adds the weapons to the store list
     * by going through the array list weaponsOwned we figure out which weapons are still available
     * for purchase and update the items and their prices/states respectively
     */
    public void KnightStoreItems() {
        basicSword = new ShopItem(0, "basic sword", "basic", "Knight", 1, null);
        //basic sword will always be purchased since you get it at the start
        basicSword.setPurchased(true);
        weaponsOwned.add(basicSword.itemName);
        theList.add(basicSword);
        //add the other corresponding knight items to the shop
        fancySword = new ShopItem(200, "fancy sword", "fancy", "Knight", 5, null);
        extravagantSword = new ShopItem(500, "extravagant sword", "extravagant", "Knight", 10,  null);
        //the data we got from the shopData file is stored in weaponsOwned, now update the items
        if (!weaponsOwned.isEmpty()) {
            for (int i = 0; i < weaponsOwned.size(); i++) {
                if (weaponsOwned.get(i).equals("fancy")) {
                    fancySword.setPurchased(true);
                    fancySword.setPrice(0);
                }
                if (weaponsOwned.get(i).equals("extravagant")) {
                    extravagantSword.setPurchased(true);
                    extravagantSword.setPrice(0);
                }
            }
        }
        //finally add them to the store
        theList.add(fancySword);
        theList.add(extravagantSword);
        itemAdapter.notifyDataSetChanged();
    }

    /**
     * MAGE this analyzes the data from shopData file and adds the weapons to the store list
     * by going through the array list weaponsOwned we figure out which weapons are still available
     * for purchase and update the items and their prices/states respectively
     */
    public void MageStoreItems() {
        basicStaff = new ShopItem(0, "basic staff", "basic", "Mage", 1, null);
        //basic staff will always be purchased since you get it at the start
        weaponsOwned.add(basicStaff.itemName);
        basicStaff.setPurchased(true);
        theList.add(basicStaff);
        //add the other corresponding mage items to the shop
        fancyStaff = new ShopItem(200, "fancy staff", "fancy", "Mage", 5, null);
        extravagantStaff = new ShopItem(500, "extravagant staff", "extravagant", "Mage", 10, null);
        //the data we got from the shopData file is stored in weaponsOwned, now update the items
        if (!weaponsOwned.isEmpty()) {
            for (int i = 0; i < weaponsOwned.size(); i++) {
                if (weaponsOwned.get(i).equals("fancy")) {
                    fancyStaff.setPurchased(true);
                    fancyStaff.setPrice(0);
                }
                if (weaponsOwned.get(i).equals("extravagant")) {
                    extravagantStaff.setPurchased(true);
                    extravagantStaff.setPrice(0);
                }
            }
        }
        theList.add(fancyStaff);
        theList.add(extravagantStaff);
        itemAdapter.notifyDataSetChanged();
    }

    /**
     * RANGER this analyzes the data from shopData file and adds the weapons to the store list
     * by going through the array list weaponsOwned we figure out which weapons are still available
     * for purchase and update the items and their prices/states respectively
     */
    public void RangerStoreItems() {
        basicBow = new ShopItem(0, "basic bow", "basic", "Ranger", 1, null);
        //basic bow will always be purchased since you get it at the start
        weaponsOwned.add(basicBow.itemName);
        basicBow.setPurchased(true);
        theList.add(basicBow);
        fancyBow = new ShopItem(200, "fancy bow", "fancy", "Ranger", 5, null);
        extravagantBow = new ShopItem(500, "extravagant bow", "extravagant", "Ranger", 10, null);
        //the data we got from the shopData file is stored in weaponsOwned, now update the items
        if (!weaponsOwned.isEmpty()) {
            for (int i = 0; i < weaponsOwned.size(); i++) {
                if (weaponsOwned.get(i).equals("fancy")) {
                    fancyBow.setPurchased(true);
                    fancyBow.setPrice(0);
                }
                if (weaponsOwned.get(i).equals("extravagant")) {
                    extravagantBow.setPurchased(true);
                    extravagantBow.setPrice(0);
                }
            }
        }
        //finally add them to the store
        theList.add(fancyBow);
        theList.add(extravagantBow);
        if (playerCharacter.getWeapon().equals("basic")) {
            basicBow.setEquipped(true);
        }
        if (playerCharacter.getWeapon().equals("fancy")) {
            fancyBow.setEquipped(true);
        }
        if (playerCharacter.getWeapon().equals("basic")) {
            extravagantBow.setEquipped(true);
        }
        itemAdapter.notifyDataSetChanged();
    }

    /**
     * when the purchase button is clicked this will be executed.
     * this checks if you are able to purchase the item, if not Nitram will let you know, otherwise
     * it will go through and let you buy it and update your gold, etc.
     * @param view
     */
    public void purchaseButton(View view) {
        ImageView nitramIV = findViewById(R.id.nitramImageView);
        itemAdapter.setMostRecentlyClickedPosition(itemAdapter.getMostRecentlyClickedPosition());
        //check if the player has enough gold
        if (playerCharacter.getGold() >= itemAdapter.currentItem.getPrice()) {
            itemAdapter.setMostRecentlyClickedPosition(itemAdapter.getMostRecentlyClickedPosition());
            //change Nitram image to thanks
            nitramIV.setImageResource(R.drawable.nitram_thanks);
            //setPurchased, take the gold away
            itemAdapter.currentItem.setPurchased(true);
            playerCharacter.setGold(playerCharacter.getGold() - itemAdapter.currentItem.getPrice());
            itemAdapter.currentItem.setPrice(0);
            weaponsOwned.add(itemAdapter.currentItem.getWeaponType());
            //update the UI
            TextView goldValueTV = findViewById(R.id.goldValueTextView);
            goldValueTV.setText(String.valueOf(playerCharacter.getGold()));
            //set their weapon to the new one they just bought
            weaponEquip();
            itemAdapter.notifyDataSetChanged();
            //update the files
            updateShopFile();
            updateCharacterFile();
        } else {
            //if the player does not have enough gold
            nitramIV.setImageResource(R.drawable.nitram_not_enough_gold);
        }
    }

    /**
     * this equips this weapon on to your character. We have to make sure to check if the weapon
     * is already equipped, as well as take into account all possibilities for each class. The code
     * is very similar for each, but tailored in regards to the different weapons.
     */
    public void weaponEquip() {
        if (playerCharacter.getWeapon().equals(itemAdapter.currentItem.getWeaponType())) {
            Toast alreadyEquippedToast = Toast.makeText(this, "this weapon is already equipped", Toast.LENGTH_LONG);
            alreadyEquippedToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 0);
            alreadyEquippedToast.show();
        }
        else {
            //KNIGHT + SWORD
            if (playerCharacter.getProfession().equals("Knight")) {
                //set whatever the current weapon equipped to false (unequipped)
                if (playerCharacter.getWeapon().equals("basic")) {
                    basicSword.setEquipped(false);

                }
                if (playerCharacter.getWeapon().equals("fancy")) {
                    fancySword.setEquipped(false);
                }
                if (playerCharacter.getWeapon().equals("extravagant")) {
                    extravagantSword.setEquipped(false);
                }
                //equip the new weapon and set to true
                playerCharacter.setWeapon(itemAdapter.currentItem.getWeaponType());
                if (playerCharacter.getWeapon().equals("basic")) {
                    basicSword.setEquipped(true);
                    playerCharacter.setWeaponmod(1);
                }
                if (playerCharacter.getWeapon().equals("fancy")) {
                    fancySword.setEquipped(true);
                    playerCharacter.setWeaponmod(5);
                }
                if (playerCharacter.getWeapon().equals("extravagant")) {
                    extravagantSword.setEquipped(true);
                    playerCharacter.setWeaponmod(10);
                }
            }
            //MAGE + STAFF
            if (playerCharacter.getProfession().equals("Mage")) {
                //set whatever the current weapon equipped to false (unequipped)
                if (playerCharacter.getWeapon().equals("basic")) {
                    basicStaff.setEquipped(false);
                }
                if (playerCharacter.getWeapon().equals("fancy")) {
                    fancyStaff.setEquipped(false);
                }
                if (playerCharacter.getWeapon().equals("extravagant")) {
                    extravagantStaff.setEquipped(false);
                }
                //equip the new weapon and set to true
                playerCharacter.setWeapon(itemAdapter.currentItem.getWeaponType());
                if (playerCharacter.getWeapon().equals("basic")) {
                    basicStaff.setEquipped(true);
                    playerCharacter.setWeaponmod(1);
                }
                if (playerCharacter.getWeapon().equals("fancy")) {
                    fancyStaff.setEquipped(true);
                    playerCharacter.setWeaponmod(5);
                }
                if (playerCharacter.getWeapon().equals("extravagant")) {
                    extravagantStaff.setEquipped(true);
                    playerCharacter.setWeaponmod(10);
                }
            }
            //RANGER + BOW
            if (playerCharacter.getProfession().equals("Ranger")) {
                //set whatever the current weapon equipped to false (unequipped)
                if (playerCharacter.getWeapon().equals("basic")) {
                    basicBow.setEquipped(false);
                }
                if (playerCharacter.getWeapon().equals("fancy")) {
                    fancyBow.setEquipped(false);
                }
                if (playerCharacter.getWeapon().equals("extravagant")) {
                    extravagantBow.setEquipped(false);
                }
                //equip the new weapon and set to true
                playerCharacter.setWeapon(itemAdapter.currentItem.getWeaponType());
                if (playerCharacter.getWeapon().equals("basic")) {
                    basicBow.setEquipped(true);
                    playerCharacter.setWeaponmod(1);
                }
                if (playerCharacter.getWeapon().equals("fancy")) {
                    fancyBow.setEquipped(true);
                    playerCharacter.setWeaponmod(5);
                }
                if (playerCharacter.getWeapon().equals("extravagant")) {
                    extravagantBow.setEquipped(true);
                    playerCharacter.setWeaponmod(10);
                }
            }
            //let the player know the weapon has successfully been equipped
            //they will also physically see it on their character on the home screen
            Toast equipToast = Toast.makeText(this, "you equipped the weapon", Toast.LENGTH_LONG);
            equipToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 0);
            equipToast.show();
            itemAdapter.notifyDataSetChanged();
        }
    }

    /**
     * when the equip button is pressed this will call the previous function to equip the item
     * @param view
     */
    public void equipButton(View view) {
        itemAdapter.setMostRecentlyClickedPosition(itemAdapter.getMostRecentlyClickedPosition());
        weaponEquip();
        updateCharacterFile();

    }

    /**
     * when the home button is pressed this will return the user to the home screen. The purpose of this
     * function is that it will call onCreate again and update the screen rather than just hitting the back
     * button
     * @param view
     */
    public void buttonBackFromHelp(View view) {
        Intent backFromShopIntent = new Intent(ShopScreenActivity.this, MainActivity.class);
        ShopScreenActivity.this.startActivity(backFromShopIntent);
    }

}

