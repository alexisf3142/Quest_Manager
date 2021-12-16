package com.example.questmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {
    ArrayList<HelpItem> helpList; // the list
    private HelpAdapter helpAdapter; // used to add and organize items in the list

    /* following calls method from helpAdapter that sets whichever list item was last clicked on
     * as the most recently clicked item, and updates the listView as a whole
     * this will be used to make the answer text visible only for the most recently clicked question*/
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            helpAdapter.setMostRecentlyClickedItemPosition(i);
            helpAdapter.notifyDataSetChanged();
        }
    };

    // following method brings user back to home screen if home button is pressed with an intent
    public void buttonBackFromHelp (View view){
        Intent backFromHelpIntent = new Intent(HelpActivity.this, MainActivity.class);
        HelpActivity.this.startActivity(backFromHelpIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help); // sets layout to help screen layout

        helpList = new ArrayList<HelpItem>(); // makes new list
        helpAdapter = new HelpAdapter(this, helpList); // makes adapter for the list
        ListView helpListView = findViewById(R.id.helpListView); // calls the listview in the help activity layout
        helpListView.setAdapter(helpAdapter); // attaches newly made adapter to the listview
        helpListView.setOnItemClickListener(listener); // attaches onClickListener to the listview

        // and then we have the adapter add all the list items to the list
        // Html.fromhtml is required for aText because they're Spanned, not strings
        helpAdapter.add(new HelpItem(getString(R.string.qGeneral), Html.fromHtml(getString(R.string.aGeneral))));
        helpAdapter.add(new HelpItem(getString(R.string.qHome), Html.fromHtml(getString(R.string.aHome))));
        helpAdapter.add(new HelpItem(getString(R.string.qQuest), Html.fromHtml(getString(R.string.aQuest))));
        helpAdapter.add(new HelpItem(getString(R.string.qProfile), Html.fromHtml(getString(R.string.aProfile))));
        helpAdapter.add(new HelpItem(getString(R.string.qShop), Html.fromHtml(getString(R.string.aShop))));
        helpAdapter.add(new HelpItem(getString(R.string.qDungeon), Html.fromHtml(getString(R.string.aDungeon))));
        helpAdapter.add(new HelpItem(getString(R.string.qCredits), Html.fromHtml(getString(R.string.aCredits))));
    }
}
