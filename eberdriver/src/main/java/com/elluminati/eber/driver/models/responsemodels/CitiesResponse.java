package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.City;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CitiesResponse {

    @SerializedName("city")
    private List<City> cities;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
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
        return "CitiesResponse{" + "cities = '" + cities + '\'' + ",success = '" + success + '\'' + ",message = '" + message + '\'' + "}";
    }
}