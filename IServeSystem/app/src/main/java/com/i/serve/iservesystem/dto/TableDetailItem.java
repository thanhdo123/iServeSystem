package com.i.serve.iservesystem.dto;

/**
 * Created by Admin on 5/29/2015.
 */
public class TableDetailItem {

    private int foodId;
    private String foodName;
    private int quantity;
    private double price;
    /*
     * 1: mon da phuc vu
     * 2: non dang cho
     * need add more here
     */
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
