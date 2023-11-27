package com.example.x.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.x.database.DbHelper;
import com.example.x.model.HardBill;
import com.example.x.model.Room;
import com.example.x.model.Service;
import com.example.x.model.TopRoom;
import com.example.x.model.TopService;
import com.example.x.model.Type;

import java.util.ArrayList;

public class StatisticsDAO {
    private SQLiteDatabase database;
    private Context context;
    RoomDAO roomDAO;
    TypeDAO typeDAO;
    HardBillDAO hardBillDAO;
    ServiceDAO serviceDAO;
    public StatisticsDAO(Context context){
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
        roomDAO = new RoomDAO(context);
        typeDAO = new TypeDAO(context);
        hardBillDAO = new HardBillDAO(context);
        serviceDAO = new ServiceDAO(context);
    }

    public ArrayList<TopRoom> getTopRoom(){
        String sql = "select idRoom,type.id, count(idRoom) as quantity from hardBill " +
                "inner join room on hardBill.idRoom = room.id " +
                "inner join type on room.idType = type.id " +
                "group by idRoom order by quantity desc limit 10";
        ArrayList<TopRoom> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql,null);
        while(cursor.moveToNext()){
            TopRoom top = new TopRoom();
            Room room = roomDAO.getId(cursor.getString(0));
            top.setNumberRoom(room.getNumber());
            Type type = typeDAO.getId(cursor.getString(1));
            top.setNameType(type.getName());
            top.setQuantity(cursor.getInt(2));
            list.add(top);
        }
        return list;
    }
    public ArrayList<TopService> getTopService(){
        String sql = "select idService, count(idService) as quantity from bill " +
                "inner join service on bill.idService = service.id " +
                "group by idService order by quantity desc limit 10";
        ArrayList<TopService> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql,null);
        while(cursor.moveToNext()){
            TopService top = new TopService();
            Service service = serviceDAO.getId(cursor.getString(0));
            top.setNameService(service.getName());
            top.setQuantityService(cursor.getInt(1));
            list.add(top);
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
