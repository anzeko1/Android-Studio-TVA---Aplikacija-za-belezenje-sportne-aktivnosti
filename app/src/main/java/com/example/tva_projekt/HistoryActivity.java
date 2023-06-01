package com.example.tva_projekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.tva_projekt.common.HistoryAdapter;
import com.example.tva_projekt.common.OnRecyclerViewCallback;
import com.example.tva_projekt.common.TVAapplication;

import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements OnRecyclerViewCallback {
    List<HistoryModel> historyModels = new ArrayList<>();
    TVAapplication app;
    private HistoryAdapter adapter;

    RequestQueue requestQueue = Volley.newRequestQueue(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        app = (TVAapplication)getApplication();
        adapter = new HistoryAdapter(this, historyModels);

        //TODO: Get data from database
        getAllActivities();

        /*
        historyModels = new ArrayList<>();
        historyModels.add(new HistoryModel("Running", "00:30:00", "2023-05-24", 12.5, R.drawable.running));
        historyModels.add(new HistoryModel("Cycling", "00:45:00", "2023-05-22", 15.2, R.drawable.cycling));
        historyModels.add(new HistoryModel("Walking", "00:15:00", "2023-04-27",10.5, R.drawable.walking));
        historyModels.add(new HistoryModel("Running", "00:30:00", "2023-04-27", 5.5, R.drawable.running));
        */


        adapter.setData(historyModels);

        RecyclerView rw = findViewById(R.id.recyclerView);
        rw.setLayoutManager(new LinearLayoutManager(this));
        rw.setAdapter(adapter);
    }

    // Method for getting all activities from database
    private void getAllActivities() {
        String URL = "http://localhost:3000/getAllActivities";
        try {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
                // Process the response
                List<HistoryModel> historyModels = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        // Parse the JSON object and create HistoryModel instances
                        String activityName = jsonObject.getString("activityName");
                        String activityDuration = jsonObject.getString("activityDuration");
                        String activityDate = jsonObject.getString("activityDate");
                        double activityDistance = jsonObject.getDouble("activityDistance");
                        int drawableId = jsonObject.getInt("drawableId");

                        // Create a new HistoryModel instance
                        HistoryModel historyModel = new HistoryModel(activityName, activityDuration, activityDate, activityDistance, drawableId);
                        historyModels.add(historyModel);
                    }

                    // Update your UI with the fetched history models
                    adapter.setData(historyModels);

                } catch (JSONException e) {
                    Log.e("Response", "onResponse: JSON parsing error", e);
                }
            }, error -> Log.e("Response", "onErrorResponse: " + error.getMessage()));
            requestQueue.add(request);
        } catch (Exception e) {
            Log.e("Response", "Error making GET request", e);
        }
    }

    @Override
    public void Callback(int Index) {
        Log.d("HistoryActivity", "Callback: " + Index);
        app.selectedItem = historyModels.get(Index);
        Intent viewIntent = new Intent(this, ViewActivity.class);
        startActivity(viewIntent);
    }

    public void filter_week(View view) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7); // Subtract 7 days from current date

        Date sevenDaysAgo = calendar.getTime();

        List<HistoryModel> filteredList = new ArrayList<>();

        for (HistoryModel item : historyModels) {
            try {
                Date itemDate = dateFormat.parse(item.getActivityDate());

                if (itemDate.after(sevenDaysAgo)) {
                    filteredList.add(item);
                }
            } catch (ParseException e) {
                Log.e("HistoryActivity", "filter_week: ", e);
            }
        }
        adapter.setData(filteredList);
    }

    public void filter_month(View view) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        Date oneMonthAgo = calendar.getTime();

        List<HistoryModel> filteredList = new ArrayList<>();

        for (HistoryModel item : historyModels) {
            try {
                Date itemDate = dateFormat.parse(item.getActivityDate());

                if (itemDate.after(oneMonthAgo)) {
                    filteredList.add(item);
                }
            } catch (ParseException e) {
                Log.e("HistoryActivity", "filter_week: ", e);
            }
        }
        adapter.setData(filteredList);
    }

    /* private void setHistoryModels() {
        String[] activityNames = getResources().getStringArray(R.array.activities_array);
        // še morem impelementirat za podatek iz baze za duration in date

        for (int i = 0; i<activityNames.length; i++) {
            historyModels.add(new HistoryModel(activityNames[]));
        }
    }*/
}