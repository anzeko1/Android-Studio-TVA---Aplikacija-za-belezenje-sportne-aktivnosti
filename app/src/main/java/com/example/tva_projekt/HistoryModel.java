package com.example.tva_projekt;

import com.example.tva_projekt.dataObjects.MyGeoPoint;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.osmdroid.util.GeoPoint;

import java.util.List;

public class HistoryModel {
    @SerializedName("activityName")
    @Expose
    public String activityName;
    @SerializedName("activityDuration")
    @Expose
    public String activityDuration;
    @SerializedName("activityDate")
    @Expose
    public String activityDate;
    @SerializedName("activityDistance")
    @Expose
    public Double activityDistance;
    @SerializedName("coordinates")
    @Expose
    public MyGeoPoint[] PATH_COORDINATES;
    int image;

    public HistoryModel(String activityName, String activityDuration, String activityDate, Double activityDistance, int image) {
        this.activityName = activityName;
        this.activityDuration = activityDuration;
        this.activityDate = activityDate;
        this.activityDistance = activityDistance;
        this.image = image;

    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityDuration() {
        return activityDuration;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public Double getActivityDistance() {
        return activityDistance;
    }

    public int getImage() {
        return image;
    }
}
