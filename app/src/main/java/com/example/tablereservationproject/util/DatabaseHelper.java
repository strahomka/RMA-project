package com.example.tablereservationproject.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tablereservationproject.model.Reservation;
import com.example.tablereservationproject.model.Table;
import com.example.tablereservationproject.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "projekat.db";

    public DatabaseHelper(Context context, int version) { super(context, DATABASE_NAME, null, version);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT);",
                Table.TABLE_NAME, Table.FIELD_TABLE_ID, Table.FIELD_TABLE_NAME));

        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT,%s TEXT, %s TEXT);",
                User.TABLE_NAME, User.FIELD_USER_ID, User.FIELD_USER_EMAIL,User.FIELD_USER_NAME, User.FIELD_USER_PASSWORD));

        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s INT,%s INT,%s DATE,%s TIME, %s INT);",
                Reservation.TABLE_NAME, Reservation.FIELD_RESERVATION_ID, Reservation.FIELD_RESERVATION_TABLE_ID,Reservation.FIELD_RESERVATION_USER_ID, Reservation.FIELD_RESERVATION_DATE, Reservation.FIELD_RESERVATION_TIME,
                Reservation.FIELD_RESERVATION_NUM_GUESTS));

        db.execSQL(String.format("INSERT INTO %s (%s) VALUES ('Table 1');", Table.TABLE_NAME, Table.FIELD_TABLE_NAME));
        db.execSQL(String.format("INSERT INTO %s (%s) VALUES ('Table 2');", Table.TABLE_NAME, Table.FIELD_TABLE_NAME));
        db.execSQL(String.format("INSERT INTO %s (%s) VALUES ('Table 3');", Table.TABLE_NAME, Table.FIELD_TABLE_NAME));
        db.execSQL(String.format("INSERT INTO %s (%s) VALUES ('Table 4');", Table.TABLE_NAME, Table.FIELD_TABLE_NAME));
        db.execSQL(String.format("INSERT INTO %s (%s) VALUES ('Table 5');", Table.TABLE_NAME, Table.FIELD_TABLE_NAME));
        db.execSQL(String.format("INSERT INTO %s (%s) VALUES ('Table 6');", Table.TABLE_NAME, Table.FIELD_TABLE_NAME));
        db.execSQL(String.format("INSERT INTO %s (%s) VALUES ('Table 7');", Table.TABLE_NAME, Table.FIELD_TABLE_NAME));
        db.execSQL(String.format("INSERT INTO %s (%s) VALUES ('Table 8');", Table.TABLE_NAME, Table.FIELD_TABLE_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s;", Table.TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s;", Reservation.TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s;", User.TABLE_NAME));
        onCreate(db);
    }
}
