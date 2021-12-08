package com.example.questmanager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QuestFragment extends Fragment {

    View rootView;
    Activity fragContext;

    int size = 0; //Keeps track of the amount of quests
    int compSize = 0;
    int mostRecentElement = -1; //Holds position in the ListView of the most recently clicked element;
    View mostRecentView; //Holds the most recently clicked element of the ListView

    ArrayList<Quest> compQuestArray;
    ArrayList<Quest> questArray; //Array of the list names
    QuestScreenAdapter questAdapter;

    public QuestFragment () {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_quest_screen, container, false);

        fragContext = getActivity();
        //Set button onClickListeners
        Button newQuestButton = rootView.findViewById(R.id.newQuestButton);
        newQuestButton.setOnClickListener(newQuestButtonListener);
        Button addQuestButton = rootView.findViewById(R.id.addQuestButton);
        addQuestButton.setOnClickListener(addQuestButtonListener);
        Button confirmButton = rootView.findViewById(R.id.confirmQuestChangesButton);
        confirmButton.setOnClickListener(confirmChangesButtonListener);

        questArray = new ArrayList<Quest>();
        getQuestArrayFromFile(); //Load from file

        compQuestArray = new ArrayList<Quest>();
        getCompQuestArrayFromFile();

        questAdapter = new QuestScreenAdapter(fragContext,questArray,this);
        ListView questListView = rootView.findViewById(R.id.questLV);
        questListView.setAdapter(questAdapter); //assign questAdapter to the listView
        questListView.setOnItemClickListener(questLVListener); //assign OnItemClickListener to ListView
        
        return rootView;
    }

    View.OnClickListener newQuestButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            newQuestButton(view);
        }
    };
    View.OnClickListener addQuestButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addQuestButton(view);
        }
    };
    View.OnClickListener confirmChangesButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonConfirm(view);
        }
    };
    View.OnClickListener editQuestButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonEdit(view);
        }
    };
    View.OnClickListener deselectQuestButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonDeselect(view);
        }
    };
    View.OnClickListener completeQuestButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonComplete(view);
        }
    };
    View.OnClickListener moveQuestButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonMove(view);
        }
    };
    View.OnClickListener deleteQuestButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            buttonDelete(view);
        }
    };

    
    AdapterView.OnItemClickListener questLVListener = new AdapterView.OnItemClickListener() {
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
            questAdapter.setMostRecentlyClickedPosition(i);
            questAdapter.notifyDataSetChanged();
        }
    };

    /**
     * Allows the user to add a new quest by setting the EditText for all quest parameters
     * and the button to add a quest to visible
     * @param view the view that was clicked and triggered the method
     */
    public void newQuestButton (View view){
        EditText questNameET = rootView.findViewById(R.id.questNameET);
        questNameET.setVisibility(View.VISIBLE);
        questNameET.requestFocus(); //Set cursor in questNameET
        EditText questDueDateET = rootView.findViewById(R.id.questDueDateET);
        questDueDateET.setVisibility(View.VISIBLE);
        EditText questDueTimeET = rootView.findViewById(R.id.questDueTimeET);
        questDueTimeET.setVisibility(View.VISIBLE);
        EditText questDescriptionET = rootView.findViewById(R.id.questDescriptionET);
        questDescriptionET.setVisibility(View.VISIBLE);
        EditText questDifficultyET = rootView.findViewById(R.id.questDifficultyET);
        questDifficultyET.setVisibility(View.VISIBLE);
        Button addButton = rootView.findViewById(R.id.addQuestButton);
        addButton.setVisibility(View.VISIBLE);
        Button confirmButton = rootView.findViewById((R.id.confirmQuestChangesButton));
        confirmButton.setVisibility(View.GONE);

        questNameET.setText("");
        questDueDateET.setText("");
        questDueTimeET.setText("");
        questDescriptionET.setText("");
        questDifficultyET.setText("");
    }

    /**
     * add a new quest to the ListView if the name is valid
     * @param view the view that was clicked and triggered the method
     */
    public void addQuestButton (View view){
        if (checkValidInput()) { //if input is valid
            EditText questNameET = rootView.findViewById(R.id.questNameET);
            String name = String.valueOf(questNameET.getText());
            if (checkValidName(name)) {
                EditText questDueDateET = rootView.findViewById(R.id.questDueDateET);
                String dueDate = String.valueOf(questDueDateET.getText());
                EditText questDueTimeET = rootView.findViewById(R.id.questDueTimeET);
                String dueTime = String.valueOf(questDueTimeET.getText());
                EditText questDescriptionET = rootView.findViewById(R.id.questDescriptionET);
                String description = String.valueOf(questDescriptionET.getText());
                EditText questDifficultyET = rootView.findViewById(R.id.questDifficultyET);
                int difficulty = Integer.parseInt(String.valueOf(questDifficultyET.getText()));

                Quest newQuest = new Quest(name, dueDate, dueTime, description, difficulty);
                questArray.add(newQuest);
                size++;
                if (size == 1) {
                    topLineAny();
                }
                updateMainFile();
                questAdapter.notifyDataSetChanged();

                questNameET.setVisibility(View.GONE);
                questDueDateET.setVisibility(View.GONE);
                questDueTimeET.setVisibility(View.GONE);
                questDescriptionET.setVisibility(View.GONE);
                questDifficultyET.setVisibility(View.GONE);
                Button addButton = rootView.findViewById(R.id.addQuestButton);
                addButton.setVisibility(View.GONE);
                //Hide keyboard
                InputMethodManager imm = (InputMethodManager) fragContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public boolean checkValidInput(){
        EditText questNameET = rootView.findViewById(R.id.questNameET);
        String questName = String.valueOf(questNameET.getText());
        EditText questDueDateET = rootView.findViewById(R.id.questDueDateET);
        EditText questDueTimeET = rootView.findViewById(R.id.questDueTimeET);
        EditText questDescriptionET = rootView.findViewById(R.id.questDescriptionET);
        EditText questDifficultyET = rootView.findViewById(R.id.questDifficultyET);
        boolean fieldEmpty = false;
        if (isEmpty(questName)) {
            fieldEmpty = true;
        }
        if (isEmpty(String.valueOf(questDueDateET.getText()))) {
            fieldEmpty = true;
        }
        if (isEmpty(String.valueOf(questDueTimeET.getText()))) {
            fieldEmpty = true;
        }
        if (isEmpty(String.valueOf(questDescriptionET.getText()))) {
            fieldEmpty = true;
        }
        if (isEmpty(String.valueOf(questDifficultyET.getText()))) {
            fieldEmpty = true;
        }

        if (fieldEmpty){
            Toast.makeText(fragContext,"Fill in all fields",Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            int difficulty;
            try {
                difficulty = Integer.parseInt(String.valueOf(questDifficultyET.getText()));
            }
            catch (NumberFormatException nfe){
                difficulty = 0;
            }
            if (difficulty <= 5 && difficulty >= 1){
                return true;
            }
            Toast.makeText(fragContext,"Difficulty must be a number from 1 to 5",Toast.LENGTH_LONG).show();
            return false;
        }

    }

    public boolean isEmpty(String value){
        if (value.equals("")){
            return true;
        }
        return false;
    }

    /**
     * Checks for a valid quest name.
     * If the name already exists outputs a toast saying so and returns false.
     * Returns true otherwise.
     * @param name String, the name entered by the user for the new quest
     * @return boolean, true if a list was added, false otherwise
     */
    public boolean checkValidName(String name){
        for (int i = 0; i < size; i++){ //Check for repeat name
            if (name.equals(questArray.get(i).getName())){
                Toast.makeText(fragContext,"A quest with fragContext name already exists",Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    /**
     * Removes the most recently clicked element(list) of the ListView from the ListView
     * and deletes it's associated file
     * @param view the view that was clicked and triggered the method
     */
    public void buttonDelete(View view){
        questArray.remove(mostRecentElement);
        size--;
        updateMainFile();
        //update list view
        questAdapter.setMostRecentlyClickedPosition(-1);
        questAdapter.notifyDataSetChanged();
        if (size == 0){ //update top line
            topLineNone();
        }
    }

    /**
     * Hides the buttons of the most recently clicked element of the ListView
     * This results in no elements with visible buttons
     * @param view the view that was clicked and triggered the method
     */
    public void buttonDeselect(View view){
        questAdapter.setMostRecentlyClickedPosition(-1);
        questAdapter.notifyDataSetChanged();
    }

    /**
     * Moves an element(list) of the ListView to the position indicated by the user. If the position
     * is out of bounds or not a number, a toast will be displayed saying so.
     * @param view the view that was clicked and triggered the method
     */
    public void buttonMove(View view){
        EditText movePosET = mostRecentView.findViewById(R.id.questMovePosET);
        int position;
        //Check if input is a number, if so set position to input, otherwise set position to 0
        try {
            position = Integer.parseInt(String.valueOf(movePosET.getText()));
        }
        catch (NumberFormatException nfe){
            position = 0;
        }
        position = position-1;
        if (position < size && position >= 0){ //Check if position is in bounds
            Quest moveQuest = questArray.get(mostRecentElement); //copy quest to be moved
            questArray.remove(mostRecentElement); //Remove quest to be moved
            questArray.add(position,moveQuest); //Add quest to be moved into appropriate position
            updateMainFile();
            questAdapter.setMostRecentlyClickedPosition(-1); //update listView
            questAdapter.notifyDataSetChanged();
            //Hide Keyboard
            InputMethodManager imm = (InputMethodManager)fragContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        else{//Toast indicating invalid position
            Toast.makeText(fragContext,"Position is out of bounds or not a number",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Move to the secondary screen which will display the list of elements associated with
     * the list that was selected
     * @param view the view that was clicked and triggered the method
     */
    public void buttonEdit(View view){
        EditText questNameET = rootView.findViewById(R.id.questNameET);
        questNameET.setVisibility(View.VISIBLE);
        questNameET.setText(questArray.get(mostRecentElement).getName());
        EditText questDueDateET = rootView.findViewById(R.id.questDueDateET);
        questDueDateET.setVisibility(View.VISIBLE);
        questDueDateET.setText(questArray.get(mostRecentElement).getDueDate());
        EditText questDueTimeET = rootView.findViewById(R.id.questDueTimeET);
        questDueTimeET.setVisibility(View.VISIBLE);
        questDueTimeET.setText(questArray.get(mostRecentElement).getDueTime());
        EditText questDescriptionET = rootView.findViewById(R.id.questDescriptionET);
        questDescriptionET.setVisibility(View.VISIBLE);
        questDescriptionET.setText(questArray.get(mostRecentElement).getDescription());
        EditText questDifficultyET = rootView.findViewById(R.id.questDifficultyET);
        questDifficultyET.setVisibility(View.VISIBLE);
        questDifficultyET.setText(String.valueOf(questArray.get(mostRecentElement).getDifficulty()));
        Button addButton = rootView.findViewById(R.id.addQuestButton);
        addButton.setVisibility(View.GONE);
        Button confirmButton = rootView.findViewById((R.id.confirmQuestChangesButton));
        confirmButton.setVisibility(View.VISIBLE);
    }

    public void buttonConfirm(View view){
        if (checkValidInput()) { //if input is valid
            EditText questNameET = rootView.findViewById(R.id.questNameET);
            String name = String.valueOf(questNameET.getText());
            boolean update = true;
            if (!name.equals(questArray.get(mostRecentElement).getName())){
                update = checkValidName(name);
            }
            if (update) {
                questArray.get(mostRecentElement).setName(name);
                EditText questDueDateET = rootView.findViewById(R.id.questDueDateET);
                questArray.get(mostRecentElement).setDueDate(String.valueOf(questDueDateET.getText()));
                EditText questDueTimeET = rootView.findViewById(R.id.questDueTimeET);
                questArray.get(mostRecentElement).setDueTime(String.valueOf(questDueTimeET.getText()));
                EditText questDescriptionET = rootView.findViewById(R.id.questDescriptionET);
                questArray.get(mostRecentElement).setDescription(String.valueOf(questDescriptionET.getText()));
                EditText questDifficultyET = rootView.findViewById(R.id.questDifficultyET);
                questArray.get(mostRecentElement).setDifficulty(Integer.parseInt(String.valueOf(questDifficultyET.getText())));

                updateMainFile();
                questAdapter.notifyDataSetChanged();

                questNameET.setVisibility(View.GONE);
                questNameET.setText("");
                questDueDateET.setVisibility(View.GONE);
                questDueDateET.setText("");
                questDueTimeET.setVisibility(View.GONE);
                questDueTimeET.setText("");
                questDescriptionET.setVisibility(View.GONE);
                questDescriptionET.setText("");
                questDifficultyET.setVisibility(View.GONE);
                questDifficultyET.setText("");
                Button confirmButton = rootView.findViewById(R.id.confirmQuestChangesButton);
                confirmButton.setVisibility(View.GONE);
                //Hide keyboard
                InputMethodManager imm = (InputMethodManager) fragContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void buttonComplete(View view){
        compQuestArray.add(0,questArray.get(mostRecentElement));
        compSize++;
        updateCompFile();
        buttonDelete(view);
        /** Add code to resolve experience and power gains */
    }

    /**
     * Writes the list of quests to file. First line is the amount of quests, each following block
     * of 5 lines is the data of one quest.
     */
    public void updateMainFile (){
        String writeString;
        writeString = String.valueOf(size);
        for (int i = 0; i < size; i++){ //Construct string to be written to the file
            writeString += "\n";
            writeString += questArray.get(i).getName();
            writeString += "\n";
            writeString += questArray.get(i).getDueDate();
            writeString += "\n";
            writeString += questArray.get(i).getDueTime();
            writeString += "\n";
            writeString += questArray.get(i).getDescription();
            writeString += "\n";
            writeString += String.valueOf(questArray.get(i).getDifficulty());
        }
        try {//write string to file
            FileOutputStream fos = fragContext.openFileOutput("questList.txt", Context.MODE_PRIVATE);
            fos.write(writeString.getBytes());
        } catch (IOException e) {//Catch exceptions
            e.printStackTrace();
            Toast.makeText(fragContext,"Problem with output file",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Update top line to indicate that there are no lists
     */
    public void topLineNone(){
        TextView topLine = rootView.findViewById(R.id.qsTopLineTV);
        topLine.setText("You have no quests.");
    }

    /**
     * Update top line to indicate that there are lists
     */
    public void topLineAny(){
        TextView topLine = rootView.findViewById(R.id.qsTopLineTV);
        topLine.setText("Your Quests:");
    }

    /**
     * Read the file of list names. Get the size from file and put the names into questArray
     */
    public void getQuestArrayFromFile(){
        File theFile = fragContext.getBaseContext().getFileStreamPath("questList.txt");
        if (theFile.exists()){
            try {
                FileInputStream fis = fragContext.openFileInput("questList.txt");
                InputStreamReader isReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isReader);
                String theLine = bufferedReader.readLine();
                size = Integer.parseInt(theLine); //Get size from file
                for (int i = 0; i < size;i++){ //Get quests from file
                    String name,dueDate,dueTime,description;
                    int difficulty;
                    //get quest data
                    theLine = bufferedReader.readLine();
                    name = theLine;
                    theLine = bufferedReader.readLine();
                    dueDate = theLine;
                    theLine = bufferedReader.readLine();
                    dueTime = theLine;
                    theLine = bufferedReader.readLine();
                    description = theLine;
                    theLine = bufferedReader.readLine();
                    difficulty = Integer.parseInt(theLine);
                    //Create quest with read data and add to list
                    Quest newQuest = new Quest(name,dueDate,dueTime,description,difficulty);
                    questArray.add(newQuest);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(fragContext,"Error loading quests",Toast.LENGTH_SHORT).show();
            }
            if (size > 0){ //Update top line if necessary
                topLineAny();
            }
        }
    }

    public void getCompQuestArrayFromFile(){
        File theFile = fragContext.getBaseContext().getFileStreamPath("compQuestList.txt");
        if (theFile.exists()) {
            try {
                FileInputStream fis = fragContext.openFileInput("compQuestList.txt");
                InputStreamReader isReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isReader);
                String theLine = bufferedReader.readLine();
                compSize = Integer.parseInt(theLine); //Get size from file
                for (int i = 0; i < compSize; i++) { //Get quests from file
                    String name, dueDate, dueTime, description;
                    int difficulty;
                    //get quest data
                    theLine = bufferedReader.readLine();
                    name = theLine;
                    theLine = bufferedReader.readLine();
                    dueDate = theLine;
                    theLine = bufferedReader.readLine();
                    dueTime = theLine;
                    theLine = bufferedReader.readLine();
                    description = theLine;
                    theLine = bufferedReader.readLine();
                    difficulty = Integer.parseInt(theLine);
                    //Create quest with read data and add to list
                    Quest newQuest = new Quest(name, dueDate, dueTime, description, difficulty);
                    compQuestArray.add(newQuest);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(fragContext, "Error loading quests", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void updateCompFile (){
        String writeString;
        writeString = String.valueOf(compSize);
        for (int i = 0; i < compSize; i++){ //Construct string to be written to the file
            writeString += "\n";
            writeString += compQuestArray.get(i).getName();
            writeString += "\n";
            writeString += compQuestArray.get(i).getDueDate();
            writeString += "\n";
            writeString += compQuestArray.get(i).getDueTime();
            writeString += "\n";
            writeString += compQuestArray.get(i).getDescription();
            writeString += "\n";
            writeString += String.valueOf(compQuestArray.get(i).getDifficulty());
        }
        try {//write string to file
            FileOutputStream fos = fragContext.openFileOutput("compQuestList.txt", Context.MODE_PRIVATE);
            fos.write(writeString.getBytes());
        } catch (IOException e) {//Catch exceptions
            e.printStackTrace();
            Toast.makeText(fragContext,"Problem with output file",Toast.LENGTH_SHORT).show();
        }
    }

}