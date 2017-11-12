package com.example.nguye.minisafeway.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.nguye.minisafeway.R;
import com.example.nguye.minisafeway.ViewHolder.HistoryHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Froz on 11/11/2017.
 */

public class OrderRVadapter extends RecyclerView.Adapter<HistoryHolder> {
    Context c;
    ArrayList<Request> requests = new ArrayList<Request> ();

    public OrderRVadapter(Context c, ArrayList<Request> r){
        this.c = c;
        this.requests = r;
    }
    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.cart_layout, parent, false);
        return new HistoryHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, int position) {
        //BindData
        holder.cart_item_name.setText(requests.get(position).getFoods().get(position).getFoodId());
        holder.cart_item_price.setText(requests.get(position).getFoods().get(position).getPrice());
        Picasso.with(c).load(requests.get(position).getFoods().get(position).getImage()).into(holder.food_image);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

}