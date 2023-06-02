package com.example.tva_projekt;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.core.content.ContextCompat;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.AxisAutoValues;
import lecho.lib.hellocharts.view.LineChartView;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tva_projekt.common.TVAapplication;
import com.example.tva_projekt.dataObjects.ActivityObject;
import com.example.tva_projekt.dataObjects.AppUser;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class WeeklyHistoryActivity extends AppCompatActivity {
    private LineChartView lineChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_weekly);

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

    /*private void displayChartData(List<Integer> stepsPerDay) {
        // Create axis values for X-axis (days)
        List<AxisValue> axisValuesX = new ArrayList<>();
        for (int i = 0; i < stepsPerDay.size(); i++) {
            axisValuesX.add(new AxisValue(i).setLabel("Day " + (i + 1)));
        }

        // Create data points for the line chart
        List<PointValue> values = new ArrayList<>();
        for (int i = 0; i < stepsPerDay.size(); i++) {
            values.add(new PointValue(i, stepsPerDay.get(i)));
        }

        // Create a line and set its attributes
        Line line = new Line(values)
                .setColor(ContextCompat.getColor(this, R.color.save_button))
                .setCubic(true)
                .setHasPoints(true)
                .setShape(ValueShape.CIRCLE);

        // Create a list of lines to be displayed in the chart
        List<Line> lines = new ArrayList<>();
        lines.add(line);

        // Create and customize the chart data
        LineChartData data = new LineChartData();
        data.setLines(lines);

        // Set X-axis and Y-axis attributes
        Axis axisX = new Axis(axisValuesX)
                .setHasLines(true)
                .setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        Axis axisY = new Axis().setHasLines(true);

        // Set the chart data and axes
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        // Set the chart data to the chart view
        lineChartView.setLineChartData(data);

        // Set the initial viewport for the chart
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.bottom = 0;
        viewport.top = getMaxSteps(stepsPerDay);
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }*/

    /*private int getMaxSteps(List<Integer> stepsPerDay) {
        int maxSteps = 0;
        for (int steps : stepsPerDay) {
            if (steps > maxSteps) {
                maxSteps = steps;
            }
        }
        return maxSteps;
    }*/

    private class GetActivitiesData extends AsyncTask<String, Void, Pair<List<Integer>, List<Integer>>> {
        @Override
        protected Pair<List<Integer>, List<Integer>> doInBackground(String... params) {
            List<Integer> stepsPerDay = new ArrayList<>();
            List<Integer> kmPerDay = new ArrayList<>();

            try {
                String urlStr = "http://192.168.0.12:3000/activity/getAllActivities";
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

            return new Pair<>(stepsPerDay, kmPerDay);
        }

        @Override
        protected void onPostExecute(Pair<List<Integer>, List<Integer>> result) {
            List<Integer> stepsPerDay = result.first;
            List<Integer> kmPerDay = result.second;

            // Find the LineChartView and TextView in your activity layout
            LineChartView stepsChart = findViewById(R.id.stepsChart);

            //LineChartView kmChart = findViewById(R.id.kmChart);

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