package com.example.tva_projekt.common;

import android.app.Application;

import com.example.tva_projekt.HistoryModel;

public class TVAapplication extends Application {
    public HistoryModel selectedItem;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
