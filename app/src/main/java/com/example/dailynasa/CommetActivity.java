package com.example.dailynasa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
}
