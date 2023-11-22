package com.example.x.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.x.database.DbHelper;
import com.example.x.model.Type;

import java.util.ArrayList;

public class TypeDAO {
    private final DbHelper dbHelper;
    public TypeDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    public boolean insert(Type type){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",type.getName());
        long row = database.insert("type",null,values);
        return row>0;
    }
    public boolean update(Type type){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",type.getName());
        long row = database.update("type",values,"id=?",new String[]{String.valueOf(type.getId())});
        return row>0;
    }
    public int updateStatus(Type type){
        ContentValues values = new ContentValues();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        values.put("status",type.getId());

        return database.update("type",values,"id=?",new String[]{String.valueOf(type.getId())});
    }
    public Type getId(String id){
        String sql = "select * from type where id=?";
        ArrayList<Type> list = getData(sql,id);
        return list.get(0);
    }
    public ArrayList<Type> getAll(){
        String sql = "select * from type where status = 0";
        return getData(sql);
    }
    private ArrayList<Type> getData(String sql,String...selectionArgs){
        ArrayList<Type> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            Type type=new Type();
            type.setId(cursor.getInt(0));
            type.setName(cursor.getString(1));
            list.add(type);
        }
        return list;
    }
}
