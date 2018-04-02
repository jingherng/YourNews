package com.example.powjh.yournews;

import android.database.sqlite.*;
import android.content.Context;

class BookmarksDB extends SQLiteOpenHelper{

    private static final String DB_NAME = "Bookmarks";
    private static final int DB_VERSION = 1;

    BookmarksDB (Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE USER_BOOKMARKS " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "CAPTIONS TEXT, "
                + "IMAGEURL TEXT, "
                + "ARTICLES TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){

    }

}