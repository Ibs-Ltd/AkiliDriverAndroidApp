package com.elluminati.eber.driver.models.responsemodels;

import com.google.gson.annotations.SerializedName;

public class ProviderLocationResponse {


    @SerializedName("location_unique_id")
    private int locationUniqueId;

    @SerializedName("total_distance")
    private double totalDistance;

    @SerializedName("total_time")
    private int totalTime;

    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("error_code")
    private int errorCode;

    public int getLocationUniqueId() {
        return locationUniqueId;
    }

    public void setLocationUniqueId(int locationUniqueId) {
        this.locationUniqueId = locationUniqueId;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
