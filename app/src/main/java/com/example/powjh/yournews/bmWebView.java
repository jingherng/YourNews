package com.example.powjh.yournews;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ShareActionProvider;

import java.util.HashMap;

public class bmWebView extends Activity {

    public static final String TAG = "article";
    private ShareActionProvider shareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_view);

        SQLiteOpenHelper dbHelper = MainApp.bmDB;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("USER_BOOKMARKS",new String[]{"ARTICLES", "CAPTIONS", "IMAGEURL"},
                null,
                null, null, null, null);
        WebView webView = (WebView) findViewById(R.id.newsWebView);
        int articleNo = (Integer)getIntent().getExtras().get(TAG);
        int i = 0;
        while(cursor.moveToNext())
        {
            if (i == articleNo)
                webView.loadUrl(cursor.getString(cursor.getColumnIndex("ARTICLES")));
            i++;
        }
    }
}