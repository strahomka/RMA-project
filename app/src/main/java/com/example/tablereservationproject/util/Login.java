package com.example.tablereservationproject.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.tablereservationproject.model.User;


public class Login {

    public static boolean loggedIn = false;
    private int checker = 0;
    private Context context;


    public Login(Context context) {
        this.context = context;
    }

    public void logout(){
        loggedIn = false;
    }
    public User checkLogin(String email, String password) {
        Context appContext = context.getApplicationContext();
        DatabaseHelper database = new DatabaseHelper(appContext, 10);
        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT user_id, user_email,user_name, user_password FROM " + User.TABLE_NAME +
                " WHERE user_email=? AND user_password=?", new String[] {email, password});

        if (cursor.moveToFirst()) {
            String userEmail = cursor.getString(cursor.getColumnIndex(User.FIELD_USER_EMAIL));
            String name = cursor.getString(cursor.getColumnIndex(User.FIELD_USER_NAME));
            String userPassword = cursor.getString(cursor.getColumnIndex(User.FIELD_USER_PASSWORD));
            cursor.close();
            User user1 = new User(userEmail, name, userPassword);
            SharedPreferences sharedPreferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userEmail", userEmail);
            editor.putString("userName", name);
            editor.putString("userPassword", userPassword);
            editor.apply();
//            System.out.println(user1);
            loggedIn = true;
            return user1;
        } else {
            cursor.close();
            Toast.makeText(context, "Incorrect credentials, please check and try again", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public void storeUserId(String userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userIdPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", userId);
        editor.apply();
    }
}