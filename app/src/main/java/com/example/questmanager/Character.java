package com.example.questmanager;
import java.io.Serializable;

public class Character implements Serializable {
    private String name,title,profession,weapon;
    private int str,dex,lck,smt,curpower,maxpower,curexp,maxexp,lvl,gold,dmg, weaponmod,finQ,finT,skillPoints;
    private boolean charged;

    /*
        The name and the professional are the only two things that we need to have entered to create
        the class. The rest I have propagated with placeholders for now we can work on some of the
        fine tuning later. - John

        KEY
        str = strength
        dex = dexterity
        lck = luck
        smt = (smarts) or intelligence
        power = how much power the player has to spend in dungeons
        exp = experience points
        lvl = level
        dmg = damage, as in the most they can do at this point
        finQ = number of finished quests
        finT = number of finished tasks
        charged = In dun. screen, if the character has a charged attack stored.
     */

    public Character(String name, String profession) {
        this.name = name;
        this.profession = profession;
        this.title = "Beginner";
        this.weapon = "a solid stick";
        this.str = 5; //Strength affects charge
        this.dex = 5; //Dex affects defense
        this.lck = 5; //Luck affects coins
        this.smt = 5; //Smarts affects Experience
        this.maxpower = 25;
        this.curpower = 0;
        this.curexp = 0;
        this.maxexp = 100;
        this.lvl = 1;
        this.gold = 0;
        this.dmg = 3;
        this.weaponmod = 1;
        this.finQ = 0;
        this.finT = 0;
        this.skillPoints = 0;
        this.charged = false;
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

    public int getCurpower() {
        return curpower;
    }

    public int getMaxpower() {
        return maxpower;
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

    public int getCurexp() {
        return curexp;
    }

    public int getMaxexp() {
        return maxexp;
    }

    public int getLvl() {
        return lvl;
    }

    public int getGold() {
        return gold;
    }

    public int getDmg() {
        return dmg + weaponmod;
    }

    public int getWeaponmod() {
        return weaponmod;
    }

    public int getFinQ() {
        return finQ;
    }

    public int getFinT() {
        return finT;
    }

    public boolean isCharged() {
        return charged;
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

    public void setCurexp(int curexp) {
        this.curexp = curexp;
    }

    public void setMaxexp(int maxexp) {
        this.maxexp = maxexp;
    }

    public void setCurpower(int curpower) {
        this.curpower = curpower;
    }

    public void setMaxpower(int maxpower) {
        this.maxpower = maxpower;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setWeaponmod(int weaponmod) {
        this.weaponmod = weaponmod;
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

    public void setCharged(boolean charged) {
        this.charged = charged;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    /* following method is called in QuestFragment and DailyQuestFragment
    if the player earns enough EXP from completing a Quest to increase their level*/
    public void levelUp (int curexp) {
        this.lvl += 1; // ups player's level by 1

        int extraEXP = curexp - this.maxexp; // calculates leftover exp from leveling up
        curexp = Math.max(extraEXP, 0);
        // sets current exp to the leftover so it doesn't go to waste, or 0 if there is none
        this.curexp = curexp; // saves current exp

        this.maxexp = maxexp + 5; // increases amount of exp player will need to level up again
        this.maxpower = maxpower + 5; // increases player's max power
        this.skillPoints++; // gives the player a skill point to use on the profile screen
    }
}


