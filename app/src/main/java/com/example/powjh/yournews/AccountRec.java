package com.example.powjh.yournews;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AccountRec extends Activity {

    private ContentValues bool;
    private SQLiteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_rec);
        dbHelper = new UserInfoDB(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        bool = new ContentValues();
        Cursor cursor = db.query("USER_INFO",
                new String[]{"NAME","BBC", "CNN", "WSJ", "RT", "NYT", "Sky", "CBS", "BR", "ESPN", "NBC"},
                "_id = ?",
                new String[]{Integer.toString(1)}, null, null, null);
        if (cursor.moveToFirst()){
            TextView userInfo = findViewById(R.id.userName);
            userInfo.setText(cursor.getString(0));
        }
        for (int i = 1; i < 10; i++){
            if (cursor.moveToFirst()){
                switch (i){
                    case 1: if (cursor.getString(i).equals("true")){
                        ToggleButton on = (ToggleButton) findViewById(R.id.BBC);
                        on.setChecked(true);
                    } else {
                        ToggleButton on = (ToggleButton) findViewById(R.id.BBC);
                        on.setChecked(false);
                    } break;
                    case 2: if (cursor.getString(i).equals("true")){
                        ToggleButton on = (ToggleButton) findViewById(R.id.CNN);
                        on.setChecked(true);
                    } else {
                        ToggleButton on = (ToggleButton) findViewById(R.id.CNN);
                        on.setChecked(false);
                    } break;
                    case 3: if (cursor.getString(i).equals("true")){
                        ToggleButton on = (ToggleButton) findViewById(R.id.WSJ);
                        on.setChecked(true);
                    } else {
                        ToggleButton on = (ToggleButton) findViewById(R.id.WSJ);
                        on.setChecked(false);
                    } break;
                    case 4: if (cursor.getString(i).equals("true")){
                        ToggleButton on = (ToggleButton) findViewById(R.id.RT);
                        on.setChecked(true);
                    } else {
                        ToggleButton on = (ToggleButton) findViewById(R.id.RT);
                        on.setChecked(false);
                    } break;
                    case 5: if (cursor.getString(i).equals("true")){
                        ToggleButton on = (ToggleButton) findViewById(R.id.NYT);
                        on.setChecked(true);
                    } else {
                        ToggleButton on = (ToggleButton) findViewById(R.id.NYT);
                        on.setChecked(false);
                    } break;
                    case 6: if (cursor.getString(i).equals("true")){
                        ToggleButton on = (ToggleButton) findViewById(R.id.Sky);
                        on.setChecked(true);
                    } else {
                        ToggleButton on = (ToggleButton) findViewById(R.id.Sky);
                        on.setChecked(false);
                    } break;
                    case 7: if (cursor.getString(i).equals("true")){
                        ToggleButton on = (ToggleButton) findViewById(R.id.CBS);
                        on.setChecked(true);
                    } else {
                        ToggleButton on = (ToggleButton) findViewById(R.id.CBS);
                        on.setChecked(false);
                    } break;
                    case 8: if (cursor.getString(i).equals("true")){
                        ToggleButton on = (ToggleButton) findViewById(R.id.BR);
                        on.setChecked(true);
                    } else {
                        ToggleButton on = (ToggleButton) findViewById(R.id.BR);
                        on.setChecked(false);
                    } break;
                    case 9: if (cursor.getString(i).equals("true")){
                        ToggleButton on = (ToggleButton) findViewById(R.id.ESPN);
                        on.setChecked(true);
                    } else {
                        ToggleButton on = (ToggleButton) findViewById(R.id.ESPN);
                        on.setChecked(false);
                    } break;
                    case 10: if (cursor.getString(i).equals("true")){
                        ToggleButton on = (ToggleButton) findViewById(R.id.NBC);
                        on.setChecked(true);
                    } else {
                        ToggleButton on = (ToggleButton) findViewById(R.id.NBC);
                        on.setChecked(false);
                    } break;
                }

            }
        }
    }

    public void setString(){
        // String builder for newsSourcesURL
        try{
            StringBuilder stringBuilder = new StringBuilder();
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query("USER_INFO",
                    new String[]{"NAME","BBC", "CNN", "WSJ", "RT", "NYT", "Sky", "CBS", "BR", "ESPN", "NBC"},
                    "_id = ?",
                    new String[]{Integer.toString(1)}, null, null, null);

            for (int i = 1; i < 10; i++) {
                if (cursor.moveToFirst()) {
                    switch (i) {
                        case 1:
                            if (cursor.getString(i).equals("true")) {
                                if (stringBuilder.equals(""))
                                    stringBuilder.append("BBC");
                                else
                                    stringBuilder.append("|BBC");
                            }
                            break;
                        case 2:
                            if (cursor.getString(i).equals("true")) {
                                if (stringBuilder.equals(""))
                                    stringBuilder.append("CNN");
                                else
                                    stringBuilder.append("|CNN");
                            }
                            break;
                        case 3:
                            if (cursor.getString(i).equals("true")) {
                                if (stringBuilder.equals(""))
                                    stringBuilder.append("WSJ");
                                else
                                    stringBuilder.append("|WSJ");
                            }
                            break;
                        case 4:
                            if (cursor.getString(i).equals("true")) {
                                if (stringBuilder.equals(""))
                                    stringBuilder.append("RT");
                                else
                                    stringBuilder.append("|RT");
                            }
                            break;
                        case 5:
                            if (cursor.getString(i).equals("true")) {
                                if (stringBuilder.equals(""))
                                    stringBuilder.append("NYT");
                                else
                                    stringBuilder.append("|NYT");
                            }
                            break;
                        case 6:
                            if (cursor.getString(i).equals("true")) {
                                if (stringBuilder.equals(""))
                                    stringBuilder.append("Sky");
                                else
                                    stringBuilder.append("|Sky");
                            }
                            break;
                        case 7:
                            if (cursor.getString(i).equals("true")) {
                                if (stringBuilder.equals(""))
                                    stringBuilder.append("CBS");
                                else
                                    stringBuilder.append("|CBS");
                            }
                            break;
                        case 8:
                            if (cursor.getString(i).equals("true")) {
                                if (stringBuilder.equals(""))
                                    stringBuilder.append("BR");
                                else
                                    stringBuilder.append("|BR");
                            }
                            break;
                        case 9:
                            if (cursor.getString(i).equals("true")) {
                                if (stringBuilder.equals(""))
                                    stringBuilder.append("ESPN");
                                else
                                    stringBuilder.append("|ESPN");
                            }
                            break;
                        case 10:
                            if (cursor.getString(i).equals("true")) {
                                if (stringBuilder.equals(""))
                                    stringBuilder.append("NBC");
                                else
                                    stringBuilder.append("|NBC");
                            }
                            break;
                    }
                }
            }
            String finalString = stringBuilder.toString();
            if (finalString.length()>0) {
                finalString = finalString.substring(1);
            }
            Log.d("This is my source: ",finalString);
            headlinesAPI.newsSources = finalString;
            latestNewsAPI.newsSources = finalString;
            searchAPI.newsSources = finalString;


        }catch(SQLException e){
            Toast toast = Toast.makeText(this, "Sources not updated", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void callHomePage(View view){

        // If-statement to check whether any toggle has been activated and to update DB if true
        if (bool.containsKey("BBC")|bool.containsKey("RT")|bool.containsKey("CNN")|
        bool.containsKey("WSJ")|bool.containsKey("NYT")|bool.containsKey("Sky")|bool.containsKey("CBS")
        |bool.containsKey("CBS")|bool.containsKey("BR")|bool.containsKey("ESPN")|
                bool.containsKey("NBC")) {
            try {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.update("USER_INFO", bool, "_id = ? ", new String[]{Integer.toString(1)});

            } catch (SQLException e) {
                Toast toast = Toast.makeText(this, "Database not modified", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        setString();
        MainApp.headlines = new headlinesAPI(MainApp.headlines.getActivity());
        MainApp.headlines.execute();
        finish();
    }

    public void checkBBC(View view){
        //Update database when BBC is checked
        ToggleButton on = (ToggleButton) findViewById(R.id.BBC);
        if (on.isChecked())
            bool.put("BBC", "true");
        else
            bool.put("BBC", "false");
    }

    public void checkCNN(View view){
        //Update database when BBC is checked
        ToggleButton on = (ToggleButton) findViewById(R.id.CNN);
        if (on.isChecked())
            bool.put("CNN", "true");
        else
            bool.put("CNN", "false");
    }

    public void checkWSJ(View view){
        //Update database when BBC is checked
        ToggleButton on = (ToggleButton) findViewById(R.id.WSJ);
        if (on.isChecked())
            bool.put("WSJ", "true");
        else
            bool.put("WSJ", "false");
    }

    public void checkRT(View view){
        //Update database when BBC is checked
        ToggleButton on = (ToggleButton) findViewById(R.id.RT);
        if (on.isChecked())
            bool.put("RT", "true");
        else
            bool.put("RT", "false");
    }

    public void checkNYT(View view){
        //Update database when BBC is checked
        ToggleButton on = (ToggleButton) findViewById(R.id.NYT);
        if (on.isChecked())
            bool.put("NYT", "true");
        else
            bool.put("NYT", "false");
    }

    public void checkESPN(View view){
        //Update database when BBC is checked
        ToggleButton on = (ToggleButton) findViewById(R.id.ESPN);
        if (on.isChecked())
            bool.put("ESPN", "true");
        else
            bool.put("ESPN", "false");
    }

    public void checkBR(View view){
        //Update database when BBC is checked
        ToggleButton on = (ToggleButton) findViewById(R.id.BR);
        if (on.isChecked())
            bool.put("BR", "true");
        else
            bool.put("BR", "false");
    }

    public void checkCBS(View view){
        //Update database when BBC is checked
        ToggleButton on = (ToggleButton) findViewById(R.id.CBS);
        if (on.isChecked())
            bool.put("CBS", "true");
        else
            bool.put("CBS", "false");
    }

    public void checkSky(View view){
        //Update database when BBC is checked
        ToggleButton on = (ToggleButton) findViewById(R.id.Sky);
        if (on.isChecked())
            bool.put("Sky", "true");
        else
            bool.put("Sky", "false");
    }

    public void checkNBC(View view){
        //Update database when BBC is checked
        ToggleButton on = (ToggleButton) findViewById(R.id.NBC);
        if (on.isChecked())
            bool.put("NBC", "true");
        else
            bool.put("NBC", "false");
    }
}