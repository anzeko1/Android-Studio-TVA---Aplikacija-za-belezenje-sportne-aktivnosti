package com.example.tva_projekt.dataObjects;

import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityObject {
    private String idUser;
    private String activityName;
    private String activityType;
    private String activityTypeRecord;
    private Date activityDate;
    private String startActivity;
    private String stopActivity;
    private String activityLength;
    private Double lon;
    private Double lat;
    private String description;
    public int steps;

    public long activityDuration;

    public List<MyGeoPoint> coordinates;

    public Double activityDistance;
    private String response;

    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public String getActivityName() {
        return activityName;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    public String getActivityTypeRecord() {
        return activityTypeRecord;
    }
    public void setActivityTypeRecord(String activityTypeRecord) {
        this.activityTypeRecord = activityTypeRecord;
    }
    public String getActivityType() {
        return activityType;
    }
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
    public Date getActivityDate() {
        return activityDate;
    }
    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }
    public String getStartActivity() {
        return startActivity;
    }
    public void setStartActivity(String startActivity) {
        this.startActivity = startActivity;
    }
    public String getStopActivity() {
        return stopActivity;
    }
    public void setStopActivity(String stopActivity) {
        this.stopActivity = stopActivity;
    }
    public String getActivityLength() {
        return activityLength;
    }
    public void setActivityLength(String activityLength) {
        this.activityLength = activityLength;
    }
    public Double getLon() {
        return lon;
    }
    public void setLon(Double lon) {
        this.lon = lon;
    }
    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description= description;
    }
    public String getResponse() {
        return response;
    }
    public void setResponse(String response) {
        this.response = response;
    }

    public ActivityObject(String idUser, String activityName, Date activityDate, String activityTypeRecord, String startActivity, String stopActivity, String activityLength, Double lon, Double lat, String description) {
        this.idUser = idUser;
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.activityTypeRecord = activityTypeRecord;
        this.startActivity = startActivity;
        this.stopActivity = stopActivity;
        this.activityLength = activityLength;
        this.lon = lon;
        this.lat = lat;
        this.description = description;
    }

    public ActivityObject() {
        this.coordinates = new ArrayList<>();
    }
}
