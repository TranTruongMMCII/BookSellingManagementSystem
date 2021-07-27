package com.example.shoppingcart;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.shoppingcart.DAO.OrderDAO;
import com.example.shoppingcart.DAO.OrderDetailDAO;
import com.example.shoppingcart.DAO.ProductDAO;
import com.example.shoppingcart.DAO.UserDAO;
import com.example.shoppingcart.models.Order;
import com.example.shoppingcart.models.OrderDetail;
import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.models.User;

@Database(entities = {User.class, Product.class, Order.class, OrderDetail.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract ProductDAO productDAO();
    public abstract OrderDAO orderDAO();
    public abstract OrderDetailDAO orderDetailDAO();
}
