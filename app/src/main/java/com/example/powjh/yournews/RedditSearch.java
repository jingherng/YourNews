package com.example.powjh.yournews;

import android.util.Log;

public class RedditSearch implements NewsSite{
	
	public String url;
	public String userQuery;
	String newsSources;
	APIrequest webreq;

	public RedditSearch(String newsSources, String userQuery) {
		this.newsSources = newsSources;
		this.newsSources = formatString();
		this.userQuery = userQuery;
		this.webreq = new APIrequest();
		
		if (newsSources.equals(""))
			this.url = "https://www.reddit.com/r/worldnews/search.json?q="+userQuery+"&restrict_sr=on&sort=new&t=all&limit=5";
		else
		   	this.url = "https://www.reddit.com/r/worldnews/search.json?q="+userQuery+"+url%3A"+this.newsSources+"&restrict_sr=on&sort=new&t=all&limit=5";
		Log.d("URL:", this.url);
	}
	
	public void setUserQuery(String newQuery) {
		this.userQuery = newQuery;}
	
	private String formatString() {
		Log.d("Before",newsSources);
		String newsSourceurl = newsSources.replaceAll("\\|","+OR+");
		Log.d("After",newsSourceurl);
		return newsSourceurl;
	}

	public String loadInitialData() {
		return webreq.makeWebServiceCall(this.url);
	}

	public String loadMoreData(String latestName) {
		this.url = this.url+"&after="+latestName;
		return webreq.makeWebServiceCall(this.url);
	};
	
}
