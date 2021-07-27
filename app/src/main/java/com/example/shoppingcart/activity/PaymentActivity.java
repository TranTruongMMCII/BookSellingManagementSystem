package com.example.shoppingcart.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.Set;

public class PaymentActivity extends AppCompatActivity {
    EditText txtNguoiNhan, txtDiaChi;
    TextView txtNgayDat, txtTongTien, txtKhuyenMai, txtTongTienPhaiThanhToan;
    Spinner spinnerThanhToan;
    RecyclerView paymentRecyclerview;
    Button btnThanhToan, btnBack;
    AppDatabase appDatabase;
    PaymentAdapter paymentAdapter;
    List<Integer> productID = new ArrayList<>(Constants.ORDER.keySet());
    List<Integer> quantities = new ArrayList<>(Constants.ORDER.values());
    float sum = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        txtNguoiNhan = findViewById(R.id.txtNguoiNhan);
        txtDiaChi = findViewById(R.id.txtDiachi);
        txtNgayDat = findViewById(R.id.txtNgayDat);
        txtTongTien = findViewById(R.id.txtTongTien);
        txtKhuyenMai = findViewById(R.id.txtKhuyenMai);
        txtTongTienPhaiThanhToan = findViewById(R.id.txtTongTienPhaiThanhToan);
        spinnerThanhToan = findViewById(R.id.spinnerThanhToan);
        paymentRecyclerview = findViewById(R.id.paymentRecyclerview);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnBack = findViewById(R.id.btnBack);

        retrievesTask();

        btnBack.setOnClickListener(view -> startActivity(new Intent(PaymentActivity.this, ViewCartActivity.class)));

        btnThanhToan.setOnClickListener(view -> {
            btnThanhToanClick();
        });
    }

    private void retrievesTask() {
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
    }

    private void btnThanhToanClick() {
        if (validateInput()){
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

            String str = "Thông tin thanh toán:\n" +
                    "Người nhận: " + txtNguoiNhan.getText().toString() +"\n" +
                    "Ngày đặt: " + txtNgayDat.getText().toString() + "\n" +
                    "Số tiền: " + txtTongTienPhaiThanhToan.getText().toString() + "\n" +
                    "Hình thức thanh toán: " + spinnerThanhToan.getSelectedItem().toString() + "\n" +
                    "Địa chỉ: " + txtDiaChi.getText().toString() + "\n";

            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
            builder.setTitle("Thanh toán thành công");
            builder.setMessage(str);
            builder.setCancelable(false);
            builder.setPositiveButton("Xem lịch sử", (dialogInterface, i) -> startActivity(new Intent(PaymentActivity.this, ViewHistoryActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)));
            builder.setNegativeButton("Tiếp tục mua hàng", (dialogInterface, i) -> {
                Constants.ORDER.clear();
                startActivity(new Intent(PaymentActivity.this, ShopActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            });
            AlertDialog alert = builder.create();
            alert.show();
            Constants.ORDER.clear();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveTasks();
    }

    private void retrieveTasks() {
        List<Product> products = new ArrayList<>();
        appDatabase = Room.databaseBuilder(PaymentActivity.this, AppDatabase.class, "app-database").allowMainThreadQueries().build();
        for (int i = 0; i < productID.size(); ++i){
            Product product = appDatabase.productDAO().getProductByID(productID.get(i));
            if (quantities.get(i) != 0){
                products.add(product);
            }
        }
        paymentAdapter.setTasks(products);
    }

    private boolean validateInput(){
        if (TextUtils.isEmpty(txtNguoiNhan.getText())){
            Toast.makeText(getApplicationContext(), "Tên người nhận không được để trống.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(txtDiaChi.getText())){
            Toast.makeText(getApplicationContext(), "Địa chỉ nhận không được để trống.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
