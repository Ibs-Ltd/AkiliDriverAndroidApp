package com.elluminati.eber.driver.models.responsemodels;

import com.google.gson.annotations.SerializedName;

public class ETAResponse {

    @SerializedName("price_per_unit_time")
    private double pricePerUnitTime;

    @SerializedName("trip_type")
    private String tripType;

    @SerializedName("distance")
    private String distance;

    @SerializedName("success")
    private boolean success;

    @SerializedName("base_price")
    private double basePrice;

    @SerializedName("price_per_unit_distance")
    private String pricePerUnitDistance;

    @SerializedName("estimated_fare")
    private double estimatedFare;

    @SerializedName("is_min_fare_used")
    private int isMinFareUsed;

    @SerializedName("user_miscellaneous_fee")
    private double userMiscellaneousFee;

    @SerializedName("time")
    private double time;

    @SerializedName("message")
    private String message;

    @SerializedName("user_tax_fee")
    private double userTaxFee;

    @SerializedName("error_code")
    private int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public double getPricePerUnitTime() {
        return pricePerUnitTime;
    }

    public void setPricePerUnitTime(double pricePerUnitTime) {
        this.pricePerUnitTime = pricePerUnitTime;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public String getPricePerUnitDistance() {
        return pricePerUnitDistance;
    }

    public void setPricePerUnitDistance(String pricePerUnitDistance) {
        this.pricePerUnitDistance = pricePerUnitDistance;
    }

    public double getEstimatedFare() {
        return estimatedFare;
    }

    public void setEstimatedFare(double estimatedFare) {
        this.estimatedFare = estimatedFare;
    }

    public int getIsMinFareUsed() {
        return isMinFareUsed;
    }

    public void setIsMinFareUsed(int isMinFareUsed) {
        this.isMinFareUsed = isMinFareUsed;
    }

    public double getUserMiscellaneousFee() {
        return userMiscellaneousFee;
    }

    public void setUserMiscellaneousFee(double userMiscellaneousFee) {
        this.userMiscellaneousFee = userMiscellaneousFee;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getUserTaxFee() {
        return userTaxFee;
    }

    public void setUserTaxFee(double userTaxFee) {
        this.userTaxFee = userTaxFee;
    }

    @Override
    public String toString() {
        return "ETAResponse{" + "price_per_unit_time = '" + pricePerUnitTime + '\'' + ",trip_type = '" + tripType + '\'' + ",distance = '" + distance + '\'' + ",success = '" + success + '\'' + ",base_price = '" + basePrice + '\'' + ",price_per_unit_distance = '" + pricePerUnitDistance + '\'' + ",estimated_fare = '" + estimatedFare + '\'' + ",is_min_fare_used = '" + isMinFareUsed + '\'' + ",user_miscellaneous_fee = '" + userMiscellaneousFee + '\'' + ",time = '" + time + '\'' + ",message = '" + message + '\'' + ",user_tax_fee = '" + userTaxFee + '\'' + "}";
    }
}