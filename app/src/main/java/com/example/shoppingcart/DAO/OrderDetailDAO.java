package com.example.shoppingcart.DAO;

import androidx.room.Dao;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shoppingcart.models.OrderDetail;

import java.util.List;

@Dao
public interface OrderDetailDAO {
    @Insert
    void insert(OrderDetail orderDetail);

    @Query("select * from OrderDetail where orderID = :id")
    List<OrderDetail> getOrderByOrderID(int id);
}
