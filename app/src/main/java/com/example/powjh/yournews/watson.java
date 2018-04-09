package com.example.powjh.yournews;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;

import java.util.ArrayList;
import java.util.List;

public class watson {
    //Gradle: compile 'com.ibm.watson.developer_cloud:java-sdk:5.1.1'
    private String input;
    private int number;
    private NaturalLanguageUnderstanding service;

    public int get(){
        return this.number;
    }

    public watson() {
        this.number=3;
        this.service = new NaturalLanguageUnderstanding(
                "2018-03-16",
                "14cfe0c5-e36f-49e8-924d-040e1ceb1fbc",
                "yOjlKNwRoAgu"
        );
        SQLiteDatabase sqldb = MainApp.watsonDB.getReadableDatabase();
        String table = "USER_KEYS";
        String[] columns = {"KEYS"};
        //String selection = "column3 =?";
        //String[] selectionArgs = {"apple"};
        String groupBy = null;
        String having = null;
        String orderBy = "_id DESC";
        String limit = "25";
        Cursor cursor = sqldb.query(table, columns, null, null, groupBy, having, orderBy, limit);
        while (cursor.moveToNext()){
            //Log.d("Cursor: ", cursor.getString(cursor.getColumnIndex("KEYS")));
            this.input += cursor.getString(cursor.getColumnIndex("KEYS"));
        }
        if (this.input!=null)
            Log.d("Watson String input: ", this.input);
        else
            Log.d("Watson String input: ", "null");
    }

    public ArrayList<String> getQueryKeyword(){

        ArrayList<String> resultList = new ArrayList<String>();

        KeywordsOptions keywordsOptions = new KeywordsOptions.Builder()
                .emotion(false)
                .sentiment(false)
                .limit(this.number)
                .build();

        Features features = new Features.Builder()
                .keywords(keywordsOptions)
                .build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                .text(input)
                .features(features)
                .build();

        AnalysisResults response = service
                .analyze(parameters)
                .execute();

        List<KeywordsResult> keywordResults = response.getKeywords();
        for (KeywordsResult keyword : keywordResults) {
            Log.d("Watson Keyword: ", keyword.getText().replaceAll("null","").replaceAll(" ","+"));
            resultList.add(keyword.getText().replaceAll("null","").replaceAll(" ","+"));
        }
        return resultList;
    }
}