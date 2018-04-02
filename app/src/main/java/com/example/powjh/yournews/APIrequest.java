package com.example.powjh.yournews;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

public class APIrequest {

    static String response = null;
    private final String USER_AGENT = "Mozilla/5.0";


    //Constructor with no parameter
    public APIrequest() {
    }

    // urladdress - url to make web request

    public String makeWebServiceCall(String urladdress) {
        URL url;
        String response = "";
        try {
            url = new URL(urladdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //Request header
            conn.setRequestProperty("User-Agent", USER_AGENT);

            int reqresponseCode = conn.getResponseCode();
            Log.d("Response Code: ", "> " + reqresponseCode);

            if (reqresponseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}