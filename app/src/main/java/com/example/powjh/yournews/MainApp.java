package com.example.powjh.yournews;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.sql.PreparedStatement;

public class MainApp extends Activity {
    // Menu variables
    private String[] menu;
    private ListView menuList;
    private DrawerLayout drawerLayout;
    public static headlinesAPI headlines;
    public static searchAPI searchAPI;
    public static watsonDB watsonDB;
    public static UserInfoDB userDB;
    public static BookmarksDB bmDB;
    private EndlessScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        watsonDB = new watsonDB(this);
        userDB = new UserInfoDB(this);
        bmDB = new BookmarksDB(this);

        // Sets Menu
        menu = getResources().getStringArray(R.array.menu);
        menuList = findViewById(R.id.drawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
        menuList.setOnItemClickListener(new DrawerItemClickListener());

        // Sets Weather Box
        setWeather();

        // Set headlines
        setHeadlines();

        // Sets DB
        SQLiteOpenHelper dbHelper = userDB;
        LinearLayout searchResults = findViewById(R.id.SearchResults);
        searchResults.setVisibility(View.GONE);
        TextView notfound = findViewById(R.id.notFound);
        notfound.setVisibility(View.GONE);

        // Sets Search view
        final SearchView search = findViewById(R.id.Search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                searchAPI = new searchAPI(MainApp.this);
                searchAPI.setQuery(s);
                searchAPI.execute();
                LinearLayout searchResults = findViewById(R.id.SearchResults);
                searchResults.setVisibility(View.VISIBLE);
                LinearLayout rest = findViewById(R.id.rest);
                rest.setVisibility(View.GONE);

                // Adds keywords of searches to database
                ContentValues keywords = new ContentValues();
                keywords.put("KEYS" , s);
                SQLiteOpenHelper dbHelper = watsonDB;
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                String query = "INSERT INTO USER_KEYS(KEYS) VALUES (?)";
                SQLiteStatement stmt = db.compileStatement(query);
                stmt.bindString(1, s);
                stmt.execute();
                db.close();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            selectItem(position);
        }

        private void selectItem(int position){
            switch(position){
                case 0:
                    // Account Settings
                    Intent recommendIntent = new Intent(MainApp.this, AccountRec.class);
                    startActivity(recommendIntent);
                    break;
                case 1:
                    // Latest news
                    Intent latestNewsIntent = new Intent(MainApp.this, LatestNews.class);
                    startActivity(latestNewsIntent);
                    break;
                case 2:
                    // Recommendations
                    Intent recommendations = new Intent(MainApp.this, AIrecommends.class);
                    startActivity(recommendations);
                    break;
                case 3:
                    // Bookmarks
                    Intent bmIntent = new Intent(MainApp.this, Bookmarks.class);
                    startActivity(bmIntent);
                    break;
            }
        }
    }

    private void setWeather(){
        new weatherAPI(this).execute();
    }

    private void setHeadlines(){
        headlines = new headlinesAPI(this);
        headlines.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LinearLayout searchResults = findViewById(R.id.SearchResults);
        searchResults.setVisibility(View.GONE);
        LinearLayout rest = findViewById(R.id.rest);
        rest.setVisibility(View.VISIBLE);
    }
}
