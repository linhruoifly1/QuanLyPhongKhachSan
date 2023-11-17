package com.example.x.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.x.database.DbHelper;
import com.example.x.model.Room;

import java.util.ArrayList;

public class RoomDAO {
    private final DbHelper dbHelper;
    public RoomDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    public boolean insert(Room room){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idType",room.getIdType());
        values.put("number",room.getNumber());
        values.put("status",room.getStatus());
        values.put("price",room.getPrice());
        long row = database.insert("room",null,values);
        return row>0;
    }
    public boolean update(Room room){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idType",room.getIdType());
        values.put("number",room.getNumber());
        values.put("status",room.getStatus());
        values.put("price",room.getPrice());
        long row = database.update("room",values,"id=?",new String[]{String.valueOf(room.getId())});
        return row>0;
    }
    public boolean changeStatus(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String sql = "update room set status=0 where id=?";
        database.execSQL(sql,new String[]{String.valueOf(id)});
        return true;
    }
    public boolean delete(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long row = database.delete("room","id=?",new String[]{String.valueOf(id)});
        return row>0;
    }
    public Room getId(String id){
        String sql = "select * from room where id=?";
        ArrayList<Room> list = getData(sql,id);
        return list.get(0);
    }
    public ArrayList<Room> getAll(){
        String sql = "select * from room";
        return getData(sql);
    }
    private ArrayList<Room> getData(String sql,String...selectionArgs){
        ArrayList<Room> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            Room room=new Room();
            room.setId(cursor.getInt(0));
            room.setIdType(cursor.getInt(1));
            room.setNumber(cursor.getInt(2));
            room.setStatus(cursor.getInt(3));
            room.setPrice(cursor.getInt(4));
            list.add(room);
        }
        return list;
    }
}
