package com.example.shoppingcart.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingcart.Constants;
import com.example.shoppingcart.R;
import com.example.shoppingcart.activity.ShopActivity;
import com.example.shoppingcart.fragment.FragmentProductDetail;
import com.example.shoppingcart.models.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {
    private Context context;
    private List<Product> products = new ArrayList<>();

    public ShopAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_row, parent, false);

        return new MyViewHolder(view);
    }


    private static String checkProductBeingOutOfStock(int quantity){
        return quantity == 0 ? "Hết hàng" : "Còn hàng";
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context)
                .load(products.get(position).getImageUrl())
                .placeholder(R.drawable.outline_autorenew_black_24dp)
                .into(holder.imgProduct);
        holder.txtName.setText(products.get(position).getName());
        holder.txtPrice.setText(String.valueOf(products.get(position).getPrice()));
        holder.txtAvailibility.setText(checkProductBeingOutOfStock(products.get(position).getQuantity()));
        if (products.get(position).getQuantity() == 0){
            holder.btnAddToCart.setEnabled(false);
            holder.txtAvailibility.setTextColor(Color.RED);
        }


        holder.btnAddToCart.setOnClickListener(view -> {
            Constants.productID.add(products.get(position).getId());
            System.out.println(Constants.productID.size());
            Toast.makeText(context, "Thêm vào giỏ hàng thành công.", Toast.LENGTH_LONG).show();
        });

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", products.get(position).getId());
            bundle.putString("name", products.get(position).getName());
            bundle.putString("price", String.valueOf(products.get(position).getPrice()));
            bundle.putString("quantity", String.valueOf(products.get(position).getQuantity()));
            bundle.putString("availability", (products.get(position).getQuantity() == 0 ? "Hết hàng" : "Còn hàng"));
            bundle.putString("content", products.get(position).getDescription());
            bundle.putString("url", products.get(position).getImageUrl());
            bundle.putBoolean("out-of-stock", products.get(position).getQuantity() == 0 );
            FragmentProductDetail fragmentProductDetail = FragmentProductDetail.getInstance();
            fragmentProductDetail.setArguments(bundle);
            fragmentProductDetail.show(((AppCompatActivity)context).getSupportFragmentManager(), "FragmentProductDetail");
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setTasks(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    public List<Product> getTasks(){
        return products;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName, txtPrice, txtAvailibility;
        Button btnAddToCart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtAvailibility = (TextView) itemView.findViewById(R.id.txtAvailibility);
            btnAddToCart = (Button) itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
