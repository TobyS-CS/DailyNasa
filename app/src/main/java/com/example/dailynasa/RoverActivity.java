package com.example.dailynasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.*;

import org.json.JSONObject;

public class  RoverActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover);
        getSupportActionBar().setTitle("Rover Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new RoverAsync(this);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }
    private class RoverAsync extends AsyncTask<Void, Void, Void> {
        private Context context;
        private JsonObject object;
        public RoverAsync(Context context) {
            // this stores the context and makes sure that the image is loaded or fails to load
            // before anything else can be done to it which is why the execute is in here.
            this.context = context;
            this.execute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String test = new webHelper().fetchRover();
                JSONObject toParse = new JSONObject(test);
                JsonParser parser = new JsonParser();
                object = (JsonObject) parser.parse(toParse.toString());
                Log.e("TESTHELP", object.toString());
            } catch (Exception e) {
                Log.e("RoverAsync", "FAILED TO GET URL", e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JsonObject jsonObject = getJson();
                String test;
                LinearLayout layout = findViewById(R.id.imageScroll);
                for (JsonElement picture : jsonObject.get("photos").getAsJsonArray()) {
                    View imageChunk = getLayoutInflater().inflate(R.layout.image_chunk, layout, false);
                    test = picture.getAsJsonObject().get("img_src").getAsString();
                    test =  "https:" + test.substring(5);
                    Picasso.get().load(test).into((ImageView)imageChunk.findViewById(R.id.roverImage));
                    layout.addView(imageChunk);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public JsonObject getJson() {
            return object;
        }
    }

}
