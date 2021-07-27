package com.example.shoppingcart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.shoppingcart.AppDatabase;
import com.example.shoppingcart.AppExecutors;
import com.example.shoppingcart.Constants;
import com.example.shoppingcart.R;
import com.example.shoppingcart.adapters.ShopAdapter;
import com.example.shoppingcart.adapters.ViewHistoryAdapter;
import com.example.shoppingcart.models.Order;

import java.util.List;

public class ViewHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btn, btn1;
    AppDatabase appDatabase;
    ViewHistoryAdapter viewHistoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewhistory);

        recyclerView = findViewById(R.id.historyRecyclerview);
        btn = findViewById(R.id.btnBack);
        btn1 = findViewById(R.id.btnTiepTucMua);

        appDatabase = Room.databaseBuilder(ViewHistoryActivity.this, AppDatabase.class, "app-database").allowMainThreadQueries().build();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewHistoryActivity.this, LinearLayoutManager.VERTICAL, false));
        viewHistoryAdapter = new ViewHistoryAdapter(ViewHistoryActivity.this);
        recyclerView.setAdapter(viewHistoryAdapter);

        btn.setOnClickListener(view -> startActivity(new Intent(ViewHistoryActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

        btn1.setOnClickListener(view -> startActivity(new Intent(ViewHistoryActivity.this, ShopActivity.class)));
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveTasks();
    }

    private void retrieveTasks() {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            appDatabase = Room.databaseBuilder(ViewHistoryActivity.this, AppDatabase.class, "app-database").allowMainThreadQueries().build();
            List<Order> orderList = appDatabase.orderDAO().getOrderByUsername(Constants.USERNAME);
            viewHistoryAdapter.setTasks(orderList);
        });
    }
}
