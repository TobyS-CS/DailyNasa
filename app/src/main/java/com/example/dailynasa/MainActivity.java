package com.example.dailynasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.*;


public class MainActivity extends AppCompatActivity {
///TEST COMMENT FOR TEST COMMIT.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = this;
        backGroundAsync async = new backGroundAsync(context);
        //stores this so that it can be accessed in the onClicks.
        //The Onclick to start the rover activity.
        Button roverButton = findViewById(R.id.roverButton);
        roverButton.setOnClickListener(unused -> roverStart(context));
        //button for comet activity.
        Button cometButton = findViewById(R.id.cometButton);
        cometButton.setOnClickListener(unused -> cometStart(context));
        //button for apod.
        Button apodButton = findViewById(R.id.dailyPictureButton);
        apodButton.setOnClickListener(unused -> pictureStart(context, async.getJson()));
    }

    /**
     * starts the Rover activity.
     * @param context this activity.
     */
    private void roverStart(Context context) {
        startActivity(new Intent(context, RoverActivity.class));
    }
    /**
     * starts the comet activity.
     * @param context this activity.
     */
    private void cometStart(Context context) {
        startActivity(new Intent(context, CommetActivity.class));
    }
    /**
     * starts the apod activity.
     * @param context this activity.
     */
    private void pictureStart(Context context, JSONObject object) {
        Intent intent = new Intent(context, apodActivity.class);
        intent.putExtra("Json", object.toString());
        startActivity(intent);
    }
    private class backGroundAsync extends AsyncTask<Void, Void, Void> {
        private Context context;
        private JSONObject object;
        public backGroundAsync(Context context) {
            // this stores the context and makes sure that the image is loaded or fails to load
            // before anything else can be done to it which is why the execute is in here.
            this.context = context;
            this.execute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String test = new webHelper().fetchApod();
                object = new JSONObject(test);
                Log.e("TESTHELP", object.toString());
            } catch (Exception e) {
                Log.e("APIASYNC", "FAILED TO GET URL", e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                View v = findViewById(R.id.constraint);
                Picasso.get()
                        .load(getJson()
                        .getString("url"))
                        .resize(v.getWidth(), v.getHeight())
                        .into((ImageView) findViewById(R.id.imageView3));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public JSONObject getJson() {
            return object;
        }
    }
}
