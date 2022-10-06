package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

public class Distance{

    @SerializedName("text")
    private String text;

    @SerializedName("value")
    private int value;

    public String getText(){
        return text;
    }

    public int getValue(){
        return value;
    }
}