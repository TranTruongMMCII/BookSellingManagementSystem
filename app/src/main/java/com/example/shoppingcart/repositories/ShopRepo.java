//package com.example.shoppingcart.repositories;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.shoppingcart.models.Product;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//public class ShopRepo {
//
//    private MutableLiveData<List<Product>> mutableProductList;
//
//    public LiveData<List<Product>> getProducts(){
//        if (mutableProductList == null){
//            mutableProductList = new MutableLiveData<>();
//            loadProducts();
//        }
//        return mutableProductList;
//    }
//
//    private void loadProducts(){
//        List<Product> productList = new ArrayList<>();
//        productList.add(new Product(UUID.randomUUID().toString(), "Mỗi lần vấp ngã là một lần trưởng thành", 67000, true,
//                "https://minilessons.net/wp-content/uploads/2019/08/vapngabook.jpg"));
//        productList.add(new Product(UUID.randomUUID().toString(), "Tuổi trẻ đáng giá bao nhiêu", 60000, true,
//                "https://salt.tikicdn.com/media/catalog/product/t/u/tuoi-tre-dang-gia-bao-nhieu-u547-d20161012-t113832-888179.u3059.d20170616.t095744.390222.jpg"));
//        productList.add(new Product(UUID.randomUUID().toString(), "Cho tôi xin một vé đi tuổi thơ", 138000, true,
//                "https://thuvientinhlaocai.vn/uploads/news/2020_09/gioi-thieu-sach/cho-toi-xin-mot-ve-di-tuoi-tho.jpg"));
//        productList.add(new Product(UUID.randomUUID().toString(), "Mặc kệ thiên hạ, sống như người Nhật", 61000, true,
//                "https://vnwriter.net/wp-content/uploads/2017/05/sach-mac-ke-thien-ha-song-nhu-nguoi-nhat-ebook.jpg"));
//        productList.add(new Product(UUID.randomUUID().toString(), "Trân trọng chính mình", 75000, true,
//                "https://salt.tikicdn.com/cache/w444/ts/product/61/20/b4/8eec36b72ed7ce2b83c9aff44bb2a57e.jpg"));
//        productList.add(new Product(UUID.randomUUID().toString(), "Tôi thấy hoa vàng trên cỏ xanh", 92000, true,
//                "https://salt.tikicdn.com/cache/w1200/media/catalog/product/t/o/toi_thay_hoa_vang.jpg"));
//        productList.add(new Product(UUID.randomUUID().toString(), "Nếu chỉ còn một ngày để sống", 72000, true,
//                "https://bizweb.dktcdn.net/100/370/339/products/885845868fc875962cd9.jpg?v=1587042283737"));
//        productList.add(new Product(UUID.randomUUID().toString(), "Đường đua của những giấc mơ", 72000, true,
//                "https://salt.tikicdn.com/media/catalog/product/d/u/duong-dua-cua-nhung-giac-mo-03.u2769.d20170302.t181017.272234.jpg"));
//        mutableProductList.setValue(productList);
//    }
//}
