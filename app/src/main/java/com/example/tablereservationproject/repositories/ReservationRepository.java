package com.example.tablereservationproject.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.widget.Toast;

import com.example.tablereservationproject.util.DatabaseHelper;
import com.example.tablereservationproject.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {

    private DatabaseHelper database;
    private Context context;

    public ReservationRepository(DatabaseHelper db, Context context){this.database = db; this.context = context;}

    public void addReservation(int table_id,int user_id, LocalDate date, LocalTime time, int num_guests){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String query = "SELECT * FROM reservations WHERE reservation_table_id = " + table_id + " AND reservation_date = '" + date + "' AND reservation_time = '" + time + "'";
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            Toast.makeText(this.context, "A reservation already exists for this table on this date and time, please change one of those", Toast.LENGTH_SHORT).show();
        }else{
            cv.put(Reservation.FIELD_RESERVATION_TABLE_ID, table_id);
            cv.put(Reservation.FIELD_RESERVATION_USER_ID, user_id);
            cv.put(Reservation.FIELD_RESERVATION_DATE, String.valueOf(date));
            cv.put(Reservation.FIELD_RESERVATION_TIME, String.valueOf(time));
            cv.put(Reservation.FIELD_RESERVATION_NUM_GUESTS, num_guests);
            db.insert(Reservation.TABLE_NAME, null, cv);

        }

        cursor.close();
    }

    public void updateReservation(int reservation_id, int table_id, int user_id, LocalDate date,LocalTime time, int num_guests){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String query = "SELECT * FROM reservations WHERE reservation_table_id = " + table_id + " AND reservation_date = '" + date + "' AND reservation_time = '" + time + "'";
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            Toast.makeText(this.context, "A reservation already exists for this table on this date and time, please change one of those", Toast.LENGTH_SHORT).show();
        }else {
            cv.put(Reservation.FIELD_RESERVATION_TABLE_ID, table_id);
            cv.put(Reservation.FIELD_RESERVATION_USER_ID, user_id);
            cv.put(Reservation.FIELD_RESERVATION_DATE, String.valueOf(date));
            cv.put(Reservation.FIELD_RESERVATION_TIME, String.valueOf(time));
            cv.put(Reservation.FIELD_RESERVATION_NUM_GUESTS, num_guests);
            db.update(Reservation.TABLE_NAME, cv, Reservation.FIELD_RESERVATION_ID + "=?", new String[]{String.valueOf(reservation_id)});
        }
    }

    public void deleteReservation(int reservation_id){
        SQLiteDatabase db = database.getWritableDatabase();
        db.delete(Reservation.TABLE_NAME, Reservation.FIELD_RESERVATION_ID + "=?", new String[] {String.valueOf(reservation_id)});
    }

    public Reservation getReservationById(int reservation_id_arg){
        SQLiteDatabase db = database.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ?;", Reservation.TABLE_NAME, Reservation.FIELD_RESERVATION_ID);
        Cursor result = db.rawQuery(query, new String[] {String.valueOf(reservation_id_arg)});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(result.moveToFirst()){
                int reservation_id = result.getInt(result.getColumnIndex(Reservation.FIELD_RESERVATION_ID));
                int table_id = result.getInt(result.getColumnIndex(Reservation.FIELD_RESERVATION_TABLE_ID));
                int user_id = result.getInt(result.getColumnIndex(Reservation.FIELD_RESERVATION_USER_ID));
                String date = result.getString(result.getColumnIndex(Reservation.FIELD_RESERVATION_DATE));
                String time = result.getString(result.getColumnIndex(Reservation.FIELD_RESERVATION_TIME));
                int num_of_guests = result.getInt(result.getColumnIndex(Reservation.FIELD_RESERVATION_NUM_GUESTS));

                return new Reservation(reservation_id, table_id,user_id,LocalDate.parse(date),LocalTime.parse(time),num_of_guests);
                }
            }else {
                return null;
            }
        return null;
    }

    public List<Reservation> getAllReservations(){
        SQLiteDatabase db = database.getReadableDatabase();
        String query = String.format("SELECT * FROM %s;", Reservation.TABLE_NAME);
        Cursor result = db.rawQuery(query, null);
        result.moveToNext();
        List<Reservation> reservations = new ArrayList<>(result.getCount());
        while(!result.isAfterLast()){
            int reservation_id = result.getInt(result.getColumnIndex(Reservation.FIELD_RESERVATION_ID));
            int table_id = result.getInt(result.getColumnIndex(Reservation.FIELD_RESERVATION_TABLE_ID));
            int user_id = result.getInt(result.getColumnIndex(Reservation.FIELD_RESERVATION_USER_ID));
            String date = result.getString(result.getColumnIndex(Reservation.FIELD_RESERVATION_DATE));
            String time = result.getString(result.getColumnIndex(Reservation.FIELD_RESERVATION_TIME));
            int num_of_guests = result.getInt(result.getColumnIndex(Reservation.FIELD_RESERVATION_NUM_GUESTS));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    reservations.add(new Reservation(reservation_id, table_id,user_id, LocalDate.parse(date), LocalTime.parse(time), num_of_guests));
                }catch (DateTimeParseException e){
                    return reservations;
                }
            }
            result.moveToNext();
        }
        return reservations;
    }

    public List<Reservation> getAllReservationsFromUser(){
        SQLiteDatabase db = database.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ?;", Reservation.TABLE_NAME, Reservation.FIELD_RESERVATION_USER_ID);
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("userIdPrefs", Context.MODE_PRIVATE);
        String user_id_arg = sharedPreferences.getString("userId", "");
        Cursor result = db.rawQuery(query, new String[] {user_id_arg});
        result.moveToNext();
        List<Reservation> reservations = new ArrayList<>(result.getCount());
        while(!result.isAfterLast()){
            int reservation_id = result.getInt(result.getColumnIndex(Reservation.FIELD_RESERVATION_ID));
            int table_id = result.getInt(result.getColumnIndex(Reservation.FIELD_RESERVATION_TABLE_ID));
            int user_id = result.getInt(result.getColumnIndex(Reservation.FIELD_RESERVATION_USER_ID));
            String date = result.getString(result.getColumnIndex(Reservation.FIELD_RESERVATION_DATE));
            String time = result.getString(result.getColumnIndex(Reservation.FIELD_RESERVATION_TIME));
            int num_of_guests = result.getInt(result.getColumnIndex(Reservation.FIELD_RESERVATION_NUM_GUESTS));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    reservations.add(new Reservation(reservation_id,table_id,user_id, LocalDate.parse(date), LocalTime.parse(time), num_of_guests));
                }catch (DateTimeParseException e){
                    return reservations;
                }
            }
            result.moveToNext();
        }
        return reservations;
    }
}
