package com.example.powjh.yournews;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AIrecommends extends Activity {

    // Menu variables
    private String[] menu;
    private ListView menuList;
    private DrawerLayout drawerLayout;

    private watson watson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airecommends);

        watson = new watson();

        // Sets Menu
        menu = getResources().getStringArray(R.array.menu);
        menuList = findViewById(R.id.drawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
        menuList.setOnItemClickListener(new AIrecommends.DrawerItemClickListener());

        executeSearch();
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
                    Intent recommendIntent = new Intent(AIrecommends.this, AccountRec.class);
                    startActivity(recommendIntent);
                    break;
                case 1:
                    // Latest news
                    Intent latestNewsIntent = new Intent(AIrecommends.this, LatestNews.class);
                    startActivity(latestNewsIntent);
                    break;
                case 2:
                    // Recommendations
                    //drawerLayout.closeDrawers();
                    break;
                case 3:
                    // Bookmarks
                    Intent bmIntent = new Intent(AIrecommends.this, Bookmarks.class);
                    startActivity(bmIntent);
                    break;
            }
        }
    }

    private void executeSearch(){
        mrWatson mrWatson = new mrWatson(AIrecommends.this);
        mrWatson.execute();
    }
}
