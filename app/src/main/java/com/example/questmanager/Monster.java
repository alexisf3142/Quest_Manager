package com.example.questmanager;

import android.media.Image;

public class Monster {
    private String name;
    private int dmg, curPow, maxPow;

    public Monster(String name, int dmg, int maxPow) {
        this.name = name;
        this.dmg = dmg;
        this.maxPow = maxPow;
        this.curPow = maxPow;
    }

    public String getMonName() {
        return name;
    }

    public int getMonDmg() {
        return dmg;
    }

    public int getMonCurPow() {
        return curPow;
    }

    public int getMonMaxPow() {
        return maxPow;
    }

    public void setCurPow(int curPow) {
        this.curPow = curPow;
    }
}
