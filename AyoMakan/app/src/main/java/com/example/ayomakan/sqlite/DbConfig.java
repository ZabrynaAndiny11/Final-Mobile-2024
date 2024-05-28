package com.example.ayomakan.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbConfig extends SQLiteOpenHelper {

    private static final String TAG = "DbConfig";

    private static final String DATABASE_NAME = "database-restaurant";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "restaurant";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";  // corrected from "usename" to "username"
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_IS_LOGGED_IN = "isLoggedIn";

    public DbConfig(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_USERNAME + " TEXT,"  // corrected
                    + COLUMN_PASSWORD + " TEXT,"
                    + COLUMN_PHONE + " TEXT,"  // changed to TEXT to handle phone numbers correctly
                    + COLUMN_ADDRESS + " TEXT,"
                    + COLUMN_IS_LOGGED_IN + " INTEGER DEFAULT 0)");
        } catch (Exception e) {
            Log.e(TAG, "Error creating table", e);
        }
    }

    public void insertData(String username, String password, String phone, String address) {  // added phone and address parameters
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, username);  // corrected
            values.put(COLUMN_PASSWORD, password);
            values.put(COLUMN_PHONE, phone);  // added
            values.put(COLUMN_ADDRESS, address);  // added
            values.put(COLUMN_IS_LOGGED_IN, 0);
            db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e(TAG, "Error inserting data", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void deleteRecords(int id) {  // added @Override annotation
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.e(TAG, "Error deleting record", e);
        } finally {
            if (db != null) {
                db.close();  // close the database
            }
        }
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        boolean exists = false;
        try {
            db = getReadableDatabase();
            cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID}, COLUMN_USERNAME + " = ?", new String[]{username}, null, null, null);
            exists = (cursor.getCount() > 0);
        } catch (Exception e) {
            Log.e(TAG, "Error checking if username exists", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return exists;
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

    public String getColumnUsername() {
        return COLUMN_USERNAME;
    }

    public String getColumnPhone() {
        return COLUMN_PHONE;
    }

    public String getColumnAddress() {
        return COLUMN_ADDRESS;
    }

    // Method to get the username of the logged-in user
    public String getUsername() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String username = "";
        try {
            db = getReadableDatabase();
            cursor = db.rawQuery("SELECT " + COLUMN_USERNAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_IS_LOGGED_IN + " = 1", null);
            if (cursor.moveToFirst()) {
                int usernameIndex = cursor.getColumnIndexOrThrow(COLUMN_USERNAME);
                username = cursor.getString(usernameIndex);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting logged-in username", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return username;
    }
}
