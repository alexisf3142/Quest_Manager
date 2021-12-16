package com.example.questmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class QuestPagerAdapter extends FragmentPagerAdapter {

    public QuestPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) { //Return correct fragment
        switch (position) {
            case 0: return new QuestFragment();
            case 1: return new DailyQuestFragment();
            case 2: return new CompletedQuestFragment();
            default: return new QuestFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) { //Set tab names
        switch (position) {
            case 0: return "Quests";
            case 1: return "Daily Quests";
            case 2: return "Completed Quests";
            default: return "";
        }
    }

}
