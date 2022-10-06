package com.elluminati.eber.driver.models.responsemodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeatMapResponse {


    @SerializedName("pickup_locations")
    private List<PickupLocations> pickupLocations;
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("error_code")
    private int errorCode;

    public List<PickupLocations> getPickupLocations() {
        return pickupLocations;
    }

    public void setPickupLocations(List<PickupLocations> pickupLocations) {
        this.pickupLocations = pickupLocations;
    }

    @Override
    public String toString() {
        return "HResponse{" + "pickup_locations = '" + pickupLocations + '\'' + "}";
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
