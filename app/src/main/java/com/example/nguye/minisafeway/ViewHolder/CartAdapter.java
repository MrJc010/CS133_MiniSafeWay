package com.example.nguye.minisafeway.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.nguye.minisafeway.Interface.ItemClickListener;
import com.example.nguye.minisafeway.Model.Order;
import com.example.nguye.minisafeway.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by nguye on 11/5/2017.
 */

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txt_cart_name,txt_price;
   // public ImageView img_cart_count;
    public TextView cart_count;
    ImageView plusitem;
    ImageView minusitem;
    int count=0;


    private ItemClickListener itemClickListener;

    public void setTxt_cart_name(TextView txt_cart_name){
        //this.txt_cart_name = txt_cart_name;
        this.cart_count= cart_count;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        txt_cart_name = (TextView) itemView.findViewById(R.id.cart_item_name);
        txt_price = (TextView) itemView.findViewById(R.id.cart_item_price);
        //img_cart_count = (ImageView) itemView.findViewById(R.id.cart_item_count);
        cart_count= (TextView) itemView.findViewById(R.id.cart_item_count);
    }

    @Override
    public void onClick(View view) {



    }
}


public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order> listData = new ArrayList<Order>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context){
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        //TextDrawable drawable = TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.RED);
        //holder.img_cart_count.setImageDrawable(drawable);

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt((listData.get(position).getQuantity())));
        holder.txt_price.setText(fmt.format(price));
        holder.txt_cart_name.setText(listData.get(position).getProductName());
        holder.cart_count.setText(fmt.format(getItemCount()));
        holder.plusitem = (ImageView) itemView.findViewById(R.id.bincrease);
        holder.minusitem = (ImageView) itemView.findViewById(R.id.bdecrease);



        TextView txtCount =(TextView) findViewById(R.id.cart_item_count);
        Button buttonInc= (Button) findViewById(R.id.bincrease);
        Button buttonDec= (Button) findViewById(R.id.bdecrease);

        buttonInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                cart_item_count.setText(String.valueOf(count));

            }
        });

        buttonDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                cart_item_count.setText(String.valueOf(count));

            }
        });


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
