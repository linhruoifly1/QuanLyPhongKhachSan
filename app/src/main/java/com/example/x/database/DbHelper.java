package com.example.x.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
public static final String dbName ="dataX";

    public DbHelper(@Nullable Context context) {
        super(context, dbName, null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbType = "CREATE TABLE type (\n" +
                "    id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name TEXT    NOT NULL,\n" +
                "    status INTEGER    NOT NULL\n" +
                ");";
        db.execSQL(tbType);
        String dataType = "insert into type values " +
                "(1,'Phòng đơn',0)," +
                "(2,'Phòng đôi',0)," +
                "(3,'Phòng hạng sang',0)";
        db.execSQL(dataType);
        String tbRoom = "CREATE TABLE room (\n" +
                "    id     INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    idType INTEGER REFERENCES type (id) \n" +
                "                   NOT NULL,\n" +
                "    number INTEGER NOT NULL,\n" +
                "    status INTEGER NOT NULL,\n" +
                "    price  INTEGER NOT NULL\n" +
                ");";
        db.execSQL(tbRoom);
        String dataRoom = "insert into room values " +
                "(1,1,101,0,100),(2,2,102,0,150),(3,2,103,0,150),(4,3,104,0,300)," +
                "(5,1,201,0,100),(6,2,202,0,150),(7,2,203,0,150),(8,3,204,0,300)," +
                "(9,1,301,0,100),(10,2,302,0,150),(11,2,303,0,150),(12,3,304,0,300)," +
                "(13,1,401,0,100),(14,2,402,0,150),(15,2,403,0,150),(16,3,404,0,300)," +
                "(17,1,501,0,100),(18,2,502,0,150),(19,2,503,0,150),(20,3,504,0,300)," +
                "(21,1,601,0,100),(22,2,602,0,150),(23,2,603,0,150),(24,3,604,0,300)";
        db.execSQL(dataRoom);
        String tbCustomer = "CREATE TABLE customer (\n" +
                "    id    INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name  TEXT    NOT NULL,\n" +
                "    phone TEXT    NOT NULL,\n" +
                "    email TEXT,\n" +
                "    birth INTEGER NOT NULL\n" +
                ");";
        db.execSQL(tbCustomer);
        String tbReceptionist = "CREATE TABLE receptionist (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name     TEXT    NOT NULL,\n" +
                "    email    TEXT,\n" +
                "    username TEXT    NOT NULL\n" +
                "                     UNIQUE,\n" +
                "    password TEXT    NOT NULL\n" +
                ");";
        db.execSQL(tbReceptionist);
        String dataReceptionist ="insert into receptionist values (1,'Nguyễn Ngọc Linh','linhruoifly1@gmail.com','admin','admin')";
        db.execSQL(dataReceptionist);
        String tbService = "CREATE TABLE service (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name     TEXT    NOT NULL,\n" +
                "    price    INTEGER NOT NULL,\n" +
                "    status INTEGER    NOT NULL\n" +

                ");";
        db.execSQL(tbService);
        String dataService = "insert into service values " +
                "(1,'Nhìn ra hồ',20,0)," +
                "(2,'Sắp xếp cuộc họp',100,1)," +
                "(3,'Trang trí cho cặp đôi',50,1)," +
                "(4,'Có thể nấu ăn',30,0)";
        db.execSQL(dataService);
        String tbBill = "CREATE TABLE bill (\n" +
                "    id             INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    idCustomer       INTEGER REFERENCES customer (id) \n" +
                "                           NOT NULL,\n" +
                "    idReceptionist INTEGER REFERENCES receptionist (id) \n" +
                "                           NOT NULL,\n" +
                "    idService      INTEGER REFERENCES service (id),\n" +
                "    checkIn        TEXT,\n" +
                "    checkOut       TEXT,\n" +
                "    date       TEXT,\n" +
                "    realCheckOut   TEXT,\n" +
                "    costRoom       INTEGER NOT NULL,\n" +
                "    costService       INTEGER,\n" +
                "    VAT            INTEGER NOT NULL,\n" +
                "    status         INTEGER NOT NULL,\n" +
                "    sumCost        INTEGER NOT NULL\n" +
                ");";
        String tbHardBill = "CREATE TABLE hardBill (\n" +
                "    id             INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    idBill         INTEGER REFERENCES bill (id),\n" +
                "    idRoom         INTEGER REFERENCES room (id),\n" +
                "    quantityPeople INTEGER NOT NULL\n" +
                ");";
        db.execSQL(tbHardBill);
        db.execSQL(tbBill);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists type");
        db.execSQL("drop table if exists room");
        db.execSQL("drop table if exists customer");
        db.execSQL("drop table if exists receptionist");
        db.execSQL("drop table if exists service");
        db.execSQL("drop table if exists bill");
        db.execSQL("drop table if exists hardBill");
        onCreate(db);
    }
}
