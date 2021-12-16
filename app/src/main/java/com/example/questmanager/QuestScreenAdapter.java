package com.example.questmanager;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.util.ArrayList;

public class QuestScreenAdapter extends ArrayAdapter<Quest> {
    QuestFragment parentFragment;
    private int mostRecentlyClickedPosition;
    boolean mostRecentCompletable;
    /**
     * Constructor for Element Adapter. Calls super constructor and sets MostRecentlyClickedPosition
     * to -1
     * @param context Activity the method is called from
     * @param questList The ArrayList to get the data from for the ArrayAdapter
     */
    public QuestScreenAdapter(Activity context, ArrayList<Quest> questList,QuestFragment parent){
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
            questItemView = LayoutInflater.from(getContext()).inflate(R.layout.quest_lv_item,parent,false);

        }

        //Modify position indicator
        TextView questNumTV = questItemView.findViewById(R.id.questNumTV);
        questNumTV.setText(String.valueOf(position+1) + ".");

        Quest currentQuest = getItem(position);
        TextView questName = questItemView.findViewById(R.id.questNameTV);
        questName.setText(currentQuest.getName());
        TextView questDueDate = questItemView.findViewById(R.id.questDueDateTV);
        questDueDate.setText(currentQuest.getDueDate());
        TextView questDueTime = questItemView.findViewById(R.id.questDueTimeTV);
        questDueTime.setText(currentQuest.getDueTime());
        TextView questDescription = questItemView.findViewById(R.id.questDescriptionTV);
        questDescription.setText(currentQuest.getDescription());

        //Modify button visibility (and TextView / EditText)
        Button editButton = questItemView.findViewById(R.id.questEditButton);
        Button deselectButton = questItemView.findViewById(R.id.questDeselectButton);
        Button completeButton = questItemView.findViewById(R.id.questCompleteButton);
        Button moveButton = questItemView.findViewById(R.id.questMoveButton);
        TextView movePosTV = questItemView.findViewById(R.id.questMovePosTV);
        TextView movePosET = questItemView.findViewById(R.id.questMovePosET);
        Button deleteButton = questItemView.findViewById(R.id.questDeleteButton);
        editButton.setOnClickListener(parentFragment.editQuestButtonListener);
        deselectButton.setOnClickListener(parentFragment.deselectQuestButtonListener);
        completeButton.setOnClickListener(parentFragment.completeQuestButtonListener);
        moveButton.setOnClickListener(parentFragment.moveQuestButtonListener);
        deleteButton.setOnClickListener(parentFragment.deleteQuestButtonListener);
        if (position == mostRecentlyClickedPosition){
            editButton.setVisibility(View.VISIBLE);
            deselectButton.setVisibility(View.VISIBLE);
            if (mostRecentCompletable) {
                completeButton.setVisibility(View.VISIBLE);
            }
            else{
                completeButton.setVisibility(View.GONE);
            }
            moveButton.setVisibility(View.VISIBLE);
            movePosTV.setVisibility(View.VISIBLE);
            movePosET.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        }else{
            editButton.setVisibility(View.GONE);
            deselectButton.setVisibility(View.GONE);
            completeButton.setVisibility(View.GONE);
            moveButton.setVisibility(View.GONE);
            movePosTV.setVisibility(View.GONE);
            movePosET.setVisibility(View.GONE);
            movePosET.setText("");
            deleteButton.setVisibility(View.GONE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String currentTime = getDateTime();
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

    public void setMostRecentCompletable(boolean completable){
        this.mostRecentCompletable = completable;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    String getDateTime(){
        Instant date = Instant.now();
        String returnDate = date.toString();
        returnDate = returnDate.substring(0,19);
        return returnDate;
    }
}
