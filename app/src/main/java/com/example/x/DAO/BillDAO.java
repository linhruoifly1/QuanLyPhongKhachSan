package com.example.x.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.x.database.DbHelper;
import com.example.x.model.Bill;
import com.example.x.model.Room;

import java.util.ArrayList;

public class BillDAO {
    private final DbHelper dbHelper;
    public BillDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    public boolean insert(Bill bill){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idCustomer",bill.getIdCustomer());
        values.put("idReceptionist",bill.getIdReceptionist());
        values.put("idService",bill.getIdService());
        values.put("checkIn",bill.getCheckIn());
        values.put("checkOut",bill.getCheckOut());
        values.put("costRoom",bill.getCostRoom());
        values.put("VAT",bill.getVAT());
        values.put("status",bill.getStatus());
        values.put("sumCost",bill.getSumCost());
        long row = database.insert("bill",null,values);
        return row>0;
    }
    public boolean update(Bill bill){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idCustomer",bill.getIdCustomer());
        values.put("idReceptionist",bill.getIdReceptionist());
        values.put("idService",bill.getIdService());
        values.put("checkIn",bill.getCheckIn());
        values.put("checkOut",bill.getCheckOut());
        values.put("costRoom",bill.getCostRoom());
        values.put("VAT",bill.getVAT());
        values.put("status",bill.getStatus());
        values.put("sumCost",bill.getSumCost());
        long row = database.update("bill",values,"id=?",new String[]{String.valueOf(bill.getId())});
        return row>0;
    }
    public boolean changeStatus(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String sql = "update bill set status=1 where id=?";
        database.execSQL(sql,new String[]{String.valueOf(id)});
        return true;
    }
    public boolean delete(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long row = database.delete("bill","id=?",new String[]{String.valueOf(id)});
        return row>0;
    }
    public Bill getId(String id){
        String sql = "select * from bill where id=?";
        ArrayList<Bill> list = getData(sql,id);
        return list.get(0);
    }
    public ArrayList<Bill> getAll(){
        String sql = "select * from bill";
        return getData(sql);
    }
    private ArrayList<Bill> getData(String sql, String...selectionArgs){
        ArrayList<Bill> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            Bill bill = new Bill();
            bill.setId(cursor.getInt(0));
            bill.setIdCustomer(cursor.getInt(1));
            bill.setIdReceptionist(cursor.getInt(2));
            bill.setIdService(cursor.getInt(3));
            bill.setCheckIn(cursor.getString(4));
            bill.setCheckOut(cursor.getString(5));
            bill.setCostRoom(cursor.getInt(6));
            bill.setVAT(cursor.getInt(7));
            bill.setStatus(cursor.getInt(8));
            bill.setSumCost(cursor.getInt(9));
            list.add(bill);
        }
        return list;
    }
}
