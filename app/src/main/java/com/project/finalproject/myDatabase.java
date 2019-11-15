package com.project.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class myDatabase extends SQLiteOpenHelper {
    private final String TAG = "Database";
    private static final int version = 2;
    private static final String db_name = "Data_nguoidung";
    private static final String table_name = "User";
    private static final String ID = "id";
    private static final String Username = "username";
    private static final String Password = "password";
    private static final String PhoneNumber = "phone";
    private static final String Name = "name";
    private static final String Address = "address";
    private static final String Gender = "gender";
    private static final String Birthdate = "birthdate";

    private String SQLQuery = "CREATE TABLE " + table_name + "("
            + ID + " integer primary key AUTOINCREMENT, "
            + Username + " Text, "
            + Password + " Text, "
            + Name + " Text, "
            + Address + " Text, "
            + PhoneNumber + " Text, "
            + Gender + " Text, "
            + Birthdate + " Date)";

    public myDatabase(Context context)
    {
         super(context,db_name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG,"onUpgrade: ");
    }

    public void addUser(User myUser)
    {
       // Date birth = myUser.getmBirthdate();
       // String birthday  = birth + "";
        ContentValues content = new ContentValues();
        content.put(Username,myUser.getmUsername());
        content.put(Password,myUser.getmPassword());
        content.put(Name,myUser.getmName());
        content.put(Address,myUser.getmAddress());
        content.put(PhoneNumber,myUser.getmPhone());
        content.put(Gender,myUser.getmGender());
        //content.put(Birthdate,birthday);
        SQLiteDatabase db =this.getWritableDatabase();
        db.insert(table_name,null,content);
        db.close();
    }
    public boolean checkAccount(String username,String password){

        String Query = "SELECT * FROM " + table_name;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery(Query,null);
        if (cursor.moveToFirst())
        {
            do{
                if (cursor.getString(1).equals(username) && cursor.getString(2).equals(password))
                {
                    return true;
                }
            }while (cursor.moveToNext());
        }
        db.close();
        return false;
    }
    public int checkUserSignUp(String username,String password)
    {
        String query = "SELECT username FROM " + table_name + " WHERE username = \"" + username + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery(query,null);
        if (cursor.getCount() > 0) {
            return 0;
        }
        if (username.length()<6)
            return 2;
        if (password.length()<6)
            return 3;
        return 1;
    }
}
