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

class latestNewsAPI extends AsyncTask<Void, Integer, Boolean>{

    private static ArrayList<HashMap<String, String>> newsList;

    Activity c;
    public static String newsSources = "";

    public static ArrayList<HashMap<String,String>> retrieveNews(){
        return newsList;
    }

    public latestNewsAPI(Activity c){
        this.c = c;
    }
    // Do in background
    @Override
    protected Boolean doInBackground(Void... Void) {
        // Creating service handler class instance
        APIrequest webreq = new APIrequest();

        // Make a function that adds all news sources in the format: bloomberg|bbc
        // Insert function here
        //String newsSourceurl = newsSources.replaceAll("|","+OR+");

        // url for latest
        //String url = "https://www.reddit.com/r/worldnews/search?q=url%3A"+newsSourceurl+"&restrict_sr=on&sort=hot&t=all";
        String emptyurl = "https://newsapi.org/v2/top-headlines?page=1&language=en&apiKey=f0da13ca99f44e9b9cc4d6ff7b4d4924";
        //String latestNewsStr = webreq.makeWebServiceCall(url, APIrequest.GETRequest);
        String emptyLatestNewsStr = webreq.makeWebServiceCall(emptyurl);

        /*if (newsSources.equals("")) {
            newsList = ParseJSON(emptyLatestNewsStr);
        }else {
            newsList = ParseJSON(latestNewsStr);
        }*/
        newsList = ParseJSON(emptyLatestNewsStr);
        return true;
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

    private ArrayList<HashMap<String, String>> ParseJSON(String latestNewsStr) {
        if (latestNewsStr != null) {
            try {
                //Hashmap for latest news articles
                ArrayList<HashMap<String, String>> latestNewsList = new ArrayList<HashMap<String, String>>();

                JSONObject jsonObj = new JSONObject(latestNewsStr);

                // Getting JSON object from key "data"
                JSONArray newsItem = jsonObj.getJSONArray("articles");
                // Testing 5 objects runtime
                int numObject = 5;

                for (int i = 0; i < 5; i++) {
                    JSONObject oneNewsItem = newsItem.getJSONObject(i);

                    //Getting the title
                    String title = oneNewsItem.getString("title");
                    //Getting the url
                    String url = oneNewsItem.getString("url");
                    String pictureLink = oneNewsItem.getString("urlToImage");
                    String descriptionLink = oneNewsItem.getString("description");
                    String timeLink = oneNewsItem.getString("publishedAt").substring(0,10);

                    /*
                    String pictureLink ="", descriptionLink="", timeLink="";

                    // Using JSoup
                    Document doc = Jsoup.connect(url).get();
                    // Scrapping for website URL
                    Elements picture= doc.getElementsByAttributeValue("property","og:image");
                    for (Element link : picture) {
                        pictureLink = picture.attr("content");
                        break;
                    }

                    // Scrapping for website description
                    Elements description= doc.getElementsByAttributeValue("property","og:description");
                    for (Element link : description) {
                        descriptionLink = description.attr("content");
                    }
                    // Scrapping for published time
                    Elements time= doc.getElementsByAttributeValue("property","article:published_time");
                    for(Element link: time){
                        timeLink = time.attr("content");
                    }
                    */

                    HashMap<String, String> newsdict = new HashMap<String, String>();

                    //formatterOut.format(dateFormatted)
                    newsdict.put("title", title);
                    newsdict.put("url", url);
                    newsdict.put("imageurl", pictureLink);
                    newsdict.put("description", descriptionLink);
                    newsdict.put("time", timeLink);

                    latestNewsList.add(newsdict);

                }
                Log.d("Response Code: ", "> " + latestNewsList);
                Log.d("Response Code: ", "> " + newsSources);
                return latestNewsList;

            }catch( JSONException e){
                e.printStackTrace();
                return null;
            }
        } else{
            Log.e("ServiceHandler", "No data received from HTTP Request");
            return null;
        }
    }
}