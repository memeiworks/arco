package com.example.samplejava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDBHelper extends SQLiteOpenHelper {

    //Database Name and Table Name

    public static final String databasename = "userAccounts.db";
    public static final String userModel = "users";

    //User Model Columns

    public static final String USER_COL1_ID = "id";
    public static final String USER_COL2_NAME = "fullname";
    public static final String USER_COL3_EMAIL = "email";
    public static final String USER_COL4_PHONE = "phoneno";
    public static final String USER_COL5_PASS = "password";

    //Database

    private SQLiteDatabase mWriteableDb;

    public UserDBHelper(Context context) {
        super(context, databasename, null,4);
        mWriteableDb = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //CREATE TABLE

        db.execSQL("CREATE TABLE "+userModel+" ( " +
                "" +USER_COL1_ID+" integer primary key autoincrement,"
                +USER_COL2_NAME+" text, "
                +USER_COL3_EMAIL+" text unique, "
                +USER_COL4_PHONE+" integer, "
                +USER_COL5_PASS+" text);");

        Log.e("TABLE OPERATIONS: ","User Table created.");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists "+userModel);
        onCreate(db);
        Log.e("TABLE OPERATIONS: ","Dropped existing tables.");

        //Default User

        db.execSQL("insert into users(id,fullname,email,phoneno,password) values " +
                "(1,'Juan DelaCruz','juandelacruz@gmail.com',091234567890,'delacruznoyPI')");
    }

    //REGISTER USER VIA EMAIL

    public boolean signup_user(String fullname, String email, String phoneno, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL2_NAME, fullname);
        contentValues.put(USER_COL3_EMAIL, email);
        contentValues.put(USER_COL4_PHONE, phoneno);
        contentValues.put(USER_COL5_PASS, password);

        Log.e("TABLE OPERATIONS: ", "Registered one user.");
        long result = db.insert(userModel, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //CURSORS

    public Cursor emailValidation(String valemail, SQLiteDatabase db) {
        String query = "select * from users where email ='"+valemail+"'";
        Cursor emailcursor = db.rawQuery(query,null);
        return emailcursor;
    }
}
