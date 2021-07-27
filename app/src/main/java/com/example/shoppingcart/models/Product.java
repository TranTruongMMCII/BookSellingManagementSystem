package com.example.shoppingcart.models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "imageUrl")
    private String imageUrl;

    @ColumnInfo(name = "description")
    private String description;

    public Product(String name, double price, int quantity, String imageUrl, String description) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //    public Product(String id, String name, double price, boolean isAvailable, String imageUrl) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.isAvailable = isAvailable;
//        this.imageUrl = imageUrl;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public boolean isAvailable() {
//        return isAvailable;
//    }
//
//    public void setAvailable(boolean available) {
//        isAvailable = available;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    @Override
//    public String toString() {
//        return "Product{" +
//                "id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", price=" + price +
//                ", isAvailable=" + isAvailable +
//                ", imageUrl='" + imageUrl + '\'' +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Product product = (Product) o;
//        return Double.compare(product.getPrice(), getPrice()) == 0 &&
//                isAvailable() == product.isAvailable() &&
//                getId().equals(product.getId()) &&
//                getName().equals(product.getName()) &&
//                getImageUrl().equals(product.getImageUrl());
//    }
//
//
//    public static DiffUtil.ItemCallback<Product> itemCallback = new DiffUtil.ItemCallback<Product>() {
//        @Override
//        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
//            return oldItem.getId().equals(newItem.getId());
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
//            return oldItem.equals(newItem);
//        }
//    };
//
//    @BindingAdapter("android:productImage")
//    public static void loadImage(ImageView imageView, String imageUrl){
//        Glide.with(imageView)
//                .load(imageUrl)
//                .fitCenter()
//                .into(imageView);
//    }
}