package com.example.ayomakan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ayomakan.sqlite.DbConfig;

public class EditProfileActivity extends AppCompatActivity {

    EditText et_name, et_number, et_address;
    Button btn_simpan;
    ImageView iv_back;
    private DbConfig dbConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbConfig = new DbConfig(this);

        et_name = findViewById(R.id.et_username);
        et_number = findViewById(R.id.et_number);
        et_address = findViewById(R.id.et_address);
        btn_simpan = findViewById(R.id.btn_save);
        iv_back = findViewById(R.id.iv_back);

        loadUserData();

        iv_back.setOnClickListener(view -> {
            finish();
        });

        btn_simpan.setOnClickListener(view -> {
            String name = et_name.getText().toString();
            String number = et_number.getText().toString();
            String address = et_address.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(EditProfileActivity.this, "Please fill the name field", Toast.LENGTH_SHORT).show();
                return;
            }

            Integer phoneNumber = null;
            if (number.isEmpty()) {
                phoneNumber = 0;  // Default value for phoneNumber
            } else {
                try {
                    phoneNumber = Integer.parseInt(number);
                } catch (NumberFormatException e) {
                    Toast.makeText(EditProfileActivity.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (address.isEmpty()) {
                address = "-";  // Default value for address
            }

            saveUserData(name, phoneNumber, address);
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
            Toast.makeText(EditProfileActivity.this, "Data successfully saved", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadUserData() {
        SQLiteDatabase db = dbConfig.getReadableDatabase();
        Cursor cursor = db.query(
                DbConfig.TABLE_NAME,
                new String[]{DbConfig.COLUMN_USERNAME, DbConfig.COLUMN_PHONE, DbConfig.COLUMN_ADDRESS},
                DbConfig.COLUMN_IS_LOGGED_IN + " = ?",
                new String[]{"1"},
                null, null, null);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DbConfig.COLUMN_USERNAME));
            int phone = cursor.getInt(cursor.getColumnIndexOrThrow(DbConfig.COLUMN_PHONE));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(DbConfig.COLUMN_ADDRESS));

            et_name.setText(name);
            et_number.setText(String.valueOf(phone));
            et_address.setText(address);
        }

        cursor.close();
        db.close();
    }

    private void saveUserData(String name, int number, String address) {
        SQLiteDatabase db = dbConfig.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbConfig.COLUMN_USERNAME, name);
        values.put(DbConfig.COLUMN_PHONE, number);
        values.put(DbConfig.COLUMN_ADDRESS, address);

        db.update(DbConfig.TABLE_NAME, values, DbConfig.COLUMN_IS_LOGGED_IN + " = ?", new String[]{"1"});
        db.close();
    }
}