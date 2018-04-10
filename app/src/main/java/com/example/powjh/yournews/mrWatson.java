package com.example.powjh.yournews;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class mrWatson extends AsyncTask<Void, Void, Boolean> implements NewsIterator{

    private static ArrayList<HashMap<String, String>> watsonNewsList = new ArrayList<HashMap<String, String>>();
    

    Activity c;

    private String userQuery;

    private facadeWatson watson;
    private NewsSite redditSearch;
    private NewsFactory newsFactory;
    
    public static ArrayList<HashMap<String,String>> retrieveNews(){
        return watsonNewsList;
    }

    public mrWatson(Activity c){
        this.c = c;
    }
    
    private String getLatestName() {
    	HashMap<String, String> item = watsonNewsList.get(watsonNewsList.size()-1);
    	return item.get("name");
    }
    
    public void setQuery(String s){
        this.userQuery = s;
        redditSearch = newsFactory.makeNewsSite("search","",userQuery);
    }
    // Do in background
    @Override
    protected Boolean doInBackground(Void... Void) {
        // Creating service handler class instance
        watson = new facadeWatson();
        watsonNewsList = watson.getResults();
        return true;
    }


    @Override
    protected void onPostExecute(Boolean values){
        c.findViewById(R.id.AIrecommendsProgressBar).setVisibility(View.GONE);
        Fragment fragment1 = new watsonFrag();
        FragmentManager manager1 = c.getFragmentManager();
        FragmentTransaction transaction1 = manager1.beginTransaction();
        transaction1.replace(R.id.AIRecFrag, fragment1);
        transaction1.addToBackStack(null);
        transaction1.commitAllowingStateLoss();
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
}