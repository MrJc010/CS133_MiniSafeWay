package com.example.nguye.minisafeway.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.nguye.minisafeway.Common.Common;
import com.example.nguye.minisafeway.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye on 11/5/2017.
 */

public class Database extends SQLiteAssetHelper{
    private static final String DB_NAME="minidata.db";
    private static final int DB_VER = 1;
    public Database(Context context){
        super(context,DB_NAME,null,DB_VER);
    }


    public List<Order> getCarts(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Name", "FoodId", "Quantity", "Price", "Discount","UserId","Image"};
        String sqlTable = "OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order> result = new ArrayList<Order>();
        if(c.moveToFirst()){
            do{
                result.add(new Order(c.getString(c.getColumnIndex("FoodId")),
                        c.getString(c.getColumnIndex("Name")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount")),
                        c.getString(c.getColumnIndex("UserId")),
                        c.getString(c.getColumnIndex("Image"))));
            } while(c.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail (FoodId,Name,Quantity,Price,Discount,UserId,Image) VALUES('%s' ,'%s' ,'%s' ,'%s','%s','%s','%s');",
                order.getFoodId(),order.getName(),order.getQuantity(),order.getPrice(),order.getDiscount(), Common.currentUser.getId(),order.getImage());
        db.execSQL(query);
    }

    public void cleanCart() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

}
