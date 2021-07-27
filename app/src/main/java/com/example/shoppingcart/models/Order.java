package com.example.shoppingcart.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Order {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int orderID;

    @ColumnInfo(name = "userID")
    private String userID;

    @ColumnInfo(name = "dateCreated")
    private String dateCreated;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @ColumnInfo(name = "amount")
    private float amount;


    public Order(String userID, String dateCreated, float amount) {
        this.userID = userID;
        this.dateCreated = dateCreated;
        this.amount = amount;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
