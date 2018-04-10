package com.example.powjh.yournews;

import android.util.Log;

public class RedditHot implements NewsSite{
	
	public String url;
	String newsSources;
	APIrequest webreq;
	
	public RedditHot(String newsSources) {
		this.webreq = new APIrequest();
		this.newsSources = newsSources;
		this.newsSources = formatString();
		
		if (newsSources.equals("")){
		        	this.url = "https://www.reddit.com/r/worldnews/hot/.json?limit=5";
		        }
		        else
		        {
		        	this.url = "https://www.reddit.com/r/worldnews/search.json?q=url%3A"+this.newsSources+"&restrict_sr=on&limit=5&sort=hot";
		        }
		Log.d("Reddit Hot",this.url);
	}
	
	private String formatString() {
		String newsSourceurl = newsSources.replaceAll("\\|","+OR+");
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
