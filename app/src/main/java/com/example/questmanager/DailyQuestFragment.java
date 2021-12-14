package com.example.questmanager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Instant;
import java.util.ArrayList;

public class DailyQuestFragment extends Fragment{

    int size = 0; //Keeps track of the amount of quests
    int mostRecentElement = -1; //Holds position in the ListView of the most recently clicked element;
    View mostRecentView; //Holds the most recently clicked element of the ListView
    String lastAccessDate = "never";
    View rootView;
    Activity fragContext;
    Character playerCharacter;

    ArrayList<DailyQuest> dailyQuestArray; //Array of the list names
    DailyQuestScreenAdapter dailyQuestAdapter;

    public DailyQuestFragment () { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_daily_quest_screen,container,false);

        fragContext = getActivity();
        dailyQuestArray = new ArrayList<DailyQuest>();
        getDailyQuestArrayFromFile(); //Load from file

        readCharacterFile();

        dailyQuestAdapter = new DailyQuestScreenAdapter(fragContext,dailyQuestArray,this);
        ListView dailyQuestListView = rootView.findViewById(R.id.dailyQuestLV);
        dailyQuestListView.setAdapter(dailyQuestAdapter); //assign questAdapter to the listView
        dailyQuestListView.setOnItemClickListener(dailyQuestLVListener); //assign OnItemClickListener to ListView

        Button newDailyQuestButton = rootView.findViewById(R.id.newDailyQuestButton);
        newDailyQuestButton.setOnClickListener(newDailyQuestButtonListener);
        Button addDailyQuestButton = rootView.findViewById(R.id.addDailyQuestButton);
        addDailyQuestButton.setOnClickListener(addDailyQuestButtonListener);
        Button backButton = rootView.findViewById(R.id.buttonBackFromDailyQuest);
        backButton.setOnClickListener(homeDailyQuestButtonListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String newDate = getDate();
            if (!lastAccessDate.equals(newDate)){
                lastAccessDate = newDate;
                resetDailyQuests();
                dailyQuestAdapter.notifyDataSetChanged();
                updateDQFile();
            }
        }
        return rootView;
    }

    View.OnClickListener newDailyQuestButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonNewDQ(view);
        }
    };
    View.OnClickListener addDailyQuestButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonAddDQ(view);
        }
    };
    View.OnClickListener deleteDailyQuestButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonDQDelete(view);
        }
    };
    View.OnClickListener deselectDailyQuestButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonDQDeselect(view);
        }
    };
    View.OnClickListener completeDailyQuestCBListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkBoxDQComplete(view);
        }
    };
    View.OnClickListener homeDailyQuestButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonBackFromDailyQuest(view);
        }
    };


    AdapterView.OnItemClickListener dailyQuestLVListener = new AdapterView.OnItemClickListener() {
        /**
         * Receives clicks within ListView questLV, updates the mostRecentlyClickedPosition,
         * and notifies the ArrayAdapter to update the ListView (sets buttons of the clicked view
         * element to visible and buttons of all other elements to gone)
         * Saves the most recently clicked position and view
         * @param adapterView the view that the click happened within (ListView listLV)
         * @param view the element(View) in adapterView that was clicked
         * @param i position in adapterView of view
         * @param l the row id of the item that was clicked (id of parameter view?)
         */
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            mostRecentView = view;
            mostRecentElement = i;
            dailyQuestAdapter.setMostRecentlyClickedPosition(i);
            dailyQuestAdapter.notifyDataSetChanged();
        }
    };

    public void buttonBackFromDailyQuest (View view){
        Intent backFromHelpIntent = new Intent(fragContext, MainActivity.class);
        startActivity(backFromHelpIntent);
    }

    public void checkBoxDQComplete(View view){
        dailyQuestArray.get(mostRecentElement).setCompleted(true);
        updateDQFile();

        // resolve experience and power gains
        int experience = playerCharacter.getCurexp();
        int maxexp = playerCharacter.getMaxexp();
        int curpower = playerCharacter.getCurpower();
        int maxpower = playerCharacter.getMaxpower();

        double experienceGain = 5 + 5 * playerCharacter.getSmt()/20.0;
        experience += (int) experienceGain;
        curpower += 5;

        if (experience >= maxexp) {
            playerCharacter.levelUp(experience);
        }
        else {
            playerCharacter.setCurexp(experience);
        }

        if (curpower > maxpower) { //Check for power beyond maximum
            curpower = maxpower;
        }
        playerCharacter.setCurpower(curpower);

        updateCharacterFile();

        dailyQuestAdapter.setMostRecentlyClickedPosition(-1);
        dailyQuestAdapter.notifyDataSetChanged();
    }

    public void buttonNewDQ(View view){
        EditText DQNameET = rootView.findViewById(R.id.dailyQuestNameET);
        DQNameET.setVisibility(View.VISIBLE);
        DQNameET.setText("");
        DQNameET.requestFocus();
        Button addDQButton = rootView.findViewById(R.id.addDailyQuestButton);
        addDQButton.setVisibility(View.VISIBLE);
    }

    public void buttonAddDQ(View view){
        EditText DQNameET = rootView.findViewById(R.id.dailyQuestNameET);
        String name = String.valueOf(DQNameET.getText());
        if (name.equals("")){
            Toast.makeText(fragContext,"Enter the quest name",Toast.LENGTH_LONG).show();
        }
        else {
            if (checkValidInput(name)) {
                DailyQuest newDQ = new DailyQuest(name, false);
                dailyQuestArray.add(newDQ);
                size++;
                DQNameET.setVisibility(View.GONE);
                Button addDQButton = rootView.findViewById(R.id.addDailyQuestButton);
                addDQButton.setVisibility(View.GONE);
                updateDQFile();
                dailyQuestAdapter.notifyDataSetChanged();
                InputMethodManager imm = (InputMethodManager) fragContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    boolean checkValidInput(String questName){
        for (int i = 0; i < size; i++){ //Check for repeat name
            if (questName.equals(dailyQuestArray.get(i).getName())){
                Toast.makeText(fragContext,"A quest with this name already exists",Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    public void buttonDQDeselect(View view){
        dailyQuestAdapter.setMostRecentlyClickedPosition(-1);
        dailyQuestAdapter.notifyDataSetChanged();
    }

    public void buttonDQDelete(View view){
        dailyQuestArray.remove(mostRecentElement);
        size--;
        dailyQuestAdapter.setMostRecentlyClickedPosition(-1);
        dailyQuestAdapter.notifyDataSetChanged();
        updateDQFile();
    }

    public void updateDQFile (){
        String writeString;
        writeString = String.valueOf(size);
        writeString += "\n";
        writeString += lastAccessDate;
        for (int i = 0; i < size; i++){ //Construct string to be written to the file
            writeString += "\n";
            writeString += dailyQuestArray.get(i).getName();
            writeString += "\n";
            if (dailyQuestArray.get(i).isCompleted()){
                writeString += "true";
            }
            else{
                writeString += "false";
            }
        }
        try {//write string to file
            FileOutputStream fos = fragContext.openFileOutput("dailyQuestList.txt", Context.MODE_PRIVATE);
            fos.write(writeString.getBytes());
        } catch (IOException e) {//Catch exceptions
            e.printStackTrace();
            Toast.makeText(fragContext,"Problem with output file",Toast.LENGTH_SHORT).show();
        }
    }

    public void getDailyQuestArrayFromFile(){
        File theFile = fragContext.getBaseContext().getFileStreamPath("dailyQuestList.txt");
        if (theFile.exists()){
            try {
                FileInputStream fis = fragContext.openFileInput("dailyQuestList.txt");
                InputStreamReader isReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isReader);
                String theLine = bufferedReader.readLine();
                size = Integer.parseInt(theLine); //Get size from file
                theLine = bufferedReader.readLine();
                lastAccessDate = theLine;
                for (int i = 0; i < size;i++){ //Get quests from file
                    String name;
                    boolean complete;
                    //get quest data
                    theLine = bufferedReader.readLine();
                    name = theLine;
                    theLine = bufferedReader.readLine();
                    complete = Boolean.parseBoolean(theLine);
                    //Create quest with read data and add to list
                    DailyQuest newQuest = new DailyQuest(name,complete);
                    dailyQuestArray.add(newQuest);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(fragContext,"Error loading quests",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            generatePresetDailyQuests();
        }
    }

    public void resetDailyQuests(){
        for(int i  = 0; i < size;i++){
            dailyQuestArray.get(i).setCompleted(false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    String getDate(){
        Instant date = Instant.now();
        String returnDate = date.toString();
        returnDate = returnDate.substring(0,10);
        return returnDate;
    }

    public void readCharacterFile() {
        File playerData = fragContext.getBaseContext().getFileStreamPath("playerData.txt");
        //looks for the file to which player character data is saved

        if (playerData.exists()) {
            //if the file is found, we will read the character object from it
            try {
                FileInputStream fis = fragContext.openFileInput("playerData.txt"); // opens file in read mode
                ObjectInputStream ois = new ObjectInputStream(fis);
                // allows us to read an object from the file, according to stack overflow
                playerCharacter = (Character) ois.readObject();

                // next two lines close the read mode file
                ois.close();
                fis.close();

            } catch (Exception e) { // error message
                Toast.makeText(fragContext, "Player data was found, but there was an error reading the file", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void updateCharacterFile() {
        File playerData = fragContext.getBaseContext().getFileStreamPath("playerData.txt");
        //looks for the file to which player character data is saved
        if (playerData.exists()) {
            try {
                FileOutputStream fos = fragContext.openFileOutput("playerData.txt", Context.MODE_PRIVATE);
                // makes the file and opens it in write mode
                ObjectOutputStream os = new ObjectOutputStream(fos);
                // allows us to write an object to the file, according to stack overflow
                os.writeObject(playerCharacter);
                // writes playerCharacter object to the file. next two lines close the write mode file
                os.close();
                fos.close();

            } catch (IOException e) {
                Toast.makeText(fragContext, "Problem with output file", Toast.LENGTH_SHORT).show();
                // placeholder error message
            }
        }
    }

    public void generatePresetDailyQuests(){
        DailyQuest makeBed = new DailyQuest("Make Bed",false);
        DailyQuest drinkWater = new DailyQuest("Drink water",false);
        DailyQuest eat = new DailyQuest("Eat Something",false);
        DailyQuest bathe = new DailyQuest("Take a Shower or Bath",false);
        DailyQuest brushTeeth = new DailyQuest("Brush Teeth",false);
        DailyQuest floss = new DailyQuest("Floss Teeth",false);
        DailyQuest exercise = new DailyQuest("Exercise",false);
        DailyQuest socialize = new DailyQuest("Socialize",false);
        dailyQuestArray.add(makeBed);
        dailyQuestArray.add(drinkWater);
        dailyQuestArray.add(eat);
        dailyQuestArray.add(bathe);
        dailyQuestArray.add(brushTeeth);
        dailyQuestArray.add(floss);
        dailyQuestArray.add(exercise);
        dailyQuestArray.add(socialize);
        size = 8;
    }
}
