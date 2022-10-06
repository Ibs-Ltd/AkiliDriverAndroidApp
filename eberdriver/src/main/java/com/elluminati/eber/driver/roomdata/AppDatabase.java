package com.elluminati.eber.driver.roomdata;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by Ravi Bhalodi on 24,February,2020 in Elluminati
 */
@Database(entities = {Location.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}
