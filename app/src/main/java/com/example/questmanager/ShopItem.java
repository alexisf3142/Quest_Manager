package com.example.questmanager;

public class ShopItem {
    private int price;
    public String itemName;
    private boolean purchased;
    private String achievementName;

    public ShopItem(int price, String itemName, boolean purchased, String achievementName) {
        this.price = price;
        this.itemName = itemName;
        this.purchased = purchased;
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
}

