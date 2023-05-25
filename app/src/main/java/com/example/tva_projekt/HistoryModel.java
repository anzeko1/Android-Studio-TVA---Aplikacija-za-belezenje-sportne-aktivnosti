package com.example.tva_projekt;

public class HistoryModel {
    String activityName;
    String activityDuration;
    String activityDate;
    int image;


    public HistoryModel(String activityName, String activityDuration, String activityDate, int image) {
        this.activityName = activityName;
        this.activityDuration = activityDuration;
        this.activityDate = activityDate;
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

    public int getImage() {
        return image;
    }
}
