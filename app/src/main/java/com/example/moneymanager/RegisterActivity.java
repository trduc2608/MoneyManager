package com.example.moneymanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.SQLite.UserDb;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword, edtEmail, edtPhone;
    private Button btnSubmit, btnChangeLogin;
    private UserDb userDb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUsername = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        btnSubmit = findViewById(R.id.btnSubmitRegis);
        btnChangeLogin = findViewById(R.id.btnLoginChange);
        userDb = new UserDb(RegisterActivity.this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertUser();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnChangeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void insertUser(){
        String user = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        if(TextUtils.isEmpty(user)){
            edtUsername.setError("Username can be not empty");
            return;
        }
        if(TextUtils.isEmpty(password)){
            edtPassword.setError("Password can be not empty");
            return;
        }
        if(TextUtils.isEmpty(email)){
            edtEmail.setError("Password can be not empty");
            return;
        }
        if(TextUtils.isEmpty(phone)){
            edtPhone.setError("Password can be not empty");
            return;
        }
        long result = userDb.addNewUser(user, password, email, phone);
        if(result == -1) {
            Toast.makeText(RegisterActivity.this, "Sign up fail!", Toast.LENGTH_LONG).show();
            return;
        } else {
            Toast.makeText(RegisterActivity.this, "Sign up successfully!", Toast.LENGTH_LONG).show();
        }
    }
}
