package com.example.assignment1_app;

import android.content.Context;
import android.database.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DatabaseHelp extends SQLiteOpenHelper {

    private static DatabaseHelp instance;
    public static final int DB_VERSION = 1;
    public static final String DB_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DB_name = "logindb";
    public static final String USER_INFO_TABLE = "user_info";
    public static final String GPS_INFO_TABLE = "gps_info";
    public static final String CHAT_INFO_TABLE = "chat_info";
    private SQLiteDatabase database;
    private static final String TAG = "FROM_DatabaseHelp";

    private static final String CREATE_USER_TABLE = "create table if not exists " + USER_INFO_TABLE + "(_id integer primary key autoincrement, username text not null, password text not null);";
    private static final String CREATE_GPS_TABLE = "create table if not exists " + GPS_INFO_TABLE + "(timestamp real primary key, latitude real not null, longitude real not null);";
    private static final String CHAT_TABLE = "create table if not exists " + CHAT_INFO_TABLE + "(_id integer primary key autoincrement,chat text not null);";

    public DatabaseHelp(Context context) {
        super(context,DB_name,null,DB_VERSION);
//        try {
//            SQLiteDatabase.openDatabase()
//            database = SQLiteDatabase.openDatabase(DB_FILE_PATH + File.separator + DB_name, null, SQLiteDatabase.OPEN_READWRITE);
//        } catch (SQLException ex) {
//            Log.e(TAG, "error --" + ex.getMessage(), ex);
//            createTables();
//        } finally {
//            database.close();
//        }

    }


    private void createTables() {
        database.execSQL(CREATE_USER_TABLE);
        database.execSQL(CREATE_GPS_TABLE);
    }

//    public SQLiteDatabase readDatabase() {
//        database = SQLiteDatabase.openDatabase(DB_FILE_PATH + File.separator + DB_name, null, SQLiteDatabase.OPEN_READONLY);
//        return database;
//    }
//
//    public SQLiteDatabase writeDatabase() {
//        database = SQLiteDatabase.openDatabase(DB_FILE_PATH + File.separator + DB_name, null, SQLiteDatabase.OPEN_READWRITE);
//        return database;
//    }

    public String getDbFilePath(){
        return DB_FILE_PATH + File.separator + DB_name;
    }

    public void addUser(String username, String password) {
        String userInfo = "('" + username + "','" + password + "');";
        String ADD_USER = "INSERT INTO " + USER_INFO_TABLE + "(username,password) VALUES" + userInfo;
        database.execSQL(ADD_USER);
    }

    public void addLatLng(float timestamp, float lat, float lng) {
        String latlngInfo = "(" + timestamp + "," + lat + "," + lng + ");";
        String ADD_LATLNG = "INSERT INTO " + GPS_INFO_TABLE + " VALUES" + latlngInfo;
        database.execSQL(ADD_LATLNG);
    }

    public void addChat(String chat){
        String ADD_CHAT = "INSERT INTO" + CHAT_INFO_TABLE + " VALUES('"+chat+"');";
        database.execSQL(ADD_CHAT);
    }

    public void close() {
        database.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_GPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    static public synchronized DatabaseHelp getInstance(Context context){
        if(instance == null){
            instance = new DatabaseHelp(context);
        }
        return instance;
    }
}