package com.example.nguye.minisafeway;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguye.minisafeway.Common.Common;
import com.example.nguye.minisafeway.Database.Database;
import com.example.nguye.minisafeway.Model.Order;
import com.example.nguye.minisafeway.Model.Request;
import com.example.nguye.minisafeway.R;
import com.example.nguye.minisafeway.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    String currentUser = Common.currentUser.getId().toString();

    DatabaseReference requests = database.getReference("History");

    TextView txtTotalPrice;
    Button btnPlace,home;

    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        System.out.println(currentUser); //TESTING TO SEE IF THIS IS NULL

        //requests = database.getReference("User");




        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView) findViewById(R.id.total);
        btnPlace = (Button) findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cart.size() > 0) {
                    showAlertDialog();
                }else{
                    Toast.makeText(Cart.this,"Your cart is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        
        loadListFood();

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this, R.style.AlertDialogStyle);
        View mView = getLayoutInflater().inflate(R.layout.dialog_checkout,null);
        alertDialog.setTitle("One more step");
        alertDialog.setMessage("Enter your card number and address: ");
        alertDialog.setView(mView);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Request request = new Request(
                        cart
                );
                for(int  x = 0 ; x < cart.size(); x++){
                    requests.push().setValue(cart.get(x));
                }


                //Delete cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Your orders are placed", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart, this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        double total = 0;
        for(Order order:cart){
            total += (Double.parseDouble(order.getPrice()) - Double.parseDouble(order.getDiscount()))*(Double.parseDouble(order.getQuantity()));
        }
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.DELETE)){
            deleteCart(item.getOrder());
        }
        return true;
    }

    private void deleteCart(int position) {
        cart.remove(position);
        new Database(this).cleanCart();
        for(Order item : cart){
            new Database(this).addToCart(item);
        }
        loadListFood();
    }
}
