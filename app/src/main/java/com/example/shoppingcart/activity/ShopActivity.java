package com.example.shoppingcart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.shoppingcart.AppDatabase;
import com.example.shoppingcart.R;
import com.example.shoppingcart.adapters.ShopAdapter;
import com.example.shoppingcart.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtSearch;
    ImageView imgSearch, imgCart, imgHistory, imgClose;
    RecyclerView shopRecyclerView;
    AppDatabase appDatabase;
    ShopAdapter shopAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        txtSearch = findViewById(R.id.txtSearch);
        imgSearch = findViewById(R.id.imgSearch);
        imgCart = findViewById(R.id.imgCart);
        imgClose = findViewById(R.id.imgClose);
        imgHistory = findViewById(R.id.imgHistory);
        shopRecyclerView = findViewById(R.id.shopRecyclerView);
        appDatabase = Room.databaseBuilder(ShopActivity.this, AppDatabase.class, "app-database").allowMainThreadQueries().build();

        shopRecyclerView.setHasFixedSize(true);
        shopRecyclerView.setLayoutManager(new GridLayoutManager(ShopActivity.this, 2));
        shopAdapter = new ShopAdapter(ShopActivity.this);
        shopRecyclerView.setAdapter(shopAdapter);

        imgCart.setOnClickListener(this::onClick);
        imgSearch.setOnClickListener(this::onClick);
        imgHistory.setOnClickListener(this::onClick);
        imgClose.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgCart:
                Intent intent = new Intent(ShopActivity.this, ViewCartActivity.class);
                startActivity(intent);
                break;
            case R.id.imgSearch:
                if (!TextUtils.isEmpty(txtSearch.getText())){
                    String search = txtSearch.getText().toString();
                    search = "%" + search + "%";
                    List<Product> products = appDatabase.productDAO().getProductByName(search);
                    if (products.isEmpty()){
                        String finalSearch = search;
                        runOnUiThread(() -> Toast.makeText(ShopActivity.this, "Không tìm thấy sách với từ khóa " + finalSearch, Toast.LENGTH_LONG).show());
                    }
                    else{
                        runOnUiThread(() -> shopAdapter.setTasks(products));
                    }
                }
                else{
                    shopAdapter.setTasks(appDatabase.productDAO().getAll());
                }
                break;
            case R.id.imgHistory:
                startActivity(new Intent(ShopActivity.this, ViewHistoryActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                break;
            case R.id.imgClose:
                txtSearch.setText("");
                imgSearch.callOnClick();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveTasks();
    }

    private void retrieveTasks() {
        List<Product> productList = appDatabase.productDAO().getAll();
        if (productList.isEmpty()){
            productList = new ArrayList<>();
            productList.add(new Product("Mỗi lần vấp ngã là một lần trưởng thành", 67000, 50,
                    "https://minilessons.net/wp-content/uploads/2019/08/vapngabook.jpg",
                    "Sách \"Mỗi lần vấp ngã là một lần trưởng thành\": “Chỉ cần còn hơi thở là còn có cơ hội, chỉ cần cố gắng là còn có thể nhìn thấy hy vọng. Dù có trải qua ngàn lần vấp ngã và thất bại thì bạn cũng hãy tin rằng: Không có gì có thể cản được bạn bước tới ngày mai”"));
            productList.add(new Product( "Tuổi trẻ đáng giá bao nhiêu", 60000, 70,
                    "https://salt.tikicdn.com/media/catalog/product/t/u/tuoi-tre-dang-gia-bao-nhieu-u547-d20161012-t113832-888179.u3059.d20170616.t095744.390222.jpg",
                    "Sách \"Tuổi trẻ đáng giá bao nhiêu\": Sách có vai trò rất quan trọng trong cuộc sống chúng ta. Những cuốn sách hay dù ít hay nhiều đều mang lại cho ta những giá trị nhất định. Không chỉ là những cuốn sách học tập, làm giàu mà những cuốn sách giải trí tưởng chừng \"vô dụng\" nhưng lại giúp ta có những giây phút thư giãn. Bằng trải nghiệm sống của chính mình, cuốn sách “Tuổi trẻ đáng giá bao nhiêu?” – tác giả Rosie Nguyễn đã đem đến cho các độc giả đang loay hoay với tuổi trẻ của mình những câu trả lời và hướng dẫn tỉ mỉ nhất để có một tuổi trẻ đáng giá và rực rỡ. Tuổi trẻ đáng giá bao nhiêu là cuốn sách không nặng nề giáo điều, không chỉ trích cực đoan, đơn giản chỉ là những tâm sự bình dị của người đi trước, Rosie Nguyễn mang đến cho bạn trẻ những tư tưởng tích cực nhất để mạnh mẽ bước chân vào đời."));
            productList.add(new Product( "Cho tôi xin một vé đi tuổi thơ", 138000, 100,
                    "https://thuvientinhlaocai.vn/uploads/news/2020_09/gioi-thieu-sach/cho-toi-xin-mot-ve-di-tuoi-tho.jpg",
                    "Sách \"Cho tôi xin một vé đi tuổi thơ\": Tôi từng thấy có nhiều người trẻ tuổi lên kế hoạch cho cuộc đời mình: 22 tuổi tốt nghiệp đại học, 25 tuổi lập gia đình, 27 tuổi mở công ty, 30 tuổi sinh con đầu lòng, vân vân và vân vân… Thật sít sao! Nhưng một khi cuộc đời một con người được lập trình chặt chẽ và khoa học đến thế thì nếu tất cả đều vào khuôn như dự tính liệu bạn có bão hòa về cảm xúc hay không?\n"));
            productList.add(new Product( "Mặc kệ thiên hạ, sống như người Nhật", 61000, 1,
                    "https://vnwriter.net/wp-content/uploads/2017/05/sach-mac-ke-thien-ha-song-nhu-nguoi-nhat-ebook.jpg",
                    "\n" +
                            "Sách \"Mặc kệ thiên hạ, sống như người Nhật\": \"Mặc hệ thiên hạ, sống như người Nhật\" chính là cuốn sách dành cho những người muốn đi bằng chính đôi chân mình, dành cho những người muốn gạt bỏ những nỗi sợ bởi chính tay mình, chứ không cầu cứu bất kì sự trợ giúp nào. Hãy thử sống một ngày “mặc kệ thiên hạ”, mặc kệ những lời nhận xét từ người khác. Hãy thử sống một ngày bạn cho phép mình từ bỏ, từ bỏ những thứ khó khăn, ngổn ngang lo lắng."));
            productList.add(new Product( "Trân trọng chính mình", 75000, 2,
                    "https://salt.tikicdn.com/cache/w444/ts/product/61/20/b4/8eec36b72ed7ce2b83c9aff44bb2a57e.jpg",
                    "Sách \"Trân trọng chính mình\": Trân trọng chính mình đề cập đến những nghịch lý bất biến trong các quan hệ xã hội, mà quan trọng nhất là nghịch lý giữa trao đi và nhận lại, đã được nhiều nhà hiền triết từ phương Đông tới phương Tây truyền giảng suốt bao đời nay. Qua phân tích và trích dẫn nhiều lời dạy thánh hiền, tác giả đã lật đổ những lầm tưởng về “Cái Tôi”, khẳng định tầm quan trọng của việc thấu hiểu và yêu thương bản thân, giúp mỗi chúng ta biết trân trọng chính mình để từ đó, biết trân trọng mọi người."));
            productList.add(new Product( "Tôi thấy hoa vàng trên cỏ xanh", 92000, 3,
                    "https://salt.tikicdn.com/cache/w1200/media/catalog/product/t/o/toi_thay_hoa_vang.jpg",
                    "\n" +
                            "Sách \"Tôi thấy hoa vàng trên cỏ xanh\": Có lẽ, nỗi vất vả của kiếp đời mưu sinh đã quá nhọc lòng trên đôi vai bé nhỏ nên những hình ảnh mộc mạc, thân thuộc thế này đã gợi không ít nỗi nhớ, niềm thương. Và bằng những dòng suy nghĩ chân thành, ngôn từ giản dị Nguyễn Nhật Ánh đã vẽ một chuyến đi đưa người đọc trở về thời thơ ấu. Không cần quá phô trương nhưng đủ sâu sắc, không cần quá thâm thúy nhưng đủ để chúng ta có thể nhìn thấy mình ở trong đó."));
            productList.add(new Product( "Nếu chỉ còn một ngày để sống", 72000, 4,
                    "https://bizweb.dktcdn.net/100/370/339/products/885845868fc875962cd9.jpg?v=1587042283737",
                    "\n" +
                            "Sách \"Nếu chỉ còn một ngày để sống\": “Nếu cuộc đời là một cuốn sách thì bạn đọc ngược từ dưới lên cũng sẽ chẳng có gì thay đổi. Hôm nay chẳng khác gì hôm qua. Ngày mai cũng giống hệt ngày hôm nay. Trong cuốn sách về cuộc đời em, chương nào cũng giống hệt chương nào, cho tới khi anh xuất hiện.”"));
            productList.add(new Product( "Đường đua của những giấc mơ", 72000, 4,
                    "https://salt.tikicdn.com/media/catalog/product/d/u/duong-dua-cua-nhung-giac-mo-03.u2769.d20170302.t181017.272234.jpg",
                    "Sách \"Đường đua của những giấc mơ\": Cuộc sống luôn phản bội vào lúc ta ít cảnh giác nhất. Thời khắc quyết định ấy, ai sẽ gục ngã, ai sẽ mạnh mẽ vực dậy hoàn toàn do chính bản thân bạn quyết định. Bỏ cuộc thì dễ kiên trì mới khó, có mấy ai bình yên mà không phải trải qua những ngày giông tố."));
            productList.add(new Product( "Cuộc sống đếch giống cuộc đời", 70000, 5,
                    "https://ohay.vn/blog/wp-content/uploads/2020/04/sach-hay-ve-cuoc-song-1.3.png",
                    "Sách \"Cuộc sống đếch giống cuộc đời\": “Cuộc sống đếch giống cuộc đời” là cuốn sách mang tính hài hước, trào phúng kể về những câu chuyện đời sống giản dị đời thường của chính tác giả, những câu chuyện đem đến tiếng cười cho độc giả. “Cuộc sống đếch giống cuộc đời” mang theo thông điệp dù cuộc sống của bạn có khó khăn vất vả thế nào thì mọi người hãy luôn suy nghĩ một cách tích cực và hài hước nhất."));
            productList.add(new Product( "Đừng lựa chọn an nhàn khi còn trẻ", 81000, 22,
                    "https://salt.tikicdn.com/ts/product/eb/62/6b/0e56b45bddc01b57277484865818ab9b.jpg",
                    "\n" +
                            "Sách \"Đừng lựa chọn an nhàn khi còn trẻ\": Ai trong chúng ta đều mong muốn có một cuộc sống viên mãn khi về già. Nhưng để có một cuộc sống “hưởng lạc” ấy thì khi còn trẻ chúng ta phải nỗ lực rất nhiều. Tại sao các tỷ phú họ giàu như vậy nhưng họ vẫn từng ngày từng ngày làm việc mà không dừng lại. Và nếu bạn lựa chọn an nhàn khi còn trẻ rất có thể sau này bạn sẽ phải hối tiếc về một tuổi trẻ đã qua."));
            productList.add(new Product( "Tôi bị bố bắt cóc", 48000, 33,
                    "https://salt.tikicdn.com/cache/w444/media/catalog/product/i/m/img518_3.jpg",
                    "Sách \"Tôi bị bố bắt cóc\": Tôi “bị” bố bắt cóc mở đầu thật bất ngờ với lời dẫn chuyện của nhân vật chính – Haru “Tôi bị BẮT CÓC, ngay ngày đầu tiên của kỳ nghỉ hè” mà thủ phạm lại chính là bố của Haru. Thế nhưng, chuyến đi của hai người – thủ phạm và nạn nhân, hay chính xác là bố và con, không hề mất đi tính gây cấn, hồi hộp của một chuyến “bắt cóc” và cũng không thiếu tính thú vị, trong trẻo, yêu thương của một chuyến “du lịch gia đình”."));
            productList.add(new Product( "Một lít nước mắt", 50000, 3,
                    "https://cf.shopee.vn/file/234ce6ff95ac43b49faa238553ab26d7",
                    "Sách \"Một lít nước mắt\": Truyện kể về cô bé Kito Aya 15 tuổi, bị chuẩn đoán mắc bệnh thoái hóa tiểu não – một căn bệnh không thể chữa được. “Một lít nước mắt” là quyển nhật kí ghi lại hành trình 8 năm chống trọi với bệnh tật của cô."));
            productList.add(new Product( "Đọc thân không cô đơn", 55000, 44,
                    "https://vcdn.tikicdn.com/cache/550x550/media/catalog/product/d/o/doc-than.u335.d20160520.t100026.jpg",
                    "Sách \"Đọc thân không cô đơn\": Độc Thân Không Cô Đơn “Khi một tình yêu ra đi thì sẽ có một tình yêu khác đến.” Có một người yêu thương, luôn sát cánh bên mình đi qua những sóng gió cuộc đời là mong ước của bất cứ ai."));
            productList.add(new Product( "Bà ngoại tôi gửi lời xin lỗi", 65000, 55,
                    "https://salt.tikicdn.com/cache/w1200/ts/product/9f/c4/1f/89a92c48d2ed021d4493db4f0a2f1de4.jpg",
                    "Sách \"Bà ngoại tôi gửi lời xin lỗi\": Có gì cổ xưa và quen thuộc hơn một câu chuyện về bà ngoại ?Cổ xưa, quen thuộc nhưng lại vô cùng ngọt ngào, ấm áp – đó là những tính từ mà ta có thể dùng để nói về người bà thân yêu của ta. Người không bao giờ nói “tạm biệt”, chỉ nói “hẹn gặp sau nhé cháu yêu”."));
            productList.add(new Product( "Những câu nói hay về sách và văn hóa đọc", 52000, 66,
                    "https://lh3.googleusercontent.com/proxy/b26SLmjIBbvVBe_gI8vUso989oFAiiZbAmQeWNMMfADLAVU5LIyS0J5k-TYSEyW1HXqEvJAy57MZdTOoCk9_MROuNV90DZBZyjNYqP8-kToqQalsj1cD8_AzTAlAZISddMFpvrrSp8FNWdQzBUgwPwqWcPTW921E0XEOVFhdQRr_4RAtX_4R5_hWQWXB2XNYAg",
                    "Sách \"Những câu nói hay về sách và văn hóa đọc\": Đối với tôi, những cuốn sách hay nhất mọi thời đại là những cuốn sách dành cho tôi khi tôi cần, cho dù đó là ngày hạnh phúc nhất trong cuộc đời tôi hay là ngày tồi tệ nhất."));

            List<Product> finalProductList = productList;
            runOnUiThread(() -> {
                for (Product p: finalProductList){
                    appDatabase.productDAO().insert(p);
                }
            });}
            List<Product> finalProductList1 = productList;
            runOnUiThread(() -> shopAdapter.setTasks(finalProductList1));
    }
}
