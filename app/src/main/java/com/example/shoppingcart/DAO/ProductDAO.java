package com.example.shoppingcart.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoppingcart.models.Product;

import java.util.List;

@Dao
public interface ProductDAO {
    @Query("select * from Product")
    List<Product> getAll();

    @Query("select * from Product where id = :pID")
    Product getProductByID(int pID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Query("select * from Product where name like :pName COLLATE [Vietnamese_CI_AI]")
    List<Product> getProductByName(String pName);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Product product);
}
