package com.example.shoppingcart.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.shoppingcart.AppDatabase;
import com.example.shoppingcart.AppExecutors;
import com.example.shoppingcart.Constants;
import com.example.shoppingcart.R;
import com.example.shoppingcart.adapters.PaymentAdapter;
import com.example.shoppingcart.models.Order;
import com.example.shoppingcart.models.OrderDetail;
import com.example.shoppingcart.models.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    EditText txtNguoiNhan;
    TextView txtNgayDat, txtTongTien, txtKhuyenMai, txtTongTienPhaiThanhToan;
    Spinner spinnerThanhToan;
    RecyclerView paymentRecyclerview;
    Button btnThanhToan, btnBack;
    AppDatabase appDatabase;
    PaymentAdapter paymentAdapter;
    List<Integer> productID = ViewCartActivity.productID;
    List<Integer> quantities = ViewCartActivity.quantities;
    float sum = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        txtNguoiNhan = findViewById(R.id.txtNguoiNhan);
        txtNgayDat = findViewById(R.id.txtNgayDat);
        txtTongTien = findViewById(R.id.txtTongTien);
        txtKhuyenMai = findViewById(R.id.txtKhuyenMai);
        txtTongTienPhaiThanhToan = findViewById(R.id.txtTongTienPhaiThanhToan);
        spinnerThanhToan = findViewById(R.id.spinnerThanhToan);
        paymentRecyclerview = findViewById(R.id.paymentRecyclerview);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnBack = findViewById(R.id.btnBack);

        txtNguoiNhan.setText(Constants.USERNAME);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        txtNgayDat.setText(formatter.format(date));

        appDatabase = Room.databaseBuilder(PaymentActivity.this, AppDatabase.class, "app-database").allowMainThreadQueries().build();

        paymentRecyclerview.setHasFixedSize(true);
        paymentRecyclerview.setLayoutManager(new LinearLayoutManager(PaymentActivity.this, LinearLayoutManager.VERTICAL, false));
        paymentAdapter = new PaymentAdapter(PaymentActivity.this);
        paymentRecyclerview.setAdapter(paymentAdapter);

//        float sum = paymentAdapter.getSum();
        for (int i = 0; i < productID.size(); ++i){
            Product product = appDatabase.productDAO().getProductByID(productID.get(i));
            sum += product.getPrice() * quantities.get(i);
        }
        txtTongTien.setText(String.valueOf(sum));
        if (sum >= 300000){
            txtKhuyenMai.setText(String.valueOf(0.1*sum));
        }
        else{
            txtKhuyenMai.setText(String.valueOf(0));
        }
        txtTongTienPhaiThanhToan.setText(String.valueOf(sum - Float.parseFloat(txtKhuyenMai.getText().toString())));

        btnBack.setOnClickListener(view -> startActivity(new Intent(PaymentActivity.this, ViewCartActivity.class)));

        btnThanhToan.setOnClickListener(view -> {
            if (validateInput()){
                AppExecutors.getInstance().getDiskIO().execute(() -> {
                    Order order = new Order(Constants.USERNAME, txtNgayDat.getText().toString(), Float.parseFloat(txtTongTienPhaiThanhToan.getText().toString()));
                    appDatabase.orderDAO().insert(order);
                    int id = appDatabase.orderDAO().getLastOrder();
                    for(int i = 0; i < productID.size(); ++i){
                        OrderDetail orderDetail = new OrderDetail(id, productID.get(i), quantities.get(i));
                        appDatabase.orderDetailDAO().insert(orderDetail);
                        Product product = appDatabase.productDAO().getProductByID(productID.get(i));
                        product.setQuantity(product.getQuantity() - quantities.get(i));
                        appDatabase.productDAO().update(product);
                    }
                });

                String str = "Thông tin thanh toán:\n" +
                        "Người nhận: " + txtNguoiNhan.getText().toString() +"\n" +
                        "Ngày đặt: " + txtNgayDat.getText().toString() + "\n" +
                        "Số tiền: " + txtTongTienPhaiThanhToan.getText().toString() + "\n" +
                        "Hình thức thanh toán: " + spinnerThanhToan.getSelectedItem().toString() + "\n";

                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                builder.setTitle("Thanh toán");
                builder.setMessage(str);
                builder.setCancelable(false);
                builder.setPositiveButton("Xem lịch sử", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(PaymentActivity.this, ViewHistoryActivity.class));
                    }
                });
                builder.setNegativeButton("Tiếp tục mua hàng", (dialogInterface, i) -> {
                    ShopActivity.productID = new ArrayList<>();
                    startActivity(new Intent(PaymentActivity.this, ShopActivity.class));
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveTasks();
    }

    private void retrieveTasks() {
        List<Product> products = new ArrayList<>();
        appDatabase = Room.databaseBuilder(PaymentActivity.this, AppDatabase.class, "app-database").allowMainThreadQueries().build();
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            for (int i = 0; i < productID.size(); ++i){
                Product product = appDatabase.productDAO().getProductByID(productID.get(i));
                products.add(product);
            }
        });
        paymentAdapter.setTasks(products);
    }

    private boolean validateInput(){
        if (TextUtils.isEmpty(txtNguoiNhan.getText())){
            Toast.makeText(getApplicationContext(), "Tên người nhận không được để trống.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
