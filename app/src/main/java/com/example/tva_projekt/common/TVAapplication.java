package com.example.tva_projekt.common;

import android.app.Application;

import com.example.tva_projekt.HistoryModel;
import com.example.tva_projekt.dataObjects.AppUser;

public class TVAapplication extends Application {
    public HistoryModel selectedItem;

    public AppUser user;

    @Override
    public void onCreate() {

        super.onCreate();

        user = new AppUser();
    }
}
