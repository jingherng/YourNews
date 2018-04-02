package com.example.powjh.yournews;

import android.content.ContentValues;
import android.database.sqlite.*;
import android.content.Context;

class UserInfoDB extends SQLiteOpenHelper{

    private static final String DB_NAME = "YourNews";
    private static final int DB_VERSION = 1;

    UserInfoDB(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE USER_INFO (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "BBC TEXT,"
                + "CNN TEXT, "
                + "WSJ TEXT, "
                + "RT TEXT, "
                + "NYT TEXT, "
                + "Sky TEXT, "
                + "CBS TEXT, "
                + "BR TEXT, "
                + "ESPN TEXT, "
                + "NBC TEXT);");

        set(db, "admin", "false");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){

    }

    private static void set(SQLiteDatabase db, String name, String on ){
        ContentValues srcValues = new ContentValues();
        srcValues.put("NAME", name);
        srcValues.put("BBC" , on);
        srcValues.put("CNN" , on);
        srcValues.put("WSJ" , on);
        srcValues.put("RT" , on);
        srcValues.put("NYT" , on);
        srcValues.put("Sky" , on);
        srcValues.put("CBS" , on);
        srcValues.put("BR" , on);
        srcValues.put("ESPN" , on);
        srcValues.put("NBC" , on);
        db.insert("USER_INFO", null, srcValues);
    }
}