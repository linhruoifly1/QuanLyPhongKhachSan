package com.example.x.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.x.database.DbHelper;
import com.example.x.model.Customer;
import com.example.x.model.HardBill;

import java.util.ArrayList;

public class HardBillDAO {
    private final DbHelper dbHelper;
    public HardBillDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    public boolean insert(HardBill hardBill){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idBill",hardBill.getIdBill());
        values.put("idRoom",hardBill.getIdRoom());
        values.put("quantityPeople",hardBill.getQuantityPeople());
        long row = database.insert("hardBill",null,values);
        return row>0;
    }
    public boolean update(HardBill hardBill){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idBill",hardBill.getIdBill());
        values.put("idRoom",hardBill.getIdRoom());
        values.put("quantityPeople",hardBill.getQuantityPeople());
        long row = database.update("hardBill",values,"id=?",new String[]{String.valueOf(hardBill.getId())});
        return row>0;
    }
    public boolean delete(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long row = database.delete("hardBill","id=?",new String[]{String.valueOf(id)});
        return row>0;
    }
    public int getCostRoom(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String sql = "select sum(price) as sumCost from hardBill inner join room on hardBill.idRoom = room.id where hardBill.idBill = ?";
        ArrayList<Integer> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id)});
        while (cursor.moveToNext()){
            try{
                list.add(cursor.getInt(0));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }
    public HardBill getId(String id){
        String sql = "select * from hardBill where id=?";
        ArrayList<HardBill> list = getData(sql,id);
        return list.get(0);
    }
    public ArrayList<HardBill> getAll(){
        String sql = "select * from hardBill";
        return getData(sql);
    }
    private ArrayList<HardBill> getData(String sql, String...selectionArgs){
        ArrayList<HardBill> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            HardBill hardBill=new HardBill();
            hardBill.setId(cursor.getInt(0));
            hardBill.setIdBill(cursor.getInt(1));
            hardBill.setIdRoom(cursor.getInt(2));
            hardBill.setQuantityPeople(cursor.getInt(3));
            list.add(hardBill);
        }
        return list;
    }
}
