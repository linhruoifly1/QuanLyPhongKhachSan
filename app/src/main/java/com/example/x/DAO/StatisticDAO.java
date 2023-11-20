package com.example.x.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.x.database.DbHelper;
import com.example.x.model.TopService;

import java.util.ArrayList;

public class StatisticDAO {

    DbHelper database;


//    public int getStatistical(String startDay, String endDay){
//        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(sumCost) FROM bill WHERE ")
//    }

    public StatisticDAO(Context context){
        database = new DbHelper(context);
    }

    public ArrayList<TopService> getService(){
        ArrayList<TopService> list = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Sv.name, COUNT(billl.idService) FROM bill billl, service Sv WHERE billl.idService = Sv.id GROUP BY billl.idService , Sv.name ORDER BY COUNT(billl.idService) DESC LIMIT 10",null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                TopService topService = new TopService();
                topService.setTenDichVu(cursor.getString(0));
                topService.setSoLuong(cursor.getInt(1));
                list.add(topService);

                cursor.moveToNext();
            }
        }
        return list;
    }
}
