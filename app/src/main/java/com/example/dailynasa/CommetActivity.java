package com.example.dailynasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class CommetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commet);
        getSupportActionBar().setTitle("Comet Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
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
                ////SEAN DO INFLATION IN HERE IT SHOULD WORK TELL ME IF THERE is AN ISSUE///
                // this is the object representing the comets from web api.
                JSONObject jsonObject = getJson();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public JSONObject getJson() {
            return object;
        }
    }
}
