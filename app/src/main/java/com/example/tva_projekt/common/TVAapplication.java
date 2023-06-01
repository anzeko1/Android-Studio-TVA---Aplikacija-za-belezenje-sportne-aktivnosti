package com.example.tva_projekt.common;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.tva_projekt.HistoryModel;
import com.example.tva_projekt.dataObjects.AppUser;

public class TVAapplication extends Application {
    public HistoryModel selectedItem;

    public AppUser user;

    @Override
    public void onCreate() {

        super.onCreate();

        user = new AppUser();

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        user.setIdUser(sharedPreferences.getString("idUser", ""));
    }
}
