package com.example.questmanager;

public class Character {
    private String name,title,profession,weapon;
    private int str,dex,lck,smt,power,exp,lvl,gold,dmg,finQ,finT;

    /*
        The name and the professional are the only two things that we need to have entered to create
        the class. The rest I have propagated with placeholders for now we can work on some of the
        fine tuning later.

        KEY
        str = strength
        dex = dexterity
        lck = luck
        smt = (smarts) or intelligence just couldn't be int
        exp = experience points
        lvl = level
        dmg = damage, as in the most they can do at this point
        finQ = number of finished quests
        finT = number of finished tasks
     */
    public Character(String name, String profession) {
        this.name = name;
        this.profession = profession;
        this.title = "the Beginner";
        this.weapon = "a solid stick";
        this.str = 5;
        this.dex = 5;
        this.lck = 5;
        this.smt = 5;
        this.power = 25;
        this.exp = 0;
        this.lvl = 0;
        this.gold = 0;
        this.dmg = 3;
        this.finQ = 0;
        this.finT = 0;
    }

    //All of the getters start here

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getProfession() {
        return profession;
    }

    public String getWeapon() {
        return weapon;
    }

    public int getStr() {
        return str;
    }

    public int getDex() {
        return dex;
    }

    public int getLck() {
        return lck;
    }

    public int getSmt() {
        return smt;
    }

    public int getPower() {
        return power;
    }

    public int getExp() {
        return exp;
    }

    public int getLvl() {
        return lvl;
    }

    public int getGold() {
        return gold;
    }

    public int getDmg() {
        return dmg;
    }

    public int getFinQ() {
        return finQ;
    }

    public int getFinT() {
        return finT;
    }

    // All of the setters start here

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public void setLck(int lck) {
        this.lck = lck;
    }

    public void setSmt(int smt) {
        this.smt = smt;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public void setFinQ(int finQ) {
        this.finQ = finQ;
    }

    public void setFinT(int finT) {
        this.finT = finT;
    }
}


