package com.example.tablereservationproject.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tablereservationproject.util.DatabaseHelper;
import com.example.tablereservationproject.model.Table;

import java.util.ArrayList;
import java.util.List;

public class TableRepository {
    private DatabaseHelper database;

    public TableRepository(DatabaseHelper db){this.database=db;}

    public void addTable(String name){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Table.FIELD_TABLE_NAME, name);
        db.insert(Table.TABLE_NAME, null, cv);
    }

    public Table getTableById(int tableId){
        SQLiteDatabase db = database.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ?;", Table.TABLE_NAME, Table.FIELD_TABLE_ID);
        Cursor result = db.rawQuery(query, new String[] {String.valueOf(tableId)});
        if(result.moveToFirst()){
            String name = result.getString(result.getColumnIndex(Table.FIELD_TABLE_NAME));
            return new Table(tableId, name);
        }else {
            return null;
        }
    }

    public List<Table> getAllTables(){
        SQLiteDatabase db = database.getReadableDatabase();
        String query = String.format("SELECT * FROM %s;", Table.TABLE_NAME);
        Cursor result = db.rawQuery(query, null);
        result.moveToNext();
        List<Table> tables = new ArrayList<>(result.getCount());
        while(!result.isAfterLast()){
            int tableId = result.getInt(result.getColumnIndex(Table.FIELD_TABLE_ID));
            String tableName = result.getString(result.getColumnIndex(Table.FIELD_TABLE_NAME));

            tables.add(new Table(tableId, tableName));
            result.moveToNext();
        }
        return tables;
    }
}
