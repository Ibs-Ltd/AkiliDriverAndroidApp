package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

public class EndLocation{

    @SerializedName("lng")
    private double lng;

    @SerializedName("lat")
    private double lat;

    public double getLng(){
        return lng;
    }

    public double getLat(){
        return lat;
    }
}