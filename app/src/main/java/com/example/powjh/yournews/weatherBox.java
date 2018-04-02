package com.example.powjh.yournews;

import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.os.Bundle;

import java.util.HashMap;

public class weatherBox extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        String[] date = new String[5];
        String[] temp = new String[5];
        String[] iconIds = new String[5];

        int i = 0;
        for (HashMap<String, String> item: weatherAPI.weatherList){
            date[i] = item.get("dt_txt");
            temp[i] = item.get("temp");
            iconIds[i] = item.get("icon");
            i++;
        }
        weatherBoxAdapter adapter = new weatherBoxAdapter(date, iconIds, temp);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView weatherRecycler = (RecyclerView) inflater.inflate(R.layout.recyclerview, container, false);
        weatherRecycler.setLayoutManager(layoutManager);
        weatherRecycler.setAdapter(adapter);
        return weatherRecycler;
    }
}
