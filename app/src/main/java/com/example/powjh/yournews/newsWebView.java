package com.example.powjh.yournews;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ShareActionProvider;

import java.util.HashMap;

public class newsWebView extends Activity {

    public static final String TAG = "article";
    private ShareActionProvider shareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_view);
        WebView webView = (WebView) findViewById(R.id.newsWebView);
        int articleNo = (Integer)getIntent().getExtras().get(TAG);
        int i = 0;
        for (HashMap<String, String> item: MainApp.headlines.retrieveNews()){
            if (i == articleNo)
                webView.loadUrl(item.get("url"));
            i++;
        }
    }
}