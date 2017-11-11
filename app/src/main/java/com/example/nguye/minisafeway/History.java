package com.example.nguye.minisafeway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.nguye.minisafeway.Common.Common;
import com.example.nguye.minisafeway.Model.Food;
import com.example.nguye.minisafeway.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class History extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference historyOrder;
    String orderID = "";
    Food food;

    public TextView txt_cart_name, txt_price;

    String currentUser = Common.currentUser.getId().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        database = FirebaseDatabase.getInstance();
        txt_cart_name = (TextView) findViewById(R.id.cart_item_name);
        txt_price = (TextView) findViewById(R.id.cart_item_price);

        historyOrder = database.getReference("User");

        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null){
            orderID = getIntent().getStringExtra("test");
        }
        if(!orderID.isEmpty() && orderID != null){
            loadListFood(orderID);
        }


    }

    private void loadListFood(String orderID) {
        historyOrder.child(currentUser).child("History").child("1510014059407").child("foods").child("0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                food = dataSnapshot.getValue(Food.class);
                txt_cart_name.setText(food.getName());
                txt_price.setText(food.getPrice());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
