package com.example.tva_projekt.dataObjects;

import java.time.LocalDate;
import java.util.Date;

public class ActivityFormObject {
    private String idUser;
    private String activityName;
    private String activityType;
    private String activityTypeRecord;
    private Date activityDate;
    private String activityLength;
    private String description;
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
    public String getActivityLength() {
        return activityLength;
    }
    public void setActivityLength(String activityLength) {
        this.activityLength = activityLength;
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

    public ActivityFormObject(String idUser, String activityName, String activityType, String activityTypeRecord, Date activityDate, String activityLength, String description) {
        this.idUser = idUser;
        this.activityName = activityName;
        this.activityType = activityType;
        this.activityTypeRecord = activityTypeRecord;
        this.activityDate = activityDate;
        this.activityLength = activityLength;
        this.description = description;
    }
}
