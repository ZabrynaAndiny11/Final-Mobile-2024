package com.example.ayomakan.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbConfig extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database-restaurant";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "restaurant";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";  // corrected from "usename" to "username"
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_IS_LOGGED_IN = "isLoggedIn";

    public DbConfig(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"  // corrected
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_IS_LOGGED_IN + " INTEGER DEFAULT 0)");
    }

    public void insertData(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);  // corrected
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_IS_LOGGED_IN, 0);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteRecords(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public String getColumnId() {
        return COLUMN_ID;
    }

    public String getColumnNim() {
        return COLUMN_USERNAME;  // corrected
    }

    // Method to get the username of the logged-in user
    public String getUsername() {
        SQLiteDatabase db = this.getReadableDatabase();
        String username = "";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT " + COLUMN_USERNAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_IS_LOGGED_IN + " = 1", null);
            if (cursor.moveToFirst()) {
                int usernameIndex = cursor.getColumnIndexOrThrow(COLUMN_USERNAME);
                username = cursor.getString(usernameIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return username;
    }

}
