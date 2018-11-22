package com.example.meuni.tp7;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.meuni.tp7.models.Bottle;

@Database(entities = {Bottle.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract BottleDao getBottleDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public static void insert(Bottle bottle) {

    }
}
