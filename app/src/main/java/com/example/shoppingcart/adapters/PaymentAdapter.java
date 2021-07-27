package com.example.shoppingcart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcart.AppDatabase;
import com.example.shoppingcart.Constants;
import com.example.shoppingcart.R;
import com.example.shoppingcart.activity.ViewCartActivity;
import com.example.shoppingcart.models.Product;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {
    private Context context;
    private List<Product> products  = new ArrayList<>();

    public PaymentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PaymentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payment, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.MyViewHolder holder, int position) {
            holder.txtName.setText(products.get(position).getName());
            holder.txtPrice.setText(String.valueOf(products.get(position).getPrice()));
            holder.txtQuantities.setText(String.valueOf(Constants.ORDER.get(products.get(position).getId())));
            holder.txtTotal.setText(String.valueOf(
                    Integer.parseInt(holder.txtQuantities.getText().toString()) * products.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setTasks(List<Product> tasks){
        this.products = tasks;

        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtQuantities, txtPrice, txtTotal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtQuantities = itemView.findViewById(R.id.txtQuantity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtTotal = itemView.findViewById(R.id.txtTotal);
        }
    }
}
