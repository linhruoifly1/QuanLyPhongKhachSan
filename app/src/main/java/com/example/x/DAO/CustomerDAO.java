package com.example.x.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.x.database.DbHelper;
import com.example.x.model.Customer;
import com.example.x.model.Room;

import java.util.ArrayList;

public class CustomerDAO {
    private final DbHelper dbHelper;
    public CustomerDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    public boolean insert(Customer customer){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",customer.getName());
        values.put("phone",customer.getPhone());
        values.put("email",customer.getEmail());
        values.put("birth",customer.getBirth());
        long row = database.insert("customer",null,values);
        return row>0;
    }
    public boolean update(Customer customer){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",customer.getName());
        values.put("phone",customer.getPhone());
        values.put("email",customer.getEmail());
        values.put("birth",customer.getBirth());
        long row = database.update("customer",values,"id=?",new String[]{String.valueOf(customer.getId())});
        return row>0;
    }
    public boolean delete(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long row = database.delete("customer","id=?",new String[]{String.valueOf(id)});
        return row>0;
    }
    public Customer getId(String id){
        String sql = "select * from customer where id=?";
        ArrayList<Customer> list = getData(sql,id);
        return list.get(0);
    }
    public ArrayList<Customer> getAll(){
        String sql = "select * from customer";
        return getData(sql);
    }
    private ArrayList<Customer> getData(String sql,String...selectionArgs){
        ArrayList<Customer> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            Customer customer=new Customer();
            customer.setId(cursor.getInt(0));
            customer.setName(cursor.getString(1));
            customer.setPhone(cursor.getString(2));
            customer.setEmail(cursor.getString(3));
            customer.setBirth(cursor.getInt(4));
            list.add(customer);
        }
        return list;
    }
}
