package com.example.ayomakan.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DbConfig extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database-restaurant";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "restaurant";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_IS_LOGGED_IN = "isLoggedIn";

    public static final String FAVORITES_TABLE_NAME = "favorite";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_RESTO_ID = "resto_id";

    public DbConfig(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +













                " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_ADDRESS + " TEXT,"
                + COLUMN_IS_LOGGED_IN + " INTEGER DEFAULT 0)");

        db.execSQL("CREATE TABLE " + FAVORITES_TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_ID + " INTEGER,"
                + COLUMN_RESTO_ID + " TEXT)");
    }

    public void insertData(String username, String password, String phone, String address) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_IS_LOGGED_IN, 0);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteRecords(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID}, COLUMN_USERNAME + " = ?", new String[]{username}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    public String getUsername() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USERNAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_IS_LOGGED_IN + " = 1", null);
        String username = "";
        if (cursor.moveToFirst()) {
            int usernameIndex = cursor.getColumnIndexOrThrow(COLUMN_USERNAME);
            username = cursor.getString(usernameIndex);
        }
        cursor.close();
        db.close();
        return username;
    }

    public void insertFavorite(int userId, String restoId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_RESTO_ID, restoId);
        db.insert(FAVORITES_TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getFavoriteRestoUserId(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(FAVORITES_TABLE_NAME, null, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)}, null, null, null);
    }

    public void deleteFavorite(int userId, String restoId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(FAVORITES_TABLE_NAME, COLUMN_USER_ID + " = ? AND " + COLUMN_RESTO_ID + " = ?", new String[]{String.valueOf(userId), String.valueOf(restoId)});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}