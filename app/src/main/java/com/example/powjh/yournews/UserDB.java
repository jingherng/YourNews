package com.example.powjh.yournews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.database.sqlite.*;
import android.content.Context;

class UserDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "UserDB";
    private static final int DB_VERSION = 1;

    UserDB(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE USER_DB (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "USERNAME TEXT, "
                + "PASSWORD TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
    }

    public boolean CheckIsDataAlreadyInDBorNot(String TableName, String dbfield, String fieldValue) {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + dbfield + "='" + fieldValue+"'";
        Cursor cursor = sqldb.rawQuery(Query, null);
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
}