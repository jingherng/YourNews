package com.example.powjh.yournews;

import android.database.sqlite.*;
import android.content.Context;

class KeywordsDB extends SQLiteOpenHelper{

    private static final String DB_NAME = "Keywords";
    private static final int DB_VERSION = 1;

    KeywordsDB(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE USER_KEYS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "KEYWORDS TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){

    }

}