package com.example.tva_projekt.dataObjects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public long uid;
    @ColumnInfo(name = "idUser")
    public String idUser;
    @ColumnInfo(name = "activityName")
    public String activityName;
    @ColumnInfo(name = "activityType")
    public String activityType;
    @ColumnInfo(name = "activityTypeRecord")
    public String activityTypeRecord;
    @ColumnInfo(name = "activityDate")
    public Date activityDate;
    @ColumnInfo(name = "activityLength")
    public String activityLength;
    @ColumnInfo(name = "description")
    public String description;
}
