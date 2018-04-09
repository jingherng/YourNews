package com.example.powjh.yournews;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class searchAPI extends AsyncTask<Boolean, Void, Boolean> implements NewsIterator{

    private static ArrayList<HashMap<String, String>> searchNewsList;
    private NewsFactory newsFactory;

    Activity c;

    private String userQuery;
    private NewsSite redditSearch;
    private JSONparser jsonManager;
    public static String newsSources = "";
    private String loadNewsStr;

    public static ArrayList<HashMap<String,String>> retrieveNews(){
        return searchNewsList;
    }

    public searchAPI(Activity c){
        this.c = c;
        newsFactory = new NewsFactory();
        jsonManager = JSONparser.getInstance();
    }

    public void setQuery(String s){
        this.userQuery = s;
        if (redditSearch != null) {
        	((RedditSearch) redditSearch).setUserQuery(s);
        }
        else {
        	redditSearch = newsFactory.makeNewsSite("search",newsSources,userQuery);
        }
    }

    // Do in background
    @Override
    protected Boolean doInBackground(Boolean... values) {
        // Creating service handler class instance
    	
        redditSearch = newsFactory.makeNewsSite("search",newsSources,userQuery);

        loadNewsStr = redditSearch.loadInitialData();
        Log.d("Hello, ",loadNewsStr);
        Log.d("NewsSource, ",newsSources);
        searchNewsList = jsonManager.parseJSON(JSONparser.REDDIT, loadNewsStr);
        if (searchNewsList==null){
            return false;
        }
        else if (searchNewsList.size()>0){
            return true;
        }
        else
            return false;
    }
    
    private String getLatestName() {
    	HashMap<String, String> item = searchNewsList.get(searchNewsList.size()-1);
    	return item.get("name");
    }

    // Search for more news
    public Boolean moreNews(){
        if (searchNewsList==null || searchNewsList.size()<1)
            return false;
        loadNewsStr = redditSearch.loadMoreData(getLatestName());
        searchNewsList = jsonManager.parseJSON(JSONparser.REDDIT, loadNewsStr);
        if (searchNewsList!=null && searchNewsList.size()>0)
            return true;
        return false;
    }

    @Override
    protected void onPostExecute(Boolean values){
        if(true) {
            c.findViewById(R.id.notFound).setVisibility(View.GONE);
            c.findViewById(R.id.searchProgress).setVisibility(View.GONE);
            Fragment fragment1 = new searchFrag();
            FragmentManager manager1 = c.getFragmentManager();
            FragmentTransaction transaction1 = manager1.beginTransaction();
            transaction1.replace(R.id.SearchResults, fragment1);
            transaction1.addToBackStack(null);
            transaction1.commitAllowingStateLoss();
        }
        else {
            c.findViewById(R.id.SearchResultsFrag).setVisibility(View.GONE);
            c.findViewById(R.id.searchProgress).setVisibility(View.GONE);
            c.findViewById(R.id.notFound).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPreExecute(){
        c.findViewById(R.id.searchProgress).setVisibility(View.VISIBLE);
        c.findViewById(R.id.notFound).setVisibility(View.GONE);
        c.findViewById(R.id.SearchResultsFrag).setVisibility(View.GONE);
    }

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