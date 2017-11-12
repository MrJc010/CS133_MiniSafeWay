package com.example.nguye.minisafeway.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguye.minisafeway.Interface.ItemClickListener;
import com.example.nguye.minisafeway.R;

/**
 * Created by nguye on 11/4/2017.
 */

public class HistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView cart_item_name;
    public TextView cart_item_price;

    private ItemClickListener itemClickListener;
    public ImageView food_image;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public HistoryHolder(View itemView) {
        super(itemView);
        cart_item_name = (TextView) itemView.findViewById(R.id.cart_item_name);
        cart_item_price = (TextView) itemView.findViewById(R.id.cart_item_price);
        food_image = (ImageView) itemView.findViewById(R.id.food_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
