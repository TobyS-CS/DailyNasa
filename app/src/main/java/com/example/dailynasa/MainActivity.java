package com.example.dailynasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
        new backGroundAsync(this);
        //stores this so that it can be accessed in the onClicks.
        final Context context = this;
        //The Onclick to start the rover activity.
        Button roverButton = findViewById(R.id.roverButton);
        roverButton.setOnClickListener(unused -> roverStart(context));
        //button for comet activity.
        Button cometButton = findViewById(R.id.cometButton);
        cometButton.setOnClickListener(unused -> cometStart(context));
        //button for apod.
        Button apodButton = findViewById(R.id.dailyPictureButton);
        apodButton.setOnClickListener(unused -> pictureStart(context));
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
    private void pictureStart(Context context) {
        startActivity(new Intent(context, apodActivity.class));
    }
    private class backGroundAsync extends AsyncTask<Void, Void, Void> {
        private Context context;
        private JSONObject object;
        public backGroundAsync(Context context) {
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
                Picasso.get().load(getJson().getString("url")).into((ImageView) findViewById(R.id.imageView3));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public JSONObject getJson() {
            return object;
        }
    }
}
