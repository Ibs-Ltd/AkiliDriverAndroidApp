package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoutesItem{

    @SerializedName("legs")
    private List<LegsItem> legs;

    public List<LegsItem> getLegs(){
        return legs;
    }
}