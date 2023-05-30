package com.example.tva_projekt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

public class WeeklyHistoryActivity extends AppCompatActivity {

    private LineChartView lineChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_weekly);

        lineChartView = findViewById(R.id.lineChartView);

        // Sample data for demonstration
        List<Integer> stepsPerDay = new ArrayList<>();
        stepsPerDay.add(2500);
        stepsPerDay.add(3500);
        stepsPerDay.add(4000);
        stepsPerDay.add(3000);
        stepsPerDay.add(5000);
        stepsPerDay.add(6000);
        stepsPerDay.add(4500);

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
    }

    private int getMaxSteps(List<Integer> stepsPerDay) {
        int maxSteps = 0;
        for (int steps : stepsPerDay) {
            if (steps > maxSteps) {
                maxSteps = steps;
            }
        }
        return maxSteps;
    }
}
