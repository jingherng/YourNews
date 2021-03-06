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

class headlinesAPI extends AsyncTask<Void, Integer, Boolean> implements NewsIterator{

    private static ArrayList<HashMap<String, String>> newsList;
    private NewsFactory newsFactory = new NewsFactory();
    String loadNewsStr;
    private NewsSite redditHot;
    private JSONparser jsonManager;
    headlinesFrag fragment1;
    FragmentManager manager1;
    FragmentTransaction transaction1;

    Activity c;
    public static String newsSources = "";

    public static ArrayList<HashMap<String,String>> retrieveNews(){
        return newsList;
    }

    public Activity getActivity(){
        return this.c;
    }

    public headlinesAPI(Activity c){
        this.c = c;
        jsonManager = JSONparser.getInstance();
        redditHot = newsFactory.makeNewsSite("hot",newsSources,"");
    }
    // Do in background
    @Override
    protected Boolean doInBackground(Void... Void) {
        // Creating service handler class instance
            loadNewsStr = redditHot.loadInitialData();
            Log.d("Problem here",loadNewsStr);
            newsList = jsonManager.parseJSON(JSONparser.REDDIT, loadNewsStr);
            if (newsList == null) {
                return false;
            } else if (newsList.size() > 0) {
                return true;
            } else
                return false;
    }
 
    private String getLatestName() {
        HashMap<String, String> item = newsList.get(newsList.size() - 1);
        return item.get("name");
    }

    public Boolean moreNews(){
        if (newsList==null || newsList.size()<1)
            return false;
        loadNewsStr = redditHot.loadMoreData(getLatestName());
        newsList.addAll(jsonManager.parseJSON(JSONparser.REDDIT, loadNewsStr));
        if (newsList!=null && newsList.size()>0)
            return true;
        return false;
    }

    @Override
    protected void onPostExecute(Boolean values){
        c.findViewById(R.id.progress).setVisibility(View.GONE);
        c.findViewById(R.id.LatestHeadlines_box).setVisibility(View.VISIBLE);
        fragment1 = new headlinesFrag();
        manager1 = c.getFragmentManager();
        transaction1 = manager1.beginTransaction();
        transaction1.replace(R.id.LatestHeadlines_box, fragment1);
        transaction1.commitAllowingStateLoss();
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        c.findViewById(R.id.progress).setVisibility(View.VISIBLE);
        c.findViewById(R.id.progress).bringToFront();
    }

	@Override
    public Iterator createIterator() {
        return retrieveNews().iterator();
    }

    public int getIteratorSize(){
        Iterator myIterator = createIterator();
        int i = 0;
        while(myIterator.hasNext()) {
            i++;
            myIterator.next();
        }
        return i;
    }

    public void runMoreHeadLines(){
        moreHeadlines more = new moreHeadlines();
        more.execute();
    }

    class moreHeadlines extends AsyncTask<Void, Integer, Boolean>{

        @Override
        protected void onPreExecute(){
            c.findViewById(R.id.BottomheadProgress).setVisibility(View.VISIBLE);
            c.findViewById(R.id.BottomheadProgress).bringToFront();
        }

        @Override
        protected Boolean doInBackground(Void...Void){
            return moreNews();
        }

        @Override
        protected void onPostExecute(Boolean values){
            c.findViewById(R.id.BottomheadProgress).setVisibility(View.GONE);
            fragment1.refresh();
        }
    }
}