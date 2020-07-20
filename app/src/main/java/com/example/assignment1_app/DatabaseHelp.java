package com.example.assignment1_app;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class DatabaseHelp {
    public static final String DB_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DB_name = "logindb";
    public static final String USER_INFO_TABLE = "user_info";
    public static final String GPS_INFO_TABLE = "gps_info";
    private SQLiteDatabase database;
    private static final String TAG = "DatabaseHelp";

    private static final String CREATE_USER_TABLE = "create table" + USER_INFO_TABLE + "(_id integer primary key autoincrement, username text not null, password text not null);";
    private static final String CREATE_GPS_TABLE = "create table" + GPS_INFO_TABLE + "(_id integer primary key autoincrement, user_id integer not null, latitude real not null, longitude real not null);";

    public DatabaseHelp() {
        try {
            database = SQLiteDatabase.openDatabase(DB_FILE_PATH + File.separator + DB_name, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLException ex) {
            Log.e(TAG, "error --" + ex.getMessage(), ex);
            createTables();
        } finally {
            database.close();
        }
    }

    private void createTables() {
        database.execSQL(CREATE_USER_TABLE);
        database.execSQL(CREATE_GPS_TABLE);
    }

    public SQLiteDatabase readDatabase() {
        database = SQLiteDatabase.openDatabase(DB_FILE_PATH + File.separator + DB_name, null, SQLiteDatabase.OPEN_READONLY);
        return database;
    }

    public SQLiteDatabase writeDatabase() {
        database = SQLiteDatabase.openDatabase(DB_FILE_PATH + File.separator + DB_name, null, SQLiteDatabase.OPEN_READWRITE);
        return database;
    }

    public void close() {
        database.close();
    }

}
