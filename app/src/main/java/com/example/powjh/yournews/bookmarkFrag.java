package com.example.powjh.yournews;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

public class bookmarkFrag extends Fragment{

    ArrayList<String> captions;
    ArrayList<String> imageURL;
    String[] url;
    private Context C;
    private static SwipeController swipeController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        C = getContext();

        SQLiteOpenHelper dbHelper = MainApp.bmDB;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("USER_BOOKMARKS",new String[]{"ARTICLES", "CAPTIONS", "IMAGEURL"},
                null,
                null, null, null, null);
        int i = 0;
        url = new String[cursor.getCount()];
        captions = new ArrayList<String>();
        imageURL = new ArrayList<String>();
        Log.d("Cursor Count: ", "> " + cursor.getCount());
        while (cursor.moveToNext()){
            Log.d("Cursor getString(0): ", "> " + cursor.getString(cursor.getColumnIndex("ARTICLES")));
            Log.d("Cursor getString(1): ", "> " + cursor.getString(cursor.getColumnIndex("CAPTIONS")));
            Log.d("Cursor getString(2): ", "> " + cursor.getString(cursor.getColumnIndex("IMAGEURL")));
            url[i] = cursor.getString(cursor.getColumnIndex("ARTICLES"));
            captions.add(cursor.getString(cursor.getColumnIndex("CAPTIONS")).replaceAll("''","'"));
            imageURL.add(cursor.getString(cursor.getColumnIndex("IMAGEURL")));
            i++;
        }
        cursor.close();

        newsAdapter adapter = new newsAdapter(captions, imageURL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView newsRecycler = (RecyclerView) inflater.inflate(R.layout.recyclerview, container, false);
        newsRecycler.setLayoutManager(layoutManager);
        newsRecycler.setAdapter(adapter);
        adapter.setListener(new newsAdapter.Listener(){
            public void onClick(int position){
                Intent intent = new Intent(getActivity(), bmWebView.class);
                intent.putExtra(bmWebView.TAG, position);
                getActivity().startActivity(intent);
            }
        });

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                // Remove bookmark from article

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
