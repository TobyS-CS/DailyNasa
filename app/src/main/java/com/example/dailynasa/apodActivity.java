package com.example.dailynasa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class apodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod);
        getSupportActionBar().setTitle("Picture Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        JSONObject object;
        try {
            //this gets the apod json as a string from main activity
            object = new JSONObject(this.getIntent().getExtras().getString("Json"));
            // puts the image into the image view.
            View v = findViewById(R.id.apodImage);
            Picasso.get()
                    .load(object.getString("url"))
                    .into((ImageView) findViewById(R.id.apodImage));
            //this makes the textview scrollable and adds the text.
            TextView description = (TextView) findViewById(R.id.apodDescription);
            description.setMovementMethod(new ScrollingMovementMethod());
            description.setText(object.get("explanation").toString());
            description.setHeight(description.getLineHeight() * description.getLineCount());
        } catch (Exception e) {
            Log.e("Json making error", e.getMessage());
        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }
}
