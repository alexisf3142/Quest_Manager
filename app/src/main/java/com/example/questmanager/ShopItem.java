package com.example.questmanager;

import androidx.annotation.Nullable;

public class ShopItem {
    private int price;
    private int weaponmod;
    public String itemName;
    private boolean purchased = false;
    private boolean equipped = false;

    public int getWeaponmod() {
        return weaponmod;
    }

    public void setWeaponmod(int weaponmod) {
        this.weaponmod = weaponmod;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    private String achievementName;
    private String weaponType; //basic, fancy, extravagant
    private String profession; //Knight, Mage, Ranger


    public ShopItem(int price, String itemName, String weaponType, String profession, int weaponmod, @Nullable String achievementName) {
        this.price = price;
        this.itemName = itemName;
        this.weaponType = weaponType;
        this.profession = profession;
        this.weaponmod = weaponmod;
        this.achievementName = achievementName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}

