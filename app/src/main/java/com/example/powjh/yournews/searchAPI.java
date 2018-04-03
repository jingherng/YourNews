package com.example.powjh.yournews;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

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

class searchAPI extends AsyncTask<Void, Void, Boolean> {

    private static ArrayList<HashMap<String, String>> searchNewsList;

    Activity c;

    private String userQuery;

    public static ArrayList<HashMap<String,String>> retrieveNews(){
        return searchNewsList;
    }

    public searchAPI(Activity c){
        this.c = c;
    }

    public void setQuery(String s){
        this.userQuery = s;
    }

    // Do in background
    @Override
    protected Boolean doInBackground(Void... Void) {
        // Creating service handler class instance
        APIrequest webreq = new APIrequest();

        //HttpURLCon webreq = new HttpURLCon();

        // Make a function that adds all news sources in the format: bloomberg|bbc
        //String newsSources = "";
        // Insert function here
        //String newsSourceurl = newsSources.replaceAll("|","+OR+");

        // url for latest
        //String url = "https://www.reddit.com/r/worldnews/search.json?q="+userQuery+"+=url%3A"+newsSourceurl+"&restrict_sr=on&sort=relevance&t=hot&limit=5";
        String emptyurl = "https://www.reddit.com/r/worldnews/search.json?q="+userQuery+"&restrict_sr=on&sort=new&t=all";
        //String emptyurl = "https://newsapi.org/v2/everything?q="+userQuery+"&domains=bbc.co.uk,techcrunch.com,engadget.com,cnn.com,nytimes.com"+"&language=en&sortBy=popularity"+"&apiKey=f0da13ca99f44e9b9cc4d6ff7b4d4924";
        //String latestNewsStr = webreq.makeWebServiceCall(url, APIrequest.GETRequest);
        //String emptyLatestNewsStr = "";
        String emptyLatestNewsStr = webreq.makeWebServiceCall(emptyurl);
        Log.d("emptyLatestNewsStr: ", "> " + emptyLatestNewsStr);
        /*try{
                emptyLatestNewsStr = webreq.sendGet();
                }
        catch(Exception e){
        }*/


        /*if (newsSources.equals("")) {
            searchNewsList = ParseJSON(emptyLatestNewsStr);
        }else {
            searchNewsList = ParseJSON(latestNewsStr);
        }

        Log.d("Search Results: ", "> " + newsSources);
        Log.d("Search Results: ", "> " + latestNewsStr);*/
        searchNewsList = ParseJSON(emptyLatestNewsStr);

        return true;
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

                Log.d("Search Results: ", "> " + latestNewsList);


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

    @Override
    protected void onPostExecute(Boolean values){
        c.findViewById(R.id.searchProgress).setVisibility(View.GONE);
        Fragment fragment1 = new searchFrag();
        FragmentManager manager1 = c.getFragmentManager();
        FragmentTransaction transaction1 = manager1.beginTransaction();
        transaction1.replace(R.id.SearchResults, fragment1);
        transaction1.addToBackStack(null);
        transaction1.commitAllowingStateLoss();
    }
}