package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.CityType;
import com.elluminati.eber.driver.models.datamodels.Trip;
import com.google.gson.annotations.SerializedName;

public class InvoiceResponse {

    @SerializedName("trip")
    private Trip trip;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("tripservice")
    private CityType tripService;

    @SerializedName("error_code")
    private int errorCode;

    public CityType getTripService() {
        return tripService;
    }

    public void setTripService(CityType tripService) {
        this.tripService = tripService;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
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

    @Override
    public String toString() {
        return "InvoiceResponse{" + "trip = '" + trip + '\'' + ",success = '" + success + '\'' + ",message = '" + message + '\'' + ",tripservice = '" + tripService + '\'' + "}";
    }
}