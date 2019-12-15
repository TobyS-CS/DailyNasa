package com.example.dailynasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//Json stuff.
import org.json.JSONObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
//Picasso
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommetActivity extends AppCompatActivity {
    private String year;
    private String month;
    private String day;
    private Context CONTEXT = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        year = "2015";
        month = "09";
        day = "08";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commet);
        getSupportActionBar().setTitle("Comet Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Map<String, Integer> monthToDays = new LinkedHashMap<String, Integer>();
        // maps months to the days within them.
        monthToDays.put("January", 31);
        monthToDays.put("February", 28);
        monthToDays.put("March", 31);
        monthToDays.put("April", 30);
        monthToDays.put("May", 31);
        monthToDays.put("June", 30);
        monthToDays.put("July", 31);
        monthToDays.put("August", 31);
        monthToDays.put("September", 30);
        monthToDays.put("October", 31);
        monthToDays.put("November", 30);
        monthToDays.put("December", 31);
        Spinner spinner = findViewById(R.id.year_spinner);
        ArrayList<String> yearList = new ArrayList<>();
        yearList.add("2019");
        yearList.add("2018");
        yearList.add("2017");
        yearList.add("2016");
        yearList.add("2015");
        yearList.add("2014");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + year,
                        Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        Spinner spinner2;
        spinner2 = findViewById(R.id.month_spinner);
        String [] monthList = new String[12];
        monthList = monthToDays.keySet().toArray(monthList);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, monthList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = position + 1 + "";
                if (month.length() < 2) {
                    month = "0" + month;
                }
                setDaySpinner(parent, monthToDays);
                Toast.makeText(parent.getContext(), "Selected: " + month,
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
        // make button to launch;
        Button load = findViewById(R.id.load);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout)findViewById(R.id.cometHolder)).removeAllViews();
                String startDate = year + "-" + month + "-" + day;
                new CometAsync(CONTEXT, startDate);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }
    private void setDaySpinner(AdapterView months, Map<String, Integer> monthToDays) {
        Spinner daySpinner;
        daySpinner = findViewById(R.id.day_spinner);
        ArrayList<Integer> daysList = new ArrayList<>();
        for (int i = 1; i <= monthToDays.get(months.getSelectedItem()); i++) {
            daysList.add(i);
        }
        ArrayAdapter<Integer> arrayAdapter3 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, daysList);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(arrayAdapter3);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = position + 1 + "";
                if (day.length() != 2) {
                    day = 0 + day;
                }
                Toast.makeText(parent.getContext(), "Selected: " + (position + 1),
                Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }
    // Seperate class.
    private class CometAsync extends AsyncTask<Void, Void, Void> {
        private Context context;
        private JsonObject object;
        private  String startDate;
        public CometAsync(Context context, String startDate) {
            this.context = context;
            this.startDate = startDate;
            this.execute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String test = new webHelper().fetchComet(startDate);
                JSONObject toParse = new JSONObject(test);
                JsonParser parser = new JsonParser();
                object = (JsonObject) parser.parse(toParse.toString());
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
                // this is the object representing the comets from web api.
                JsonObject jsonObject = getJson();
                JsonObject dateArray = jsonObject.get("near_earth_objects").getAsJsonObject().getAsJsonObject();
                LinearLayout cometHolder = findViewById(R.id.cometHolder);
                for (JsonElement comet : dateArray.get(startDate).getAsJsonArray()) {
                    View cometChunk = getLayoutInflater().inflate(R.layout.comet_chunk, cometHolder, false);
                    ((TextView) cometChunk.findViewById(R.id.name))
                            .setText(comet.getAsJsonObject().get("name").getAsString());
                    ((TextView) cometChunk.findViewById(R.id.size))
                            .setText(comet.getAsJsonObject()
                                    .get("estimated_diameter").getAsJsonObject()
                                    .get("meters").getAsJsonObject()
                                    .get("estimated_diameter_max").getAsString());
                    for (JsonElement date : comet.getAsJsonObject().get("close_approach_data").getAsJsonArray()) {
                        ((TextView) (cometChunk.findViewById(R.id.date)))
                                .setText(date.getAsJsonObject()
                                        .get("close_approach_date").getAsString());
                    }
                    cometHolder.addView(cometChunk);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public JsonObject getJson() { return object;
        }
    }
}
