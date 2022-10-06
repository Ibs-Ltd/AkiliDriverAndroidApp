package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("full_cityname")
    private String fullCityName;

    @SerializedName("cityname")
    private String cityName;

    @SerializedName("_id")
    private String id = "";

    public String getFullCityName() {
        return fullCityName;
    }

    public void setFullCityName(String fullCityName) {
        this.fullCityName = fullCityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}