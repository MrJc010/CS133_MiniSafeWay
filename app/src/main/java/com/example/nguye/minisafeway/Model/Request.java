package com.example.nguye.minisafeway.Model;

import java.util.List;

/**
 * Created by nguye on 11/6/2017.
 */

public class Request {
    private List<Order> Order;

    public Request() {
    }
    public Request(List<Order> foods) {
        this.Order = foods;
    }
    public List<Order> getFoods() {
        return Order;
    }

    public void setFoods(List<Order> foods) {
        this.Order = foods;
    }
}
