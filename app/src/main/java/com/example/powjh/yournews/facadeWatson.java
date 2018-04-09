package com.example.powjh.yournews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class facadeWatson {
	
    private static ArrayList<HashMap<String, String>> watsonNewsList = new ArrayList<HashMap<String, String>>();
    private static ArrayList<HashMap<String, String>> tempWatsonNewsList;
    private Set<HashMap<String,String>> watsonFinalList = new HashSet<>();
    private watson watson;
    private NewsSite redditSearch;
    private NewsFactory newsFactory;
    private JSONparser jsonManager;
    
    public facadeWatson(String userQuery) {
    	this.watson = new watson();
        newsFactory = new NewsFactory();
        redditSearch = newsFactory.makeNewsSite("search","",userQuery);
        jsonManager = JSONparser.getInstance();
    }
    
    public ArrayList<HashMap<String,String>> getResults() {
		ArrayList<String> resultList = watson.getQueryKeyword();
        
        for (String result : resultList) {
            String loadNewsStr;
            l
            oadNewsStr = redditSearch.loadInitialData();
            tempWatsonNewsList = jsonManager.parseJSON(JSONparser.REDDIT, loadNewsStr);
            watsonFinalList.addAll(tempWatsonNewsList);
        }

        watsonNewsList.addAll(watsonFinalList);
        return watsonNewsList;
	}
}
