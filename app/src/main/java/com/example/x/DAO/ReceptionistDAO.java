package com.example.x.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Patterns;

import com.example.x.database.DbHelper;
import com.example.x.model.Receptionist;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ReceptionistDAO {

    SharedPreferences sharedPreferences;
    private final DbHelper dbHelper;
    public ReceptionistDAO(Context context){
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("receptionist", Context.MODE_PRIVATE);
    }
    public Receptionist getUsername(String username){
        String sql = "select * from receptionist where username=?";
        ArrayList<Receptionist> list = getData(sql,username);
        if (list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    public Receptionist getId(String id){
        String sql = "select * from receptionist where id=?";
        ArrayList<Receptionist> list = getData(sql,id);
        return list.get(0);
    }
    public ArrayList<Receptionist> getAll(){
        String sql = "select * from receptionist";
        return getData(sql);
    }
    private ArrayList<Receptionist> getData(String sql,String...selectionArgs){
        ArrayList<Receptionist> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            Receptionist receptionist=new Receptionist();
            receptionist.setId(cursor.getInt(0));
            receptionist.setName(cursor.getString(1));
            receptionist.setEmail(cursor.getString(2));
            receptionist.setUsername(cursor.getString(3));
            receptionist.setPassword(cursor.getString(4));
            list.add(receptionist);
        }
        return list;
    }
    public long insert(Receptionist receptionist){
        ContentValues values = new ContentValues();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        values.put("name",receptionist.getName());
        values.put("email",receptionist.getEmail());
        values.put("username",receptionist.getUsername());
        values.put("password",receptionist.getPassword());
        return database.insert("receptionist",null,values);
    }

    public long insertImage(Receptionist receptionist){
       ContentValues contentValues = new ContentValues();
       SQLiteDatabase database = dbHelper.getWritableDatabase();
       contentValues.put("avatar",receptionist.getAvatar().toString());
       return  database.insert("receptionist",null,contentValues);

    }
    public boolean checkLogin(String username,String password){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM receptionist WHERE username = ? AND password = ?",new String[]{username,password});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("id", cursor.getString(0));
            editor.putString("name", cursor.getString(1));
            editor.putString("email",cursor.getString(2));
            editor.putString("username", cursor.getString(3));
            editor.putString("password", cursor.getString(4));
            editor.commit();
            return true;
        }
//        Cursor cursor = database.rawQuery("select * from receptionist where username=? and password=?",new String[]{username,password});
//        int row = cursor.getCount();
        return false;
    }

    public int changePassword(String username,String pass, String newPass){
        ContentValues values = new ContentValues();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM receptionist WHERE username = ? AND password = ?",new String[]{username, pass});
        if (cursor.getCount() > 0){
            values.put("password",newPass);
            long check = database.update("receptionist",values,"username = ?",new String[]{username});
            if (check == -1){
                return -1; //thất bại
            }
            return 1; // đổi mật khẩu thành công
        }
        return 0; // đổi thất bại do mật khẩu cũ sai
    }

    public boolean checkUser(String username){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM receptionist WHERE username = ?", new String[]{username});
        int cout = 0;
        if (cursor.getCount() > 0){
            if (cursor.moveToFirst()){
                cout = cursor.getInt(0);
            }

        }
        return cout>0;
    }
    public int updateProfile(Receptionist receptionist){
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        contentValues.put("name",receptionist.getName());
        contentValues.put("email",receptionist.getEmail());

        return database.update("receptionist",contentValues,"id=?",new String[]{String.valueOf(receptionist.getId())});

    }
}
