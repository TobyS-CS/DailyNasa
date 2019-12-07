package com.example.dailynasa;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class webHelper {
    final String API_KEY = "U8vRzGRmgweqsmKPxmDpPNeRQWpBsgXNZ3zCeJrW";
    // This code is done with  help from Big Nerd Ranch's Adnroid Programing 3rd Edition second printing, april 2017
    public byte[] getUrlBytes(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = con.getInputStream();
            if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException();
            }
            // I don't know why this is needed but all the stack overflow people have a similar variable
            // So I trust them and am going to run with this.
            int byresRead = 0;
            byte[] buffer = new byte[1024];
            while ((byresRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, byresRead);
            }
            outputStream.close();
            return outputStream.toByteArray();
        } catch(Exception e) {

        } finally {
            con.disconnect();
        }
        return null;
    }
    public String fetchApod() {
        long millis = System.currentTimeMillis();
        try {
            java.sql.Date date = new java.sql.Date(millis);
            String url = Uri.parse("https://api.nasa.gov/planetary/apod")
                    .buildUpon()
                    .appendQueryParameter("date", (date.toString()))
                    .appendQueryParameter("api_key", API_KEY)
                    .build()
                    .toString();
            String test = new String(getUrlBytes(url));
            Log.e("FETCH APOD", "GOT JSON: " + test);
            return test;
        } catch(Exception e) {

        }
        return "";
    }
}
