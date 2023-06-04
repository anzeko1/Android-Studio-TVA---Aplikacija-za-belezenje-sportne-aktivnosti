package com.example.tva_projekt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.example.tva_projekt.common.TVAapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class WeeklyHistoryActivity extends AppCompatActivity {
    private LineChartView lineChartView;

    TVAapplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_weekly);
        app = (TVAapplication)getApplication();

        lineChartView = findViewById(R.id.stepsChart);

        /*List<Integer> stepsPerDay = new ArrayList<>();
        stepsPerDay.add(2500);
        stepsPerDay.add(3500);
        stepsPerDay.add(4000);
        stepsPerDay.add(3000);
        stepsPerDay.add(5000);
        stepsPerDay.add(6000);
        stepsPerDay.add(4500);*/

        new /*WeeklyHistoryActivity.*/GetActivitiesData().execute();
    }

    private class GetActivitiesData extends AsyncTask<String, Void, List<Integer>> {
        @Override
        protected List<Integer> doInBackground(String... params) {
            List<Integer> stepsPerDay = new ArrayList<>();
            List<Integer> kmPerDay = new ArrayList<>();

            try {
                String urlStr = "http://192.168.0.12:3000/activity/getAllActivities/" + app.user.getIdUser();
                // Create a new connection for retrieving activities
                URL activitiesUrl = new URL(urlStr);
                HttpURLConnection activitiesConnection = (HttpURLConnection) activitiesUrl.openConnection();
                activitiesConnection.setRequestMethod("GET");
                //activitiesConnection.setRequestProperty("Content-Type", "application/json");
                //activitiesConnection.setDoOutput(true);

                // Get the activities response from the server
                int activitiesResponseCode = activitiesConnection.getResponseCode();
                if (activitiesResponseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = null;
                    br = new BufferedReader(new InputStreamReader(activitiesConnection.getInputStream()));
                    String strCurrentLine;
                    StringBuilder responseBuilder = new StringBuilder();
                    while ((strCurrentLine = br.readLine()) != null) {
                        System.out.println(strCurrentLine);
                        responseBuilder.append(strCurrentLine);
                    }
                    // Parse the JSON response and extract steps and km data
                    JSONArray jsonArray = new JSONArray(responseBuilder.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject.has("steps")) {
                            int steps = jsonObject.getInt("steps");
                            stepsPerDay.add(steps);

                            if (jsonObject.has("activityDistance")) {
                                int distance = jsonObject.getInt("activityDistance");
                                kmPerDay.add(distance);
                            } else {
                                kmPerDay.add(0);
                            }
                        }
                    }
                } else {
                    Log.e("GetActivitiesData", "Failed retrieval of activities. Response Code: " + activitiesResponseCode);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.d("GetActivitiesData", e.getMessage());
            }

            return stepsPerDay;
        }

        /*private boolean isLoggedIn() {
            SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
            return sharedPreferences.getBoolean("isLoggedIn", false);
        }*/

        public void goToHistoryActivity(View view) {
            Intent intent = new Intent(WeeklyHistoryActivity.this, HistoryActivity.class);
            startActivity(intent);
        }

        @Override
        protected void onPostExecute(List<Integer> stepsPerDay) {
            super.onPostExecute(stepsPerDay);

            // Print the collected steps data
            for (int steps : stepsPerDay) {
                System.out.println(steps);
            }

            // Find the LineChartView and TextView in your activity layout
            LineChartView stepsChart = findViewById(R.id.stepsChart);

            // Prepare data for the chart
            List<PointValue> pointValues = new ArrayList<>();
            List<AxisValue> axisValues = new ArrayList<>();

            for (int i = 0; i < stepsPerDay.size(); i++) {
                int steps = stepsPerDay.get(i);
                pointValues.add(new PointValue(i, steps));
                axisValues.add(new AxisValue(i).setLabel("Day " + (i + 1)));
            }

            Line line = new Line(pointValues).setColor(Color.BLUE).setCubic(true);
            List<Line> lines = new ArrayList<>();
            lines.add(line);

            LineChartData data = new LineChartData();
            data.setLines(lines);

            // Customize the axis labels
            Axis axisX = new Axis(axisValues).setHasLines(true).setTextColor(Color.BLACK);
            Axis axisY = new Axis().setHasLines(true).setTextColor(Color.BLACK);

            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);

            // Set the chart data and update the title
            stepsChart.setLineChartData(data);

            // Refresh the chart
            stepsChart.invalidate();
        }
    }
}