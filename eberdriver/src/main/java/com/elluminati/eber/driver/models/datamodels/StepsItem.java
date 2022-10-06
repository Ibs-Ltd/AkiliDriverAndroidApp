package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

public class StepsItem{

    @SerializedName("polyline")
    private Polyline polyline;

    public Polyline getPolyline(){
        return polyline;
    }
}