package com.example.powjh.yournews;

import android.util.Log;

public class NewsAPILatest implements NewsSite{
	
	public String url;
	String newsSources;
	APIrequest webreq;
	int page =2;
	
	public NewsAPILatest(String newsSources) {
		this.newsSources = newsSources;
		this.newsSources = formatString();
		this.webreq = new APIrequest();
		
		if (newsSources.equals("")){
		        	this.url = "https://newsapi.org/v2/top-headlines?page=1&language=en&apiKey=f0da13ca99f44e9b9cc4d6ff7b4d4924";
		        }
		        else
		        {
		        	this.url = "https://newsapi.org/v2/top-headlines?page=1&language=en&apiKey=f0da13ca99f44e9b9cc4d6ff7b4d4924&sources="+this.newsSources;
		        }	
	}
	
	private String formatString() {
		String newsSourceurl = newsSources.replaceAll("\\|",",");
		String returnString;
		returnString = newsSourceurl.replaceAll("BBC","bbc-news");
    	returnString = returnString.replaceAll("CNN","cnn");
    	returnString = returnString.replaceAll("WSJ","the-wall-street-journal");
    	returnString = returnString.replaceAll("RT","rt");
    	returnString = returnString.replaceAll("NYT","the-new-york-times");
    	//No source for Sky news
    	returnString = returnString.replaceAll("Sky","");
    	returnString = returnString.replaceAll("CBS","cbs-news");
    	//No source for BR
    	returnString = returnString.replaceAll("BR","");
    	returnString = returnString.replaceAll("ESPN","espn");
    	returnString = returnString.replaceAll("NBC","nbc-news");
    	Log.d("Check String >>",returnString);
		return returnString;
	}

	public String loadInitialData() {
		return webreq.makeWebServiceCall(this.url);
	}

	public String loadMoreData(String pageHolder) {
		this.url = "https://newsapi.org/v2/top-headlines?page="+ page + "&language=en&apiKey=f0da13ca99f44e9b9cc4d6ff7b4d4924&sources="+this.newsSources;
		page++;
		return webreq.makeWebServiceCall(this.url);
	};
	
}
