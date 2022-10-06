package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

public class DayTime {
    @SerializedName("multiplier")
    private double multiplier;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("start_time")
    private String startTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}