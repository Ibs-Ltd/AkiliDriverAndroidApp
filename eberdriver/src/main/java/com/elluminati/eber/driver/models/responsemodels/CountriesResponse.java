package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.Country;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountriesResponse {


    @SerializedName("country_list")
    private List<Country> country;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;


    @SerializedName("error_code")
    private int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
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
        return "CountriesResponse{" + "country = '" + country + '\'' + ",success = '" + success + '\'' + ",message = '" + message + '\'' + "}";
    }
}