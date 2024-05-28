package com.example.ayomakan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ayomakan.sqlite.DbConfig;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText et_username;
    TextInputEditText et_password;
    TextView tv_login;
    Button btn_register;
    DbConfig dbConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbConfig = new DbConfig(this);

        et_username = findViewById(R.id.username2);
        et_password = findViewById(R.id.password2);
        btn_register = findViewById(R.id.btnRegister);
        tv_login = findViewById(R.id.login);

        btn_register.setOnClickListener(v -> {
            String username = et_username.getText().toString().trim();
            String password = et_password.getText().toString().trim();

            if (!username.isEmpty() && !password.isEmpty()) {
                dbConfig.insertData(username, password);

                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                if (username.isEmpty()) {
                    et_username.setError("Please enter your username");
                } else {
                    et_password.setError("Please enter your password");
                }
            }
        });

        tv_login.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}