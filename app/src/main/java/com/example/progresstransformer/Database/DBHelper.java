package com.example.progresstransformer.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "usersdb";
    private static final String TABLE_Users = "videodetails";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_URL = "url";
    private static final String KEY_Progress = "progress";
    public DBHelper(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_URL + " TEXT,"
                + KEY_Progress + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);
    }
    public void insertData(String name,String url,String progress){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_URL, url);
        cValues.put(KEY_Progress, progress);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users,null, cValues);
        db.close();
    }
    public ArrayList<HashMap<String, String>> getvideoData(String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, location, designation FROM "+ TABLE_Users;
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_NAME, KEY_Progress}, KEY_URL+ "=?",new String[]{url},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("progress",cursor.getString(cursor.getColumnIndex(KEY_Progress)));
            userList.add(user);
        }
        return  userList;
    }
    public int updateProgress(String progress, String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_Progress, progress);
        int count = db.update(TABLE_Users, cVals, KEY_URL+" = ?",new String[]{url});
        return  count;
    }
}
