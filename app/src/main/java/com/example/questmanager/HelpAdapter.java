package com.example.questmanager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class HelpAdapter extends ArrayAdapter<HelpItem> {

    private int mostRecentlyClickedItemPosition; // used to know which item was clicked last

    public HelpAdapter(Activity context, ArrayList<HelpItem> HelpList) { // constructor for the class
        super(context,0, HelpList);
        mostRecentlyClickedItemPosition = -1; // default value
    }

    public void setMostRecentlyClickedItemPosition(int mostRecentlyClickedItemPosition) {
        this.mostRecentlyClickedItemPosition = mostRecentlyClickedItemPosition;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView; // not necessary?

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.help_item_layout, parent, false);
        } // calls the relevant item layout

        HelpItem currentHelpItem = getItem(position);

        // following two lines fill the layout's question text view with the current help item's qText
        TextView questionTV = listItemView.findViewById(R.id.questionTextView);
        questionTV.setText(currentHelpItem.getQuestion());

        // following two lines fill the layout's answer text view with the current help item's aText
        TextView answerTV = listItemView.findViewById(R.id.answerTextView);
        answerTV.setText(currentHelpItem.getAnswer());
        // and then this if/else changes the visibility of the answer text
        if (position == mostRecentlyClickedItemPosition) {
            answerTV.setVisibility(View.VISIBLE);
            // makes answer visible if this item was the last one clicked on
        } else {
            answerTV.setVisibility(View.GONE);
        } // makes answer invisible if this item was not the last one clicked on

        return listItemView; // saves the listview
    }
}
