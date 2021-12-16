package com.example.questmanager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ShopAdapter extends ArrayAdapter<ShopItem> {

    //important variables
    private int mostRecentlyClickedPosition;
    ShopItem currentItem;

    public ShopAdapter(Activity context, ArrayList<ShopItem> theList) {
        super(context, 0, theList);
        mostRecentlyClickedPosition = -1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.shop_item_layout, parent, false);
        }
        ImageView itemIV = listItemView.findViewById(R.id.itemImageView);
        TextView itemNameTV = listItemView.findViewById(R.id.itemNameTextView);
        TextView priceTV = listItemView.findViewById(R.id.priceTextView);
        TextView priceValueTV = listItemView.findViewById(R.id.priceValueTextView);
        Button purchaseB = listItemView.findViewById(R.id.purchaseButton);
        Button equipB = listItemView.findViewById(R.id.equipButton);

        //get the current item
        currentItem = getItem(position);
        //set all the UI components to the correct values/images/text
        //KNIGHT
        if (currentItem.getProfession().equals("Knight")) {
            if (currentItem.getWeaponType().equals("basic")) {
                itemIV.setImageResource(R.drawable.basic_sword);
            }
            if (currentItem.getWeaponType().equals("fancy")) {
                itemIV.setImageResource(R.drawable.fancy_sword);
            }
            if (currentItem.getWeaponType().equals("extravagant")) {
                itemIV.setImageResource(R.drawable.extravagant_sword);
            }
        }
        //MAGE
        else if (currentItem.getProfession().equals("Mage")) {
            if (currentItem.getWeaponType().equals("basic")) {
                itemIV.setImageResource(R.drawable.basic_staff);
            }
            if (currentItem.getWeaponType().equals("fancy")) {
                itemIV.setImageResource(R.drawable.fancy_staff);
            }
            if (currentItem.getWeaponType().equals("extravagant")) {
                itemIV.setImageResource(R.drawable.extravagant_staff);
            }
        }
        //RANGER
        else {
            if (currentItem.getWeaponType().equals("basic")) {
                itemIV.setImageResource(R.drawable.basic_bow);
            }
            if (currentItem.getWeaponType().equals("fancy")) {
                itemIV.setImageResource(R.drawable.fancy_bow);
            }
            if (currentItem.getWeaponType().equals("extravagant")) {
                itemIV.setImageResource(R.drawable.extravagant_bow);
            }
        }
        //set everything else
        itemNameTV.setText(currentItem.itemName);


        //maybe if purchased we could have it say "owned"?
        if(currentItem.isPurchased()){
            if(currentItem.isEquipped()) {
                priceTV.setText("Equipped");
                priceValueTV.setText("");
            }
            else {
                priceTV.setText("Owned");
                priceValueTV.setText("");
            }
        }
        else {
            priceTV.setText("Price: ");
            priceValueTV.setText(String.valueOf(currentItem.getPrice()));
        }
        if (position == mostRecentlyClickedPosition) {
            if(currentItem.isPurchased()) {
                if(currentItem.isEquipped()) {
                    priceTV.setText("Equipped");
                    priceValueTV.setText("");
                }
                else {
                    priceTV.setText("Owned");
                    priceValueTV.setText("");
                }
                equipB.setVisibility(View.VISIBLE);
                purchaseB.setVisibility(View.GONE);
            }
            else {
                purchaseB.setVisibility(View.VISIBLE);
                equipB.setVisibility(View.GONE);
                priceTV.setText("Price: ");
                priceValueTV.setText(String.valueOf(currentItem.getPrice()));
            }
        } else {
            purchaseB.setVisibility(View.GONE);
            equipB.setVisibility(View.GONE);
        }

        return listItemView;
    }

    public int getMostRecentlyClickedPosition() {
        return mostRecentlyClickedPosition;
    }

    public void setMostRecentlyClickedPosition(int mostRecentlyClickedPosition) {
        this.mostRecentlyClickedPosition = mostRecentlyClickedPosition;
        currentItem = getItem(mostRecentlyClickedPosition);
    }
}
