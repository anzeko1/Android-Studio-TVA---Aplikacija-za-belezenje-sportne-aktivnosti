package com.example.tva_projekt;

import org.osmdroid.util.GeoPoint;

import java.util.List;

public class HistoryModel {
    String activityName;
    String activityDuration;
    String activityDate;

    Double activityDistance;
    int image;

    public GeoPoint[] PATH_COORDINATES = {
            new GeoPoint(46.34746762253959, 15.40569592159496),
            new GeoPoint(46.34266836576128, 15.412133222749201),
            new GeoPoint(46.339527883288895, 15.417197232990535),
            new GeoPoint(46.33970565126404, 15.426810269380866),
            new GeoPoint(46.34124628283859, 15.434792522812122),
            new GeoPoint(46.341068519872444, 15.445607188751245),
            new GeoPoint(46.34391265797569, 15.45736599219299),
    };


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
