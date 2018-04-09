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
    String loadNewsStr;
    NewsSite latestNews;

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
        latestNews = newsFactory.makeNewsSite("latest",newsSources,"");
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

    public Boolean moreNews(){
        if (newsList==null || newsList.size()<1)
            return false;
        loadNewsStr = latestNews.loadMoreData("");
        newsList.addAll(jsonManager.parseJSON(JSONparser.NEWSAPI, loadNewsStr));
        if (newsList!=null && newsList.size()>0)
            return true;
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
    public static Iterator createStaticIterator() {
        return retrieveNews().iterator();
    }
    public static int getIteratorSize(){
        Iterator myIterator = createStaticIterator();
        int i = 0;
        while(myIterator.hasNext()) {
            i++;
            myIterator.next();
        }
        return i;
    }

    public void runMoreLatest(){
        moreLatest more = new moreLatest();
        more.execute();
    }

    class moreLatest extends AsyncTask<Void, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(Void...Void){
            return moreNews();
        }

        @Override
        protected void onPostExecute(Boolean values){
            c.findViewById(R.id.LatestNewsprogress).setVisibility(View.GONE);
            Fragment bookmarksFrag = new latestNewsFrag();
            FragmentManager manager = c.getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.replace(R.id.latestNewsFrag, bookmarksFrag);
            transaction.commitAllowingStateLoss();
        }
    }
}