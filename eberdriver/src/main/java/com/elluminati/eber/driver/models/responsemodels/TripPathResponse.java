package com.elluminati.eber.driver.models.responsemodels;

import com.elluminati.eber.driver.models.datamodels.Triplocation;
import com.google.gson.annotations.SerializedName;

public class TripPathResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("triplocation")
    private Triplocation triplocation;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Triplocation getTriplocation() {
        return triplocation;
    }

    public void setTriplocation(Triplocation triplocation) {
        this.triplocation = triplocation;
    }

    @Override
    public String toString() {
        return "TripPathResponse{" + "success = '" + success + '\'' + ",triplocation = '" + triplocation + '\'' + "}";
    }
}