package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.TripHistory;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TripHistoryResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("error_code")
    private int errorCode;

    @SerializedName("trips")
    private List<TripHistory> trips;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<TripHistory> getTrips() {
        return trips;
    }

    public void setTrips(List<TripHistory> trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return "TripHistoryResponse{" + "success = '" + success + '\'' + ",trips = '" + trips + '\'' + "}";
    }
}