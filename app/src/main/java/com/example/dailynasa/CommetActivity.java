package com.example.dailynasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commet);
        getSupportActionBar().setTitle("Comet Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //final String Choosen = "";
        Spinner spinner = findViewById(R.id.spinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("2019");
        arrayList.add("2018");
        arrayList.add("2017");
        arrayList.add("2016");
        arrayList.add("2015");
        arrayList.add("2014");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Choose = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + Choose,
                        Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        Spinner spinner2;
        spinner2 = findViewById(R.id.spinner2);
        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("January");
        arrayList2.add("February");
        arrayList2.add("March");
        arrayList2.add("April");
        arrayList2.add("May");
        arrayList2.add("June");
        arrayList2.add("July");
        arrayList2.add("August");
        arrayList2.add("September");
        arrayList2.add("October");
        arrayList2.add("November");
        arrayList2.add("December");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Choosen = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + Choosen,
                        Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        Spinner spinner3;
        spinner3 = findViewById(R.id.spinner3);
        ArrayList<String> arrayList3 = new ArrayList<>();
        if (arrayList2.equals("February")) {
            for (int i = 0; i <= 28; i++) {
                arrayList3.add("" + i);
            }
        } else if (arrayList2.equals("September") || arrayList2.equals("April") || arrayList2.equals("June") || arrayList2.equals("November")) {
            for (int i = 0; i <= 30; i++) {
                arrayList3.add("" + i);
            }
        } else {
            for (int i = 0; i <= 31; i++) {
                arrayList3.add("" + i);
            }
        }

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList3);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(arrayAdapter3);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Choosem = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + Choosem,
                        Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        ///THIS STARTS THE ASYNCTASK TO GET API STUFF
        // dates in form YYYY-MM-DD;
        // these lines should give the current date;
        //long millis = System.currentTimeMillis();
        //java.sql.Date date = new java.sql.Date(millis);
        //new CometAsync(this, startDate, endDate);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }
    private class CometAsync extends AsyncTask<Void, Void, Void> {
        private Context context;
        private JSONObject object;
        private  String startDate;
        private  String endDate;
        public CometAsync(Context context, String startDate, String endDate) {
            this.context = context;
            this.startDate = startDate;
            this.endDate = endDate;
            this.execute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String test = new webHelper().fetchComet(startDate, endDate);
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
                /* ok  */

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
