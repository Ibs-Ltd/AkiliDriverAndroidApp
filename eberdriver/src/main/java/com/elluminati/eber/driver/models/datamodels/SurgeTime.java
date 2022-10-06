package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SurgeTime {


    @SerializedName("is_surge")
    private boolean isSurge;
    @SerializedName("day")
    private int day;
    @SerializedName("day_time")
    private List<DayTime> dayTime;

    public boolean isSurge() {
        return isSurge;
    }

    public void setSurge(boolean surge) {
        isSurge = surge;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<DayTime> getDayTime() {
        return dayTime;
    }

    public void setDayTime(List<DayTime> dayTime) {
        this.dayTime = dayTime;
    }


}