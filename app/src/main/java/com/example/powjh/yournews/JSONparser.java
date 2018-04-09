package com.example.powjh.yournews;
//This is a singleton design principle

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONparser {
	private static JSONparser jsonManager = null;
	static boolean firstThread = true;
	public static final int REDDIT = 1;
	public static final int NEWSAPI = 2;

	//Prevent users from instantiating the class,
	//Only JSONparser can instantiate this class
	private JSONparser() {
	}

	public static JSONparser getInstance() {
		if (jsonManager == null) {
			if (firstThread) {
				firstThread = false;
			}
		}
		synchronized (JSONparser.class) {
			if (jsonManager == null) {
				jsonManager = new JSONparser();
			}
		}
		return jsonManager;
	}

	public ArrayList<HashMap<String, String>> parseJSON(int newsSite, String json) {
		switch (newsSite) {
			case 1:
				if (json != null) {
					try {
						//Hashmap for latest news articles
						ArrayList<HashMap<String, String>> redditNewsList = new ArrayList<HashMap<String, String>>();

						JSONObject jsonObj = new JSONObject(json);

						// Getting JSON object from key "data"
						JSONObject newsData = jsonObj.getJSONObject("data");
						// Get the integer from "dist" key
						int numObject = newsData.getInt("dist");
						// Get the Array from "children" key
						JSONArray newsItem = newsData.getJSONArray("children");
						ArrayList <String> urls = new ArrayList<String>();

						for (int i = 0; i < numObject; i++) {
							JSONObject oneNewsItem = (newsItem.getJSONObject(i)).getJSONObject("data");
							// Using JSoup
							try {
								//Getting the title
								String title = oneNewsItem.getString("title");
								//Getting the url
								String url = oneNewsItem.getString("url");

								String name = oneNewsItem.getString("name");

								String pictureLink = "";// descriptionLink="", timeLink="";
								Document doc = Jsoup.connect(url).get();
								Elements picture = doc.getElementsByAttributeValue("property", "og:image");
								for (Element link : picture) {
									pictureLink = picture.attr("content");
									break;
								}
								if (urls.contains(url)){}
								else{
								HashMap<String, String> newsdict = new HashMap<String, String>();

								//formatterOut.format(dateFormatted)
								newsdict.put("title", title);
								newsdict.put("url", url);
								newsdict.put("imageurl", pictureLink);
								newsdict.put("name", name);
	                        	urls.add(title);
	                        	redditNewsList.add(newsdict);
								}
							} catch (JSONException | IOException e) {
								//Does Nothing
							}
						}

						Log.d("Search Results: ", "> " + redditNewsList);

						return redditNewsList;
					} catch (JSONException e) {
						e.printStackTrace();
						return null;
					}
				} else {
					Log.e("ServiceHandler", "No data received from HTTP Request");
					return null;
				}
			case 2:
				if (json != null) {
					try {
						//Hashmap for latest news articles
						ArrayList<HashMap<String, String>> newsAPINewsList = new ArrayList<HashMap<String, String>>();
						ArrayList<String> titles = new ArrayList<String>();
						JSONObject jsonObj = new JSONObject(json);

						// Getting JSON object from key "data"
						JSONArray newsItem = jsonObj.getJSONArray("articles");

						// Testing 5 objects runtime
						int numObject = 5;
						if (newsItem == null || newsItem.length() < 5) {
							return null;
						}

						for (int i = 0; i < 5; i++) {
							JSONObject oneNewsItem = newsItem.getJSONObject(i);

							//Getting the title
							String title = oneNewsItem.getString("title");
							//Getting the url
							String url = oneNewsItem.getString("url");
							String pictureLink = oneNewsItem.getString("urlToImage");
							String descriptionLink = oneNewsItem.getString("description");
							String timeLink = oneNewsItem.getString("publishedAt").substring(0, 10);


							if (titles.contains(url)) {
							} else {
								HashMap<String, String> latestnewsdict = new HashMap<String, String>();

								//formatterOut.format(dateFormatted)
								latestnewsdict.put("title", title);
								latestnewsdict.put("url", url);
								latestnewsdict.put("imageurl", pictureLink);
								latestnewsdict.put("description", descriptionLink);
								latestnewsdict.put("time", timeLink);
								titles.add(url);
								newsAPINewsList.add(latestnewsdict);
							}

						}
						Log.d("Search Results: ", "> " + newsAPINewsList);
						return newsAPINewsList;
					}catch (JSONException e) {
						e.printStackTrace();
						return null;
					}
				} else {
					Log.e("ServiceHandler", "No data received from HTTP Request");
					return null;
				}
		}

		return null;
	}
}