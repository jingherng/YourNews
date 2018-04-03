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

class headlinesAPI extends AsyncTask<Void, Integer, Boolean>{

    private static ArrayList<HashMap<String, String>> newsList;

    Activity c;
    public static String newsSources = "";

    public static ArrayList<HashMap<String,String>> retrieveNews(){
        return newsList;
    }

    public headlinesAPI(Activity c){
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
        String emptyurl = "https://www.reddit.com/r/worldnews/hot/.json";
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
        c.findViewById(R.id.progress).setVisibility(View.GONE);
        Fragment fragment1 = new headlinesFrag();
        FragmentManager manager1 = c.getFragmentManager();
        FragmentTransaction transaction1 = manager1.beginTransaction();
        transaction1.add(R.id.LatestHeadlines_box, fragment1);
        transaction1.commitAllowingStateLoss();
    }

    private ArrayList<HashMap<String, String>> ParseJSON(String latestNewsStr) {
        if (latestNewsStr != null) {
            try {
                //Hashmap for latest news articles
                ArrayList<HashMap<String, String>> latestNewsList = new ArrayList<HashMap<String, String>>();

                JSONObject jsonObj = new JSONObject(latestNewsStr);

                // Getting JSON object from key "data"
                JSONObject newsData = jsonObj.getJSONObject("data");
                // Get the integer from "dist" key
                int numObject = newsData.getInt("dist");
                // Get the Array from "children" key
                JSONArray newsItem = newsData.getJSONArray("children");

                    for (int i = 0; i < 5; i++) {
                        JSONObject oneNewsItem = (newsItem.getJSONObject(i)).getJSONObject("data");

                        //Getting the title
                        String title = oneNewsItem.getString("title");
                        //Getting the url
                        String url = oneNewsItem.getString("url");

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

            }catch(IOException| JSONException e){
                e.printStackTrace();
                return null;
            }
        } else{
            Log.e("ServiceHandler", "No data received from HTTP Request");
            return null;
        }
    }
}