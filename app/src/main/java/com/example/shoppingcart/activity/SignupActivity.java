package com.example.shoppingcart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.shoppingcart.AppDatabase;
import com.example.shoppingcart.Constants;
import com.example.shoppingcart.R;
import com.example.shoppingcart.models.User;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    //    components
    EditText txtUsername, txtPassword, txtPasswordAgain;
    ImageView imgView, imgView2;
    Button btnSignin, btnSignup;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
//        get view
        this.txtUsername = findViewById(R.id.txtUsername);
        this.txtPassword = findViewById(R.id.txtPassword);
        this.txtPasswordAgain = findViewById(R.id.txtPasswordAgain);
        this.imgView = findViewById(R.id.imgView);
        this.btnSignin = findViewById(R.id.btnSignin);
        this.btnSignup = findViewById(R.id.btnSignup);
        this.imgView2 = findViewById(R.id.imgView2);
        this.appDatabase = Room.databaseBuilder(SignupActivity.this, AppDatabase.class, "app-database").allowMainThreadQueries().build();

        this.btnSignin.setOnClickListener(this::onClick);
        this.btnSignup.setOnClickListener(this::onClick);
        this.imgView.setOnClickListener(this::onClick);
        this.imgView2.setOnClickListener(this::onClick);

        txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        txtPasswordAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignin:
                signIn();
                break;
            case R.id.btnSignup:
                signUp();
                break;
            case R.id.imgView:
                showPassword();
                break;
            case R.id.imgView2:
                showPassword2();
                break;
        }
    }

    private void showPassword2() {
        if (txtPasswordAgain.getTransformationMethod() == PasswordTransformationMethod.getInstance()){
            imgView2.setImageResource(R.drawable.outline_visibility_off_black_24dp);
            txtPasswordAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else{
            imgView2.setImageResource(R.drawable.outline_visibility_black_24dp);
            txtPasswordAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void showPassword() {
        if (txtPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()){
            imgView.setImageResource(R.drawable.outline_visibility_off_black_24dp);
            txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else{
            imgView.setImageResource(R.drawable.outline_visibility_black_24dp);
            txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void signUp() {
        if (validateInput()){
            User user = appDatabase.userDAO().getUserByUsername(txtUsername.getText().toString());
            if (user != null){
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Tài khoản đã tồn tại!", Toast.LENGTH_LONG).show());
            }
            else{
                user = new User(txtUsername.getText().toString(), txtPassword.getText().toString());
                appDatabase.userDAO().insert(user);
                Constants.USERNAME = txtUsername.getText().toString();
                startActivity(new Intent(SignupActivity.this, ShopActivity.class));
            }
        }
    }

    private void signIn() {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    boolean validateInput(){
        if (TextUtils.isEmpty(txtUsername.getText())){
            Toast.makeText(SignupActivity.this, "Tên đăng nhập không được để trống!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(txtPassword.getText())){
            Toast.makeText(SignupActivity.this, "Mật khẩu không được để trống!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(txtPasswordAgain.getText())){
            Toast.makeText(SignupActivity.this, "Nhập lại mật khẩu không được để trống!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!txtPassword.getText().toString().equals(txtPasswordAgain.getText().toString())){
            Toast.makeText(SignupActivity.this, "Mật khẩu phải khớp nhau!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
