package com.example.shiyan2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "diary.db";
    private static final int DB_VERSION = 1;
    public static final String CREATE_DIARY = "CREATE TABLE diary ("
            +"id INTEGER PRIMARY KEY,"
            +"title TEXT,"
            +"content TEXT,"
            +"createTime TEXT,"
            +"author TEXT)";
    private Context context;
    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //增
    public long addDiary(Diary diary){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",diary.getTitle());
        values.put("content",diary.getContent());
        values.put("createTime",diary.getCreateTime());
        values.put("author",diary.getAuthor());
        long id = db.insert("diary",null,values);
        db.close();
        return id;
    }
    //改
    public int updateDiary(Diary diary) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", diary.getTitle());
        values.put("content", diary.getContent());
        values.put("createTime", diary.getCreateTime());
        values.put("author", diary.getAuthor());
        int rows = db.update("diary", values, "id = ?", new String[]{String.valueOf(diary.getId())});
        db.close();
        return rows;
    }
    //删
    public int deleteDiary(int id){
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete("diary","id = ?",new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }
    //查
    public List<Diary> getAllDiaries(){
        List<Diary> diaries = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("diary",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                Diary diary = new Diary(id, title, content, createTime, author);
                diaries.add(diary);
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return diaries;
    }
    public List<Diary> getDiaries(String[] Author){
        List<Diary> diaries = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("diary",null,"author = ?",Author,null,null,null);
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                Diary diary = new Diary(id, title, content, createTime, author);
                diaries.add(diary);
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return diaries;
    }
}
