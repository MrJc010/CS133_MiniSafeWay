package com.example.nguye.minisafeway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.nguye.minisafeway.Common.Common;
import com.example.nguye.minisafeway.Interface.ItemClickListener;
import com.example.nguye.minisafeway.Model.Food;
import com.example.nguye.minisafeway.Model.History;
import com.example.nguye.minisafeway.ViewHolder.FoodViewHolder;
import com.example.nguye.minisafeway.ViewHolder.HistoryHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nguye.minisafeway.FoodDetail;
import com.example.nguye.minisafeway.Interface.ItemClickListener;
import com.example.nguye.minisafeway.Model.Food;
import com.example.nguye.minisafeway.R;
import com.example.nguye.minisafeway.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HistoryList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoryId = "";
    FirebaseRecyclerAdapter<History, HistoryHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("History");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadListFood();
    }


    private void loadListFood() {
        adapter = new FirebaseRecyclerAdapter<History, HistoryHolder>(History.class,R.layout.history_layout,HistoryHolder.class,foodList.orderByChild("userId").equalTo(Common.currentUser.getId())) {
            @Override
            protected void populateViewHolder(HistoryHolder viewHolder, History model, int position) {
                viewHolder.cart_item_name.setText(model.getName());
                viewHolder.cart_item_price.setText(model.getPrice());
                //Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);
                final History local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDetail = new Intent(com.example.nguye.minisafeway.HistoryList.this, FoodDetail.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
