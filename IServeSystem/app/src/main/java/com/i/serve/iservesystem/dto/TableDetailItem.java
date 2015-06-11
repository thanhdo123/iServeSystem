package com.i.serve.iservesystem.dto;

/**
 * Created by Admin on 5/29/2015.
 */
public class TableDetailItem {

    private int foodId;
    private String foodName;
    private int quantity;
    private double price;

    public final static int ORDER_STATE_CREATED = 1;
    public final static int ORDER_STATE_PROGRESS = 2;
    public final static int ORDER_STATE_DONE = 3;
    public final static int ORDER_STATE_DELIVERED = 4;
    public final static int ORDER_STATE_REMOVE = 100;
    public final static int ORDER_STATE_OUTOFSTOCK = 101;
    public final static int ORDER_STATE_WAIT_INFO = 200;
    public final static int ORDER_STATE_CONFIRM = 201;

    private int status;

    public TableDetailItem(int foodId, String foodName, int quantity, double price, int status) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
