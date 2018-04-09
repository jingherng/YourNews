package com.example.powjh.yournews;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Canvas;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.*;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class searchFrag extends Fragment{

    String[] captions;
    String[] imageURL;
    private Iterator myIterator;
    private Context C;
    private static SwipeController swipeController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        C = getContext();
        ArrayList<String> captions = new ArrayList<String>();
        ArrayList<String> imageURL = new ArrayList<String>();

        int i = 0;
        myIterator = searchAPI.createStaticIterator();
        while(myIterator.hasNext())
        {
            HashMap<String,String> item = (HashMap<String, String>) (myIterator.next());
            captions.add(item.get("title"));
            imageURL.add(item.get("imageurl"));
            i++;
        }

        newsAdapter adapter = new newsAdapter(captions, imageURL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView newsRecycler = (RecyclerView) inflater.inflate(R.layout.recyclerview, container, false);
        newsRecycler.setLayoutManager(layoutManager);
        newsRecycler.setAdapter(adapter);
        adapter.setListener(new newsAdapter.Listener() {
            public void onClick(int position) {
                // Add article caption to watson DB
                ContentValues caption = new ContentValues();
                int i = 0;
                for (HashMap<String, String> item : searchAPI.retrieveNews()) {
                    if (i == position) {
                        caption.put("KEYS", item.get("title"));
                    }
                    i++;
                }
                SQLiteOpenHelper dbHelper = MainApp.watsonDB;
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.insert("USER_KEYS", null, caption);
                db.close();

                Intent intent = new Intent(getActivity(), searchWebView.class);
                intent.putExtra(searchWebView.TAG, position);
                getActivity().startActivity(intent);
            }
        });

        /*// For endless Scrolling
        newsRecycler.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemCounts, RecyclerView newsRecycler) {

            }
            
        });*/


        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                ContentValues articles = new ContentValues();

                int i = 0;
                for (HashMap<String, String> item: searchAPI.retrieveNews()){
                    if (i == position){
                        articles.put("ARTICLES" , item.get("url"));
                        articles.put("CAPTIONS", item.get("title").replaceAll("'","''"));
                        articles.put("IMAGEURL", item.get("imageurl"));
                    }
                    i++;
                }
                Log.d("Bookmarked > ", articles.toString());
                SQLiteOpenHelper dbHelper = MainApp.bmDB;
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if (!MainApp.bmDB.CheckIsDataAlreadyInDBorNot("USER_BOOKMARKS","CAPTIONS",articles.getAsString("CAPTIONS"))){
                    db.insert("USER_BOOKMARKS", null, articles);
                    Log.d("Bookmark", ": Success");
                } else {
                    Log.d("Bookmark", ": Unsuccessful. Bookmark already exists");
                }
                db.close();
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(newsRecycler);

        newsRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        return newsRecycler;
    }
}
