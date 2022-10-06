package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.CityType;
import com.elluminati.eber.driver.models.datamodels.Trip;
import com.elluminati.eber.driver.models.datamodels.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TripHistoryDetailResponse {

    @SerializedName("trip")
    private Trip trip;

    @SerializedName("user")
    private User user;

    @SerializedName("success")
    private boolean success;

    @SerializedName("startTripToEndTripLocations")
    private List<List<Double>> startTripToEndTripLocations;

    @SerializedName("message")
    private String message;

    @SerializedName("tripservice")
    private CityType tripService;

    @SerializedName("error_code")
    private int errorCode;

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

    public List<List<Double>> getStartTripToEndTripLocations() {
        return startTripToEndTripLocations;
    }

    public void setStartTripToEndTripLocations(List<List<Double>> startTripToEndTripLocations) {
        this.startTripToEndTripLocations = startTripToEndTripLocations;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    @Override
    public String toString() {
        return "TripHistoryDetailResponse{" + "trip=" + trip + ", user=" + user + ", success=" + success + ", startTripToEndTripLocations=" + startTripToEndTripLocations + ", message='" + message + '\'' + ", tripService=" + tripService + ", errorCode=" + errorCode + '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}