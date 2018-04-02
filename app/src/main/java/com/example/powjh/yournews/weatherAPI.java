package com.example.powjh.yournews;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

class weatherAPI extends AsyncTask<Void, Void, Boolean> {
    Activity c;
    // URL to get Weather
    private static String weatherURL = "http://api.openweathermap.org/data/2.5/forecast?q=Singapore&APPID=0a608291ea9cfb37d56a0425638d057c";
    // JSON Node names
    public static final String TAG_DATETIME = "dt_txt";
    public static final String TAG_TEMP = "temp";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_ICON = "icon";

    // Hashmap for ListView
    public static ArrayList<HashMap<String, String>> weatherList;

    // empty constructor
    public weatherAPI(Activity c) {
        this.c = c;
    }

    @Override
    protected Boolean doInBackground(Void... Void) {
        // Creating service handler class instance
        APIrequest webreq = new APIrequest();
        // Making a request to url and getting response
        String jsonStr = webreq.makeWebServiceCall(weatherURL);
        //Log.d("Response: ", "> " + jsonStr);
        weatherList = ParseJSON(jsonStr);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean v){
        Fragment fragment = new weatherBox();
        FragmentManager manager = c.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.weather_Box, fragment);
        transaction.commit();
    }

    private ArrayList<HashMap<String, String>> ParseJSON(String json) {
        if (json != null) {
            try {
                //Hashmap for ListView
                ArrayList<HashMap<String, String>> weatherList = new ArrayList<HashMap<String, String>>();

                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Array node
                JSONArray weathers = jsonObj.getJSONArray("list");


                for (int i = 0; i < 40; i+=8) {
                    JSONObject w = weathers.getJSONObject(i);

                    //Getting the date
                    String dateTime = w.getString(TAG_DATETIME);

                    //Getting the temp
                    JSONObject main = w.getJSONObject("main");
                    String temp = Double.toString(Math.round(Double.parseDouble(main.getString(TAG_TEMP)) - 273.15));

                    //Getting the weather icon and description
                    JSONArray weather = w.getJSONArray("weather");
                    JSONObject realWeatherItem = weather.getJSONObject(0);
                    String description = realWeatherItem.getString(TAG_DESCRIPTION);
                    String formatDescription = description.substring(0, 1).toUpperCase() + description.substring(1);
                    String icon = realWeatherItem.getString(TAG_ICON);

                    HashMap<String, String> hm = new HashMap<String, String>();

                    //formatterOut.format(dateFormatted)
                    hm.put(TAG_DATETIME, dateTime.substring(8,10) + "/" + dateTime.substring(5,7));
                    hm.put(TAG_TEMP, temp + "°C");
                    hm.put(TAG_DESCRIPTION, formatDescription);
                    hm.put(TAG_ICON, icon);

                    weatherList.add(hm);

                }

                Log.d("Response Code: ", "> " + weatherList);


                return weatherList;
            }catch(JSONException e){
                e.printStackTrace();
                return null;
            }
        } else{
            Log.e("ServiceHandler", "No data received from HTTP Request");
            return null;
        }
    }
}


