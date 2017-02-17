package com.example.mierul.myapplication10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by mierul on 4/2/2016.
 */
public class DBhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="amierul.db";
    private static final int SCHEMA_VERSION=1;

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE amierul(_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,REMINDER TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS amierul");
        onCreate(db);
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM amierul WHERE _id="+id+"", null );
        return res;
    }

    public void insertReminder(String reminder,String name){

        Log.e("inserted", "inserted reminder");
        getWritableDatabase().execSQL("UPDATE amierul SET REMINDER='"+reminder+"' WHERE NAME='"+name+"'");
    }

    public void insert(String name)
    {
        ContentValues cv=new ContentValues();
        cv.put("REMINDER","default");
        cv.put("NAME",name);
        Log.e("inserted", "inserted");
        getWritableDatabase().insert("amierul", null, cv);

    }

    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT NAME FROM amierul", null );
        return res;
    }

    public ArrayList<String> getAllName(){

        ArrayList<String> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from amierul", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex("NAME")));
            res.moveToNext();
        }

        res.close();
        return array_list;
    }

    public ArrayList<String> getAllReminder(){

        ArrayList<String> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from amierul", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex("REMINDER")));
            res.moveToNext();
        }

        res.close();
        return array_list;
    }
}
