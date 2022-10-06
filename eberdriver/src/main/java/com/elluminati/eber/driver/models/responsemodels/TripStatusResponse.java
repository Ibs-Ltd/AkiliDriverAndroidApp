package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.CityType;
import com.elluminati.eber.driver.models.datamodels.Trip;
import com.elluminati.eber.driver.models.datamodels.User;
import com.google.gson.annotations.SerializedName;

public class TripStatusResponse {

    @SerializedName("user")
    private User user;

    @SerializedName("error_code")
    private int errorCode;
    @SerializedName("price_for_waiting_time")
    private double priceForWaitingTime;
    @SerializedName("trip")
    private Trip trip;
    @SerializedName("country_phone_code")
    private String countryPhoneCode;
    @SerializedName("phone")
    private String phone;
    @SerializedName("success")
    private boolean success;
    @SerializedName("waiting_time_start_after_minute")
    private int waitingTimeStartAfterMinute;
    @SerializedName("total_wait_time")
    private int totalWaitTime;
    @SerializedName("map_pin_image_url")
    private String mapPinImageUrl;


    @SerializedName("message")
    private String message;
    @SerializedName("tripservice")
    private CityType tripService;

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

    public double getPriceForWaitingTime() {
        return priceForWaitingTime;
    }

    public void setPriceForWaitingTime(double priceForWaitingTime) {
        this.priceForWaitingTime = priceForWaitingTime;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public void setCountryPhoneCode(String countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getWaitingTimeStartAfterMinute() {
        return waitingTimeStartAfterMinute;
    }

    public void setWaitingTimeStartAfterMinute(int waitingTimeStartAfterMinute) {
        this.waitingTimeStartAfterMinute = waitingTimeStartAfterMinute;
    }

    public int getTotalWaitTime() {
        return totalWaitTime;
    }

    public void setTotalWaitTime(int totalWaitTime) {
        this.totalWaitTime = totalWaitTime;
    }

    public String getMapPinImageUrl() {
        return mapPinImageUrl;
    }

    public void setMapPinImageUrl(String mapPinImageUrl) {
        this.mapPinImageUrl = mapPinImageUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TripStatusResponse{" + "price_for_waiting_time = '" + priceForWaitingTime + '\'' + ",trip = '" + trip + '\'' + ",country_phone_code = '" + countryPhoneCode + '\'' + ",phone = '" + phone + '\'' + ",success = '" + success + '\'' + ",waiting_time_start_after_minute = '" + waitingTimeStartAfterMinute + '\'' + ",total_wait_time = '" + totalWaitTime + '\'' + ",map_pin_image_url = '" + mapPinImageUrl + '\'' + ",message = '" + message + '\'' + "}";
    }

    public User getUser() {
        return user;
    }
}