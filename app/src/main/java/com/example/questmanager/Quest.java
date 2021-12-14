package com.example.questmanager;

public class Quest {
    private String name,dueDate,dueTime,description,creationDate;
    private int difficulty;
    private boolean completable;

    public Quest(String name,String dueDate, String dueTime, String description, int difficulty,String creationDate){
        this.name = name;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.description = description;
        this.difficulty = difficulty;
        this.creationDate = creationDate;
        this.completable = false;
    }

    public boolean isCompletable() {
        return completable;
    }

    public void setCompletable(boolean completable) {
        this.completable = completable;
    }

    public String getCreationDate() {
        return creationDate;
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
