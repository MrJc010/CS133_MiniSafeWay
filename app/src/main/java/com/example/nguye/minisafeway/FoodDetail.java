package com.example.nguye.minisafeway;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.nguye.minisafeway.Common.Common;
import com.example.nguye.minisafeway.Database.Database;
import com.example.nguye.minisafeway.Model.Food;
import com.example.nguye.minisafeway.Model.Order;
import com.example.nguye.minisafeway.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class FoodDetail extends AppCompatActivity {

    TextView food_name, food_price, food_description,discount;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart,btnWish, home,home2;
    ElegantNumberButton numberButton;

    String foodId= "";

    FirebaseDatabase database;
    DatabaseReference foods;

    DatabaseReference wish;

    Food currentFood;

    List<Order> cart = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");
        wish = database.getReference("Wishlist");

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);
        btnWish = (FloatingActionButton) findViewById(R.id.btnWish);
        home = (FloatingActionButton) findViewById(R.id.home);
        home2 = (FloatingActionButton) findViewById(R.id.home2);
        String name="";
        try{
            name = Common.currentUser.getName();
       }catch (NullPointerException e){
            name=null;
        }
        if(name==null) {
           btnCart.hide();
            btnWish.hide();
           numberButton.removeAllViews();
       }


        btnWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order(
                        foodId, currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount(),
                        Common.currentUser.getId(),
                        currentFood.getImage()
                );

                wish.push().setValue(order);

                Toast.makeText(FoodDetail.this,"Added to your Wishlist",Toast.LENGTH_SHORT).show();
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId, currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount(),
                        Common.currentUser.getId(),
                        currentFood.getImage()
                ));

                Toast.makeText(FoodDetail.this,"Added to Cart",Toast.LENGTH_SHORT).show();
            }
        });

        food_description = (TextView) findViewById(R.id.food_description);
        food_name = (TextView) findViewById(R.id.food_name);
        food_price = (TextView) findViewById(R.id.food_price);
        food_image = (ImageView) findViewById(R.id.img_food);
        discount = (TextView) findViewById(R.id.discount);

        if(name!=null){
            home2.hide();
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodDetail.this, Home.class);
                startActivity(i);
            }
        });

        home2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodDetail.this, Guest.class);
                startActivity(i);
            }
        });

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        if(getIntent() != null){
            foodId = getIntent().getStringExtra("FoodId");
        }
        if(!foodId.isEmpty()){
            if(Common.isConnectedToInternet(getApplicationContext())) {
                getDetailFood(foodId);
            }else{
                Toast.makeText(FoodDetail.this, "Please check your connection!", Toast.LENGTH_SHORT).show();

            }
        }

    }


    private void getDetailFood(String foodId) {

        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(currentFood.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_name.setText(currentFood.getName());
                food_description.setText(currentFood.getDescription());
                discount.setText(currentFood.getDiscount());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
