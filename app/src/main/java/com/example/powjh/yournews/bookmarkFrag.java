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
import java.util.HashMap;

public class bookmarkFrag extends Fragment{

    String[] captions;
    String[] imageURL;
    private BookmarksDB bm;
    private Context C;
    private static SwipeController swipeController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        C = getContext();
        bm = new BookmarksDB(C);

        SQLiteOpenHelper dbHelper = bm;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("USER_BOOKMARKS",new String[]{"ARTICLES", "CAPTIONS", "IMAGEURL"},
                "_id = ?",
                new String[]{Integer.toString(1)}, null, null, null);
        int i = 0;

        captions = new String[5];
        imageURL = new String[5];

        while (cursor.moveToNext()){
            Log.d("Cursor getString(1): ", "> " + cursor.getString(cursor.getColumnIndex("CAPTIONS")));
            Log.d("Cursor getString(2): ", "> " + cursor.getString(cursor.getColumnIndex("IMAGEURL")));
            captions[i] = cursor.getString(cursor.getColumnIndex("CAPTIONS"));
            imageURL[i] = cursor.getString(cursor.getColumnIndex("IMAGEURL"));
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
                Intent intent = new Intent(getActivity(), newsWebView.class);
                intent.putExtra(newsWebView.TAG, position);
                getActivity().startActivity(intent);
            }
        });
        // For endless Scrolling
        /*newsRecycler.addOnScrollListener(new EndlessScrollListener(layoutManager){
            @Override
            public void onLoadMore(int page, int totalItemCounts, RecyclerView newsRecycler){
                loadNextDataFromApi();
            }
        });*/

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {

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
