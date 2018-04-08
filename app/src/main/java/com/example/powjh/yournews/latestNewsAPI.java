package com.example.powjh.yournews;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class latestNewsAPI extends AsyncTask<Void, Integer, Boolean> implements NewsIterator{

    private static ArrayList<HashMap<String, String>> newsList;
    private NewsFactory newsFactory = new NewsFactory();
    private JSONparser jsonManager;

    Activity c;
    public static String newsSources = "";

    public static ArrayList<HashMap<String,String>> retrieveNews(){
        return newsList;
    }

    public latestNewsAPI(Activity c){
        this.c = c;
        jsonManager = JSONparser.getInstance();
    }
    // Do in background
    @Override
    protected Boolean doInBackground(Void... Void) {
        // Creating service handler class instance
        NewsSite latestNews = newsFactory.makeNewsSite("latest",newsSources,"");
        String loadNewsStr;
        loadNewsStr = latestNews.loadInitialData();
        newsList = jsonManager.parseJSON(JSONparser.NEWSAPI, loadNewsStr);
        if (newsList==null){
            return false;
        }
        else if (newsList.size()>0){
            return true;
        }
        else
            return false;
    }
   
    
    @Override
    protected void onPostExecute(Boolean values){
        c.findViewById(R.id.LatestNewsprogress).setVisibility(View.GONE);
        Fragment bookmarksFrag = new latestNewsFrag();
        FragmentManager manager = c.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.latestNewsFrag, bookmarksFrag);
        transaction.commitAllowingStateLoss();
    }

	@Override
	public Iterator createIterator() {
		return retrieveNews().iterator();
	}

}