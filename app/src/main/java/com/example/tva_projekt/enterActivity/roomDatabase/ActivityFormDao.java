package com.example.tva_projekt.enterActivity.roomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tva_projekt.dataObjects.User;

import java.util.List;

@Dao
public interface ActivityFormDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    void insertActivity(User... users);

    @Delete
    void delete(User user);
}
