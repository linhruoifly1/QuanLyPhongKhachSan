package com.example.x.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.x.database.DbHelper;
import com.example.x.model.Service;

import java.util.ArrayList;

public class ServiceDAO {
    private final DbHelper dbHelper;
    public ServiceDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    public long insert(Service service){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",service.getName());
        values.put("price",service.getPrice());
        values.put("status",service.getStatus());
        return  database.insert("service",null,values);

    }
    public boolean update(Service service){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",service.getName());
        values.put("price",service.getPrice());
        long row = database.update("service",values,"id=?",new String[]{String.valueOf(service.getId())});
        return row>0;
    }
    public boolean delete(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long row = database.delete("service","id=?",new String[]{String.valueOf(id)});
        return row>0;
    }
    public Service getId(String id){
        String sql = "select * from service where id=?";
        ArrayList<Service> list = getData(sql,id);
        return list.get(0);
    }
    public ArrayList<Service> getAll(){
        String sql = "select * from service where status = 0";
        return getData(sql);
    }
    private ArrayList<Service> getData(String sql,String...selectionArgs){
        ArrayList<Service> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            Service service=new Service();
            service.setId(cursor.getInt(0));
            service.setName(cursor.getString(1));
            service.setPrice(cursor.getInt(2));
            service.setStatus(cursor.getInt(3));
            list.add(service);
        }
        return list;
    }
}
