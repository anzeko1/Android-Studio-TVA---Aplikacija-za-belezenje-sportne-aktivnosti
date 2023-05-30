package com.example.tva_projekt.enterActivity;

public class RealmDatabaseItem {
    private int mImageResource;
    private String mActivityName;
    private String mDate;
    private String mDuration;

    public RealmDatabaseItem(int imageResource, String activityName, String date, String duration) {
        mImageResource = imageResource;
        mActivityName = activityName;
        mDate = date;
        mDuration = duration;
    }
    public int getImageResource() {
        return mImageResource;
    }
    public String getActivityName() {
        return mActivityName;
    }
    public String getDate() {
        return mDate;
    }
    public String getDuration() {
        return mDuration;
    }
}
