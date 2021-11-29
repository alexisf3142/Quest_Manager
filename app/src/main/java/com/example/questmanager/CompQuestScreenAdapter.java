package com.example.questmanager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CompQuestScreenAdapter extends ArrayAdapter<Quest> {
    /**
     * Constructor for Element Adapter. Calls super constructor and sets MostRecentlyClickedPosition
     * to -1
     * @param context Activity the method is called from
     * @param questList The ArrayList to get the data from for the ArrayAdapter
     */
    public CompQuestScreenAdapter(Activity context, ArrayList<Quest> questList){
        super(context, 0, questList);
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
            questItemView = LayoutInflater.from(getContext()).inflate(R.layout.comp_quest_item,parent,false);

        }

        //Modify position indicator
        TextView questNumTV = questItemView.findViewById(R.id.compQuestNumTV);
        questNumTV.setText(String.valueOf(position+1) + ".");

        Quest currentQuest = getItem(position);
        TextView questName = questItemView.findViewById(R.id.compQuestNameTV);
        questName.setText(currentQuest.getName());
        TextView questDueDate = questItemView.findViewById(R.id.compQuestDueDateTV);
        questDueDate.setText(currentQuest.getDueDate());
        TextView questDueTime = questItemView.findViewById(R.id.compQuestDueTimeTV);
        questDueTime.setText(currentQuest.getDueTime());
        TextView questDescription = questItemView.findViewById(R.id.compQuestDescriptionTV);
        questDescription.setText(currentQuest.getDescription());

        return questItemView ;
    }
}
