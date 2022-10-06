package com.elluminati.eber.driver.roomdata;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Ravi Bhalodi on 24,February,2020 in Elluminati
 */
@Entity
public class Location implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "location_unique_id")
    private String locationUniqueId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocationUniqueId() {
        return locationUniqueId;
    }

    public void setLocationUniqueId(String locationUniqueId) {
        this.locationUniqueId = locationUniqueId;
    }
}
