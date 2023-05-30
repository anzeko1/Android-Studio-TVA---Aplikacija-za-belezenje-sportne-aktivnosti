package com.example.tva_projekt.enterActivity.roomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tva_projekt.dataObjects.ActivityRoomObject;

import java.util.List;

@Dao
public interface ActivityFormDao {
    @Query("SELECT * FROM ActivityRoomObject")
    List<ActivityRoomObject> getAll();

    @Insert
    void insertActivity(ActivityRoomObject... activityRoomObjects);

    @Delete
    void delete(ActivityRoomObject activityRoomObject);
}
