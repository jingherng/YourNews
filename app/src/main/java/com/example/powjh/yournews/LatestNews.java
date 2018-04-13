package com.example.powjh.yournews;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LatestNews extends Activity {
    // Menu variables
    private String[] menu;
    private ListView menuList;
    private DrawerLayout drawerLayout;
    public static latestNewsAPI latest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_news);

        // Sets Menu
        menu = getResources().getStringArray(R.array.menu);
        menuList = findViewById(R.id.drawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
        menuList.setOnItemClickListener(new LatestNews.DrawerItemClickListener());

        //
        setNews();
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
                    Intent recommendIntent = new Intent(LatestNews.this, AccountRec.class);
                    startActivity(recommendIntent);
                    break;
                case 1:
                    // Latest news
                    //drawerLayout.closeDrawers();
                    break;
                case 2:
                    // Recommendations
                    Intent recommendations = new Intent(LatestNews.this, AIrecommends.class);
                    startActivity(recommendations);
                    break;
                case 3:
                    // Bookmarks
                    Intent bmIntent = new Intent(LatestNews.this, Bookmarks.class);
                    startActivity(bmIntent);
                    break;
                case 4:
                    // Help
                    Intent help = new Intent(LatestNews.this, help.class);
                    startActivity(help);
                    break;
            }
        }
    }

    private void setNews(){
        latest = new latestNewsAPI(this);
        latest.execute();
    }
}
