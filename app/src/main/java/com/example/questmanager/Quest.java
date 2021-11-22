package com.example.questmanager;

public class Quest {
    private String name,dueDate,dueTime,description;
    private int difficulty;

    public Quest(String name,String dueDate, String dueTime, String description, int difficulty){
        this.name = name;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.description = description;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public String getDescription() {
        return description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
