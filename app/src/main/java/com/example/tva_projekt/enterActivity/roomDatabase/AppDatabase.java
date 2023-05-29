package com.example.tva_projekt.enterActivity.roomDatabase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.tva_projekt.dataObjects.ActivityRoomObject;
import com.example.tva_projekt.otherClasses.DateConverter;

@Database(entities = {ActivityRoomObject.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ActivityFormDao activityFormDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {
        context.getApplicationContext().deleteDatabase("DB");
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
/*
@Database(entities = {ActivityFormObject.class}, version = 1)
public abstract AppDatabase extends RoomDatabase{
public abstract UserDao userDao ();

private static AppDatabase INSTANCE;

public static AppDatabase getDbInstance(Context context) {
        context.getApplicationContext().deleteDatabase("DB");
        if(INSTANCE == null) {
        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DB")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build();
        }
        return INSTANCE;
        }
}
*/