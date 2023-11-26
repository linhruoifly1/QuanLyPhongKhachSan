package com.example.x.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.x.database.DbHelper;
import com.example.x.model.TopService;

import java.util.ArrayList;

public class StatisticsDAO {
    private SQLiteDatabase database;
    private Context context;
    public StatisticsDAO(Context context){
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public ArrayList<TopService> getService(){
        ArrayList<TopService> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Sv.name, COUNT(billl.idService) FROM bill billl, service Sv WHERE billl.idService = Sv.id GROUP BY billl.idService , Sv.name ORDER BY COUNT(billl.idService) DESC LIMIT 10",null);
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
    public int getRevenue(String startDate,String endDate){
        String sqlRevenue = "select sum(sumCost) as revenue from bill where checkIn between ? and ? and status=1";
        ArrayList<Integer> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sqlRevenue, new String[]{startDate,endDate});
        while (cursor.moveToNext()){
            try{
                list.add(cursor.getInt(0));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }
}
