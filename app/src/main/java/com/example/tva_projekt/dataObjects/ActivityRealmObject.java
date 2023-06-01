package com.example.tva_projekt.dataObjects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
@RealmClass
public class ActivityRealmObject extends RealmObject {
    @PrimaryKey
    long id;
    public String activityRealmId;
    public String activityName;
    public String activityType;
    public String activityTypeRecord;
    public String activityDate;
    public String activityLength;
    public String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getActivityId() {
        return activityRealmId;
    }

    public void setActivityId(String activityRealmId) {
        this.activityRealmId = activityRealmId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityTypeRecord() {
        return activityTypeRecord;
    }

    public void setActivityTypeRecord(String activityTypeRecord) {
        this.activityTypeRecord = activityTypeRecord;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
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
        this.description = description;
    }
}
