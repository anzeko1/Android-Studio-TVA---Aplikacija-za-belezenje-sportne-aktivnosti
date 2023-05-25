package com.example.tva_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    // ArrayList<HistoryModel> historyModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
    }

    /* private void setHistoryModels() {
        String[] activityNames = getResources().getStringArray(R.array.activities_array);
        // še morem impelementirat za podatek iz baze za duration in date

        for (int i = 0; i<activityNames.length; i++) {
            historyModels.add(new HistoryModel(activityNames[]));
        }
    }*/
}