package com.example.powjh.yournews;

public class NewsFactory {
	public NewsSite makeNewsSite(String typeOfNewsSite, String newsSources,String userQuery) {
		
		if(typeOfNewsSite.equals("search")) {
			return new RedditSearch(newsSources,userQuery);
		}
		else if (typeOfNewsSite.equals("hot")) {
			return new RedditHot(newsSources);
		}
		else if (typeOfNewsSite.equals("latest")) {
			return new NewsAPILatest(newsSources);
		}
		return null;
	}
}
