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
import com.example.nguye.minisafeway.Model.Order;
import com.example.nguye.minisafeway.Model.OrderRVadapter;
import com.example.nguye.minisafeway.ViewHolder.HistoryHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class HistoryList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference orderHistoryList;

    FirebaseRecyclerAdapter<Food, HistoryHolder> adapter;
    OrderRVadapter oAdapter;

    ArrayList<Order> aOrderList = new ArrayList<Order>();
    String currentUser = Common.currentUser.getId();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = FirebaseDatabase.getInstance();
        orderHistoryList = database.getReference("History");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //orderHistoryList.orderByChild("userId").equalTo(Common.currentUser.getId());
        loadLocalDatabase(orderHistoryList);
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
                        aOrderList.add(order);
                        //System.out.println("This is the order " + order);
                    }
                }
                System.out.println("This is size of request in Anon Class: " + aOrderList.size());
                loadListFood();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


    private synchronized void loadListFood() {
        //oAdapter = new OrderRVadapter(this, aFoodList);
        adapter = new FirebaseRecyclerAdapter<Food, HistoryHolder>(Food.class,R.layout.history_layout,HistoryHolder.class, orderHistoryList.orderByChild("userId").equalTo(Common.currentUser.getId())) {
            @Override
            protected void populateViewHolder(HistoryHolder viewHolder, Food model, int position) {
                viewHolder.cart_item_name.setText(model.getName());
                viewHolder.cart_item_price.setText(model.getPrice());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);
                final String local = model.getMenuID();

                //System.out.println("This is the damn menuid that crashes b4" + aOrderList.get(position).getFoodId());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDetail = new Intent(com.example.nguye.minisafeway.HistoryList.this, FoodDetail.class);
                        //System.out.println("This is the damn menuid that crashes after" + aOrderList.get(position).getFoodId());
                        foodDetail.putExtra("FoodId",aOrderList.get(position).getFoodId()); //// bad
                        //foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
