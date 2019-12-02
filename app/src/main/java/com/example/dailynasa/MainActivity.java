package com.example.dailynasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
///TEST COMMENT FOR TEST COMMIT.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
