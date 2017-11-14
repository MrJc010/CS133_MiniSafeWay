package com.example.nguye.minisafeway;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.nguye.minisafeway.Common.Common;
import com.example.nguye.minisafeway.Interface.ItemClickListener;
import com.example.nguye.minisafeway.Model.Food;
import com.example.nguye.minisafeway.Model.Order;
import com.example.nguye.minisafeway.Model.OrderRVadapter;
import com.example.nguye.minisafeway.ViewHolder.HistoryHolder;
import com.example.nguye.minisafeway.ViewHolder.WishListHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class WishList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference wishList;

    FirebaseRecyclerAdapter<Food, WishListHolder> adapter;

    FloatingActionButton home;

    ArrayList<Order> list = new ArrayList<Order>();
    String currentUser = Common.currentUser.getId();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        database = FirebaseDatabase.getInstance();
        wishList = database.getReference("Wishlist");

        recyclerView = (RecyclerView) findViewById(R.id.orderRCList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //orderHistoryList.orderByChild("userId").equalTo(Common.currentUser.getId());
        loadLocalDatabase(wishList);
        home = (FloatingActionButton) findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WishList.this, Home.class);
                startActivity(i);
            }
        });
    }

    public void loadLocalDatabase (DatabaseReference db) {
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                while(dataSnapshots.hasNext()) {
                    DataSnapshot orderItem = dataSnapshots.next();
                    Order order = orderItem.getValue(Order.class); // This is the request object.
                    if(order.getUserId().equals(currentUser)) {
                        //System.out.println("condition fulfilled user is: " + order.getUserId());
                        list.add(order);
                        //System.out.println("This is the order " + order);
                    }
                }
                loadListFood();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private synchronized void loadListFood() {
        adapter = new FirebaseRecyclerAdapter<Food, WishListHolder>(Food.class,R.layout.history_layout,WishListHolder.class,wishList.orderByChild("userId").equalTo(Common.currentUser.getId())) {
            @Override
            protected void populateViewHolder(WishListHolder viewHolder, Food model, int position) {
                viewHolder.cart_item_name.setText(model.getName());
                viewHolder.cart_item_price.setText(model.getPrice());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);
                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDetail = new Intent(com.example.nguye.minisafeway.WishList.this, FoodDetail.class);
                        foodDetail.putExtra("FoodId",list.get(position).getFoodId()); //// bad
                        startActivity(foodDetail);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.DELETE)){
            showDeleteDialog(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void showDeleteDialog(String key) {
        wishList.child(key).removeValue();
        Toast.makeText(this, "Item has been removed!", Toast.LENGTH_SHORT).show();
    }


}
