package com.example.moneymanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.Model.User;
import com.example.moneymanager.SQLite.UserDb;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUserName, edtPassword;
    private Button btnSubmit, btnRegis;
    private UserDb userDb;

    private TextView tvError;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUserName = findViewById(R.id.edtUserNameLog);
        edtPassword = findViewById(R.id.edtPasswordLog);
        btnRegis = findViewById(R.id.registerChange);
        btnSubmit = findViewById(R.id.btnSubmitLog);
        tvError = findViewById(R.id.tvError);
        userDb = new UserDb(LoginActivity.this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String username = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            edtUserName.setError("Username can be not empty");
            return;
        }
        if (TextUtils.isEmpty(password)){
            edtPassword.setError("Password can be not empty");
            return;
        }

        User infoUser = userDb.getSingleUser(username, password);
        assert infoUser != null;
        if(infoUser.getEmail() != null && infoUser.getPhone() != null) {
//            tai khoan ton tai trong db
            tvError.setText("");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
//            dang nhap sai
            tvError.setText("Account invalid");
            return;
        }
    }
}
