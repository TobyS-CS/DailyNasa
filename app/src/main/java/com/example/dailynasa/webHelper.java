package com.example.dailynasa;

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class webHelper {
    final String API_KEY = "U8vRzGRmgweqsmKPxmDpPNeRQWpBsgXNZ3zCeJrW";
    // This code is done with  help from Big Nerd Ranch's Adnroid Programing 3rd Edition second printing, april 2017

    /**
     * @param urlString The url that the data is downloaded from.
     * @return returns an array of bytes that is later decoded to give the readable information.
     * @throws IOException throws an exception if it does not connect to the server properly.
     */
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
    private String fetch(String url) {
        try {
            String test = new String(getUrlBytes(url));
            Log.e("FETCH", "GOT JSON: " + test);
            return test;
        } catch (IOException e) {
            Log.e("Failed FETCH", "Failed JSON: ");
            return "";
        }
    }
    public String fetchApod() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String test = (fetch(apodBuilder(date)));
        String url = "";
        if (!(test.toLowerCase().contains("youtube"))) {
            url = apodBuilder(date);
        } else {
            millis  = System.currentTimeMillis()-24*60*60*1000;
            date = new java.sql.Date(millis);
            url = apodBuilder(date);
        }
        return fetch(url);
    }
    private String apodBuilder ( java.sql.Date date) {
        String url = Uri.parse("https://api.nasa.gov/planetary/apod")
                .buildUpon()
                .appendQueryParameter("date", (date.toString()))
                .appendQueryParameter("api_key", API_KEY)
                .build()
                .toString();
        return url;
    }
    public String fetchComet(String startDate) {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String url = "https://api.nasa.gov/neo/rest/v1/feed?start_date="
                + startDate + "&api_key=" + API_KEY;
        return fetch(url);
    }
    public String fetchRover() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String url = Uri.parse("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos")
                .buildUpon()
                .appendQueryParameter("earth_date", (date.toString()))
                .appendQueryParameter("api_key", API_KEY)
                .build()
                .toString();
        return fetch(url);
    }
}
