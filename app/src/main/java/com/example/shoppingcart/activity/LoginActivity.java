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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
//    components
    EditText txtUsername, txtPassword;
    ImageView imgView;
    Button btnSignin, btnSignup;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        get view
        this.txtUsername = findViewById(R.id.txtUsername);
        this.txtPassword = findViewById(R.id.txtPassword);
        this.imgView = findViewById(R.id.imgView);
        this.btnSignin = findViewById(R.id.btnSignin);
        this.btnSignup = findViewById(R.id.btnSignup);
        this.appDatabase = Room.databaseBuilder(LoginActivity.this, AppDatabase.class, "app-database").allowMainThreadQueries().build();

        this.btnSignin.setOnClickListener(this::onClick);
        this.btnSignup.setOnClickListener(this::onClick);
        this.imgView.setOnClickListener(this::onClick);

        txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void signIn() {
        if (validateInput()){
            User user = appDatabase.userDAO().getUserByUsername(txtUsername.getText().toString());
            if (user == null){
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "T??i kho???n kh??ng t???n t???i!", Toast.LENGTH_LONG).show());
            }
            else{
                if (user.getPassword().equals(txtPassword.getText().toString())){
                    Constants.USERNAME = txtUsername.getText().toString();
                    startActivity(new Intent(LoginActivity.this, ShopActivity.class));
                }
                else {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "M???t kh???u kh??ng ????ng!", Toast.LENGTH_LONG).show());
                }
            }
        }
    }

    boolean validateInput(){
        if (TextUtils.isEmpty(txtUsername.getText())){
            Toast.makeText(LoginActivity.this, "T??n ????ng nh???p kh??ng ???????c ????? tr???ng!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(txtPassword.getText())){
            Toast.makeText(LoginActivity.this, "M???t kh???u kh??ng ???????c ????? tr???ng!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
