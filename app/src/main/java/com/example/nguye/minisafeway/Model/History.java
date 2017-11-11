package com.example.nguye.minisafeway.Model;

/**
 * Created by nguye on 11/4/2017.
 */

public class History {
    private String Discount;
    private String Image;
    private String MenuId;
    private String Name;
    private String Price;
    private String UserId;

    public History() {
    }

    public History(String discount, String image, String menuID, String name, String price, String UserId) {
        Discount = discount;
        Image = image;
        MenuId = menuID;
        Name = name;
        Price = price;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMenuID() {
        return MenuId;
    }

    public void setMenuID(String menuID) {
        MenuId = menuID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}