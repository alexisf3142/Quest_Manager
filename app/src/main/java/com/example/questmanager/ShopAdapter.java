package com.example.questmanager;

import android.app.Activity;
import android.content.Context;
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

    public ShopAdapter(Activity context, ArrayList<ShopItem> theList) {
        super(context,0, theList);
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
        ImageView coin2IV = listItemView.findViewById(R.id.coin2ImageView);
        TextView priceValueTV = listItemView.findViewById(R.id.priceValueTextView);
        Button purchaseB = listItemView.findViewById(R.id.purchaseButton);

        if (position == mostRecentlyClickedPosition) {
            purchaseB.setVisibility(View.VISIBLE);
        }
        else {
            purchaseB.setVisibility(View.GONE);
        }

        return listItemView;
    }

    public int getMostRecentlyClickedPosition() {
        return mostRecentlyClickedPosition;
    }

    public void setMostRecentlyClickedPosition(int mostRecentlyClickedPosition) {
        this.mostRecentlyClickedPosition = mostRecentlyClickedPosition;
    }
}
