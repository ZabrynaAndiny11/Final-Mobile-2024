package com.example.ayomakan.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.ayomakan.EditProfileActivity;
import com.example.ayomakan.LoginActivity;
import com.example.ayomakan.R;
import com.example.ayomakan.sqlite.DbConfig;

public class ProfileFragment extends Fragment {

    private TextView tv_welcome, tv_name, tv_number, tv_address;
    private ImageView iv_logout, iv_delete;
    private Button btn_change;
    private DbConfig dbConfig;
    private int recordId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbConfig = new DbConfig(getContext());

        iv_logout = view.findViewById(R.id.btn_logout);
        tv_welcome = view.findViewById(R.id.tv_welcome);
        tv_name = view.findViewById(R.id.tv_name);
        tv_number = view.findViewById(R.id.tv_number);
        tv_address = view.findViewById(R.id.tv_address);
        btn_change = view.findViewById(R.id.btn_edit);
        iv_delete = view.findViewById(R.id.iv_delete);

        loadUserData();

        iv_logout.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                showLogoutConfirmationDialog();
            } else {
                Toast.makeText(getContext(), "No internet connection available", Toast.LENGTH_SHORT).show();
            }
        });

        iv_delete.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                dbConfig.deleteRecords(recordId);
                showDeleteConfirmationDialog();
            } else {
                Toast.makeText(getContext(), "No internet connection available", Toast.LENGTH_SHORT).show();
            }
        });

        btn_change.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                startActivityForResult(new Intent(getActivity(), EditProfileActivity.class), 1);
            } else {
                Toast.makeText(getContext(), "No internet connection available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadUserData() {
        try (SQLiteDatabase db = dbConfig.getReadableDatabase();
             Cursor cursor = db.query(
                     DbConfig.TABLE_NAME,
                     new String[]{DbConfig.COLUMN_ID, DbConfig.COLUMN_USERNAME, DbConfig.COLUMN_PHONE, DbConfig.COLUMN_ADDRESS},
                     DbConfig.COLUMN_IS_LOGGED_IN + " = ?",
                     new String[]{"1"},
                     null, null, null)) {

            if (cursor.moveToFirst()) {
                recordId = cursor.getInt(cursor.getColumnIndexOrThrow(DbConfig.COLUMN_ID));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(DbConfig.COLUMN_USERNAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(DbConfig.COLUMN_PHONE));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(DbConfig.COLUMN_ADDRESS));

                tv_welcome.setText("Halo, " + username + "!");
                tv_name.setText(username);
                tv_number.setText(String.valueOf(phone));
                tv_address.setText(address);
            }
        }
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> logoutUser())
                .setNegativeButton("No", null)
                .show();
    }

    private void logoutUser() {
        try (SQLiteDatabase db = dbConfig.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(DbConfig.COLUMN_IS_LOGGED_IN, 0);
            db.update(DbConfig.TABLE_NAME, values, DbConfig.COLUMN_IS_LOGGED_IN + " = ?", new String[]{"1"});
        }

        startActivity(new Intent(getActivity(), LoginActivity.class));
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete Account")
                .setMessage("Are you sure to delete the account?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    dbConfig.deleteRecords(recordId);
                    logoutUser();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                loadUserData();
            }
        }
    }
}
