package com.example.shoppingcart.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shoppingcart.models.Order;

import java.util.List;

@Dao
public interface OrderDAO {
    @Query("select * from `Order` where userID = :user")
    List<Order> getOrderByUsername(String user);

    @Insert
    void insert(Order order);

    @Query("SELECT orderID FROM `order` ORDER BY orderID DESC LIMIT 1")
    int getLastOrder();
}
