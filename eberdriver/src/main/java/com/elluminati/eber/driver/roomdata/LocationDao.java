package com.elluminati.eber.driver.roomdata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by Ravi Bhalodi on 24,February,2020 in Elluminati
 */
@Dao
public interface LocationDao {

    @Query("SELECT * FROM location")
    List<Location> getAll();

    @Insert
    void insert(Location location);

    @Delete
    void delete(Location location);

    @Update
    void update(Location location);

    @Query("DELETE FROM location")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM location")
    long count();

    @Query("DELETE FROM location WHERE location_unique_id=:uniqueId")
    void deleteLocation(String uniqueId);
}
