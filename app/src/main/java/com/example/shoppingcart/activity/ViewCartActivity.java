package com.example.shoppingcart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.shoppingcart.AppDatabase;
import com.example.shoppingcart.Constants;
import com.example.shoppingcart.R;
import com.example.shoppingcart.adapters.CartAdapter;
import com.example.shoppingcart.models.Product;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ViewCartActivity extends AppCompatActivity {
    RecyclerView cartRecyclerView;
    Button btnOder, btnBack;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcart);
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        btnBack = findViewById(R.id.btnBack);
        btnOder = findViewById(R.id.btnThanhToan);

        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(ViewCartActivity.this, LinearLayoutManager.VERTICAL, false));
        cartAdapter = new CartAdapter(ViewCartActivity.this);
        cartRecyclerView.setAdapter(cartAdapter);

        btnBack.setOnClickListener(view -> startActivity(new Intent(ViewCartActivity.this, ShopActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)));
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveTasks();
        if (cartAdapter.getItemCount()==0){
            btnOder.setEnabled(false);
        }
        else{
            btnOder.setEnabled(true);
            btnOder.setOnClickListener(view -> startActivity(new Intent(ViewCartActivity.this, PaymentActivity.class)));
        }
    }

    private void retrieveTasks() {
        Set<Integer> set = new LinkedHashSet<>();
        set.addAll(Constants.productID);
        Constants.productID.clear();
        Constants.productID.addAll(set);
        List<Product> products = new ArrayList<>();
        AppDatabase appDatabase = Room.databaseBuilder(ViewCartActivity.this, AppDatabase.class, "app-database").allowMainThreadQueries().build();
        for (int i:Constants.productID){
            products.add(appDatabase.productDAO().getProductByID(i));
            Constants.quantities.add(1);
        }
        cartAdapter.setTasks(products);
    }
}
