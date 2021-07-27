package com.example.shoppingcart.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.shoppingcart.Constants;
import com.example.shoppingcart.R;
import com.example.shoppingcart.activity.ShopActivity;
import com.example.shoppingcart.adapters.ShopAdapter;

public class FragmentProductDetail extends DialogFragment implements View.OnClickListener {
    int id;
    ImageView imgProduct;
    TextView txtName, txtPrice, txtAvailability, txtQuantity;
    EditText txtContent;
    Button btnAddToCart, btnExit;
    View view;

    public static FragmentProductDetail getInstance(){
        return new FragmentProductDetail();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        setCancelable(false);

        imgProduct = (ImageView) view.findViewById(R.id.imgProduct);
        txtName = (TextView) view.findViewById(R.id.txtName);
        txtPrice = view.findViewById(R.id.txtPrice);
        txtAvailability = (TextView) view.findViewById(R.id.txtAvailibility);
        txtQuantity = (TextView) view.findViewById(R.id.txtQuantity);
        btnExit = (Button) view.findViewById(R.id.btnExit);
        btnAddToCart = (Button) view.findViewById(R.id.btnAddToCart);
        txtContent = (EditText) view.findViewById(R.id.txtContent);

        retreivesFragment();


        btnExit.setOnClickListener(view -> dismiss());

        btnAddToCart.setOnClickListener(view -> {
            Constants.productID.add(id);
            System.out.println(Constants.productID.size());
            Toast.makeText(getActivity(), "Thêm vào giỏ hàng thành công.", Toast.LENGTH_LONG).show();
        });

        return view;
    }

    private void retreivesFragment() {
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        txtName.setText(bundle.getString("name"));
        txtAvailability.setText(bundle.getString("availability"));
        txtPrice.setText(bundle.getString("price"));
        txtQuantity.setText(bundle.getString("quantity"));
        txtContent.setText(bundle.getString("content"));
        Glide.with(getActivity())
                .load(bundle.getString("url"))
                .placeholder(R.drawable.outline_autorenew_black_24dp)
                .into(imgProduct);

        if (bundle.getBoolean("out-of-stock")){
            txtAvailability.setTextColor(Color.RED);
            btnAddToCart.setEnabled(false);
        }

        txtContent.setOnTouchListener((view, motionEvent) -> {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        });
        txtContent.setKeyListener(null);
        txtContent.setHorizontallyScrolling(false);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = dialog.getWindow().getAttributes().height;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
