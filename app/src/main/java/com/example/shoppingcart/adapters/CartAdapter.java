package com.example.shoppingcart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.shoppingcart.AppDatabase;
import com.example.shoppingcart.AppExecutors;
import com.example.shoppingcart.Constants;
import com.example.shoppingcart.R;
import com.example.shoppingcart.activity.ShopActivity;
import com.example.shoppingcart.activity.ViewCartActivity;
import com.example.shoppingcart.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private List<Integer> productIDs = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private AppDatabase appDatabase;

    public CartAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_in_cart, parent, false);
//        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "app-database").allowMainThreadQueries().build();
//
//        AppExecutors.getInstance().getDiskIO().execute(() -> {
//            for (int i:productIDs){
//                products.add(appDatabase.productDAO().getProductByID(i));
//                ViewCartActivity.quantities.add(1);
//            }
//        });

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.imgProduct
        Glide.with(context)
                .load(products.get(position).getImageUrl())
                .placeholder(R.drawable.sanpham1)
                .into(holder.imgProduct);
        holder.txtName.setText(products.get(position).getName());
        holder.numberPicker.setMaxValue(products.get(position).getQuantity());
        holder.numberPicker.setValue(1);

        holder.numberPicker.setOnValueChangedListener((numberPicker, i, i1) -> ViewCartActivity.quantities.set(position, i1));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setTasks(List<Product> tasks){
        this.products = tasks;

        notifyDataSetChanged();
    }

    public List<Integer> getTasks(){
        return this.productIDs;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName;
        NumberPicker numberPicker;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtName = itemView.findViewById(R.id.txtName);
            numberPicker = itemView.findViewById(R.id.numberPicker);
            numberPicker.setMinValue(0);
        }
    }
}
