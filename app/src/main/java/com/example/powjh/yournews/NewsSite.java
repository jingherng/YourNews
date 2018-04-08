package com.example.powjh.yournews;

public interface NewsSite {
	public String loadInitialData();
	public String loadMoreData(String latestName);
}
