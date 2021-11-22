package com.example.questmanager;

public class Achievement {
    private String achTitle, description;
    private boolean completed;

    /*
    KEY
    achTitle = achievement title
    description = description of achievement
    completed = bool for whether achievement is completed or not
     */

    public Achievement(String title, String description, boolean completed){
        this.achTitle = title;
        this.description = description;
        this.completed = completed;
    }

    public String getAchTitle() {
        return achTitle;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setAchTitle(String achTitle) {
        this.achTitle = achTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
