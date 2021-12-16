package com.example.questmanager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DailyQuestScreenAdapter extends ArrayAdapter<DailyQuest> {
    DailyQuestFragment parentFragment;
    private int mostRecentlyClickedPosition;
    /**
     * Constructor for Element Adapter. Calls super constructor and sets MostRecentlyClickedPosition
     * to -1
     * @param context Activity the method is called from
     * @param questList The ArrayList to get the data from for the ArrayAdapter
     */
    public DailyQuestScreenAdapter(Activity context, ArrayList<DailyQuest> questList,DailyQuestFragment parent){
        super(context, 0, questList);
        mostRecentlyClickedPosition = -1;
        parentFragment = parent;
    }

    /**
     * Updates data for the parent ViewGroup (ListView in this case)
     * @param position int, the index within @param parent that @param convertView is located at
     * @param convertView View, element of @param parent to be updated
     * @param parent ViewGroup, the container for the Views that the ArrayAdapter is updating
     * @return View, the updated element of @param parent
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View questItemView = convertView;
        if (questItemView == null){//If a new view needs to be created, assign it's layout
            questItemView = LayoutInflater.from(getContext()).inflate(R.layout.daily_quest_item,parent,false);

        }

        //Modify position indicator
        TextView questNumTV = questItemView.findViewById(R.id.dailyQuestNumTV);
        questNumTV.setText(String.valueOf(position+1) + ".");

        DailyQuest currentQuest = getItem(position);
        TextView questName = questItemView.findViewById(R.id.dailyQuestNameTV);
        questName.setText(currentQuest.getName());
        CheckBox completeCB = questItemView.findViewById(R.id.dailyQuestCompleteCB);
        completeCB.setOnClickListener(parentFragment.completeDailyQuestCBListener);
        if  (currentQuest.isCompleted()){
            completeCB.setChecked(true);
            completeCB.setClickable(false);
        }
        else{
            completeCB.setChecked(false);
            completeCB.setClickable(true);
        }

        //Modify button visibility (and TextView / EditText)
        Button deselectButton = questItemView.findViewById(R.id.dailyQuestDeselectButton);
        Button deleteButton = questItemView.findViewById(R.id.dailyQuestDeleteButton);
        TextView completeTV = questItemView.findViewById(R.id.dailyQuestCompleteTV);
        deselectButton.setOnClickListener(parentFragment.deselectDailyQuestButtonListener);
        deleteButton.setOnClickListener(parentFragment.deleteDailyQuestButtonListener);
        if (position == mostRecentlyClickedPosition){
            deselectButton.setVisibility(View.VISIBLE);
            if (position > 7) {
                deleteButton.setVisibility(View.VISIBLE);
            }
            completeTV.setVisibility(View.VISIBLE);
            completeCB.setVisibility(View.VISIBLE);
        }else{
            deselectButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            completeTV.setVisibility(View.GONE);
            if (!currentQuest.isCompleted()) {
                completeCB.setVisibility(View.GONE);
            }
        }

        return questItemView ;
    }
    /**
     * Setter for mostRecentlyClickedPosition
     * @param mostRecentlyClickedPosition int, index in the ListView that was most recently clicked
     */
    public void setMostRecentlyClickedPosition(int mostRecentlyClickedPosition) {
        this.mostRecentlyClickedPosition = mostRecentlyClickedPosition;
    }
}
