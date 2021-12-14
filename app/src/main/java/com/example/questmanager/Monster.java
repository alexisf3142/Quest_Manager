package com.example.questmanager;

import android.media.Image;

public class Monster {
    private String name;
    private int dmg, curPow, maxPow, pic, deathPic;
    private boolean charged;

    public Monster(String name, int dmg, int maxPow,int Pic,int DeathPic) {
        this.name = name;
        this.dmg = dmg;
        this.maxPow = maxPow;
        this.curPow = maxPow;
        this.charged = false;
        this.pic = Pic;
        this.deathPic = DeathPic;
    }

    public String getMonName() {
        return name;
    }

    public boolean isCharged() {
        return charged;
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

    public void setCharged(boolean charged) {
        this.charged = charged;
    }
}
