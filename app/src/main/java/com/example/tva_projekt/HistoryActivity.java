package com.example.tva_projekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.tva_projekt.common.HistoryAdapter;
import com.example.tva_projekt.common.OnRecyclerViewCallback;
import com.example.tva_projekt.common.TVAapplication;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        app = (TVAapplication)getApplication();
        adapter = new HistoryAdapter(this, historyModels);

        //TODO: Get data from database

        historyModels = new ArrayList<>();
        historyModels.add(new HistoryModel("Running", "00:30:00", "2023-05-24", 12.5, R.drawable.running));
        historyModels.add(new HistoryModel("Cycling", "00:45:00", "2023-05-22", 15.2, R.drawable.cycling));
        historyModels.add(new HistoryModel("Walking", "00:15:00", "2023-04-27",10.5, R.drawable.walking));
        historyModels.add(new HistoryModel("Running", "00:30:00", "2023-04-27", 5.5, R.drawable.running));


        adapter.setData(historyModels);

        RecyclerView rw = findViewById(R.id.recyclerView);
        rw.setLayoutManager(new LinearLayoutManager(this));
        rw.setAdapter(adapter);
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
    public void weeklyHistoryActivity(View view) {
        Intent intent = new Intent(HistoryActivity.this, WeeklyHistoryActivity.class);
        startActivity(intent);
    }
}