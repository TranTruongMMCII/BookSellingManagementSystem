package com.example.shoppingcart.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.shoppingcart.models.User;

@Dao
public interface UserDAO {
    @Query("select * from User where username = :userName")
    User getUserByUsername(String userName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);
}
