package com.example.nguye.minisafeway.Model;

import android.widget.ImageView;

/**
 * Created by nguye on 11/5/2017.
 */

public class Order {
    private String FoodId;
    private String Name;
    private String Quantity;
    private String Price;
    private String Discount;
    private String UserId;
    private String Image;

    public Order(){}

    public Order(String productId, String productName, String quantity, String price, String discount, String userId, String image) {
        FoodId = productId;
        Name = productName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
        UserId = userId;
        Image = image;
    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
