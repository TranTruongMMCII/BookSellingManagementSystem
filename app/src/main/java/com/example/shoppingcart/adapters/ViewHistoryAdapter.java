package com.example.shoppingcart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcart.R;
import com.example.shoppingcart.models.Order;

import java.util.ArrayList;
import java.util.List;

public class ViewHistoryAdapter extends RecyclerView.Adapter<ViewHistoryAdapter.MyViewHolder> {
    private Context context;
    private List<Order> orders = new ArrayList<>();

    public ViewHistoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtname.setText(orders.get(position).getUserID());
        holder.txtdate.setText(orders.get(position).getDateCreated());
        holder.txtmoney.setText(String.valueOf(orders.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setTasks(List<Order> tasks){
        this.orders = tasks;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtname, txtdate, txtmoney;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.txtUsername);
            txtdate = itemView.findViewById(R.id.txtDate);
            txtmoney = itemView.findViewById(R.id.txtTongTien);
        }
    }

}
