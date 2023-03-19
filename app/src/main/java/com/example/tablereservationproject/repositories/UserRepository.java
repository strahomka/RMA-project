package com.example.tablereservationproject.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.example.tablereservationproject.util.DatabaseHelper;
import com.example.tablereservationproject.model.User;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRepository {

    private DatabaseHelper database;
    private Context context;

    public UserRepository(DatabaseHelper db, Context context){this.database=db; this.context = context;}


    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean validate(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(User.TABLE_NAME, new String[] { User.FIELD_USER_ID, User.FIELD_USER_EMAIL, User.FIELD_USER_NAME, User.FIELD_USER_PASSWORD },
                "user_email=?", new String[] { email }, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }
    public void addUser(String user_email,String user_name, String user_password){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(User.FIELD_USER_EMAIL, user_email);
        cv.put(User.FIELD_USER_NAME, user_name);
        cv.put(User.FIELD_USER_PASSWORD, user_password);
        db.insert(User.TABLE_NAME, null, cv);
    }

    public void updateUser(int user_id, String user_email,String user_name, String user_password){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(User.FIELD_USER_EMAIL, user_email);
        cv.put(User.FIELD_USER_NAME, user_name);
        cv.put(User.FIELD_USER_PASSWORD, user_password);
        db.update(User.TABLE_NAME, cv, User.FIELD_USER_ID + "=?", new String[] {String.valueOf(user_id)});
    }

    public void deleteReservation(int user_id){
        SQLiteDatabase db = database.getWritableDatabase();
        db.delete(User.TABLE_NAME, User.FIELD_USER_ID + "=?", new String[] {String.valueOf(user_id)});
    }

    public User getUserById(int user_id){
        SQLiteDatabase db = database.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ?;", User.TABLE_NAME, User.FIELD_USER_ID);
        Cursor result = db.rawQuery(query, new String[] {String.valueOf(user_id)});
        if(result.moveToFirst()){
            String email = result.getString(result.getColumnIndex(User.FIELD_USER_EMAIL));
            String name = result.getString(result.getColumnIndex(User.FIELD_USER_NAME));
            String password = result.getString(result.getColumnIndex(User.FIELD_USER_PASSWORD));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return new User(email,name, password);
            }
            return null;
        }else {
            return null;
        }
    }

    public List<User> getAllUsers(){
        SQLiteDatabase db = database.getReadableDatabase();
        String query = String.format("SELECT * FROM %s;", User.TABLE_NAME);
        Cursor result = db.rawQuery(query, null);
        result.moveToNext();
        List<User> users = new ArrayList<>(result.getCount());
        while(!result.isAfterLast()){
            String email = result.getString(result.getColumnIndex(User.FIELD_USER_EMAIL));
            String name = result.getString(result.getColumnIndex(User.FIELD_USER_NAME));
            String password = result.getString(result.getColumnIndex(User.FIELD_USER_PASSWORD));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    users.add(new User(email,name, password));
                }catch (DateTimeParseException e){
                    return users;
                }
            }
            result.moveToNext();
        }
        return users;
    }
}
