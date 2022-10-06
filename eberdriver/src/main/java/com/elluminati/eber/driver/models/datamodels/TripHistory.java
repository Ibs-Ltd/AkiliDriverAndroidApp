package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

public class TripHistory {

    @SerializedName("provider_trip_end_time")
    private String providerTripEndTime;

    @SerializedName("is_trip_cancelled_by_user")
    private int isTripCancelledByUser;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("currencycode")
    private String currencycode;

    @SerializedName("current_provider")
    private String currentProvider;

    @SerializedName("total")
    private double total;

    @SerializedName("is_provider_rated")
    private int isProviderRated;

    @SerializedName("is_trip_cancelled_by_provider")
    private int isTripCancelledByProvider;

    @SerializedName("source_address")
    private String sourceAddress;

    @SerializedName("total_distance")
    private double totalDistance;

    @SerializedName("currency")
    private String currency;

    @SerializedName("total_time")
    private int totalTime;

    @SerializedName("invoice_number")
    private String invoiceNumber;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("is_user_rated")
    private int isUserRated;

    @SerializedName("is_trip_completed")
    private int isTripCompleted;

    @SerializedName("trip_id")
    private String tripId;

    @SerializedName("unique_id")
    private int uniqueId;

    @SerializedName("provider_service_fees")
    private double providerServiceFees;

    @SerializedName("destination_address")
    private String destinationAddress;

    @SerializedName("user_create_time")
    private String userCreateTime;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("picture")
    private String picture;

    @SerializedName("unit")
    private int unit;

    @SerializedName("_id")
    private String id;

    @SerializedName("is_trip_cancelled")
    private int isTripCancelled;

    public int getIsTripCancelled() {
        return isTripCancelled;
    }

    public void setIsTripCancelled(int isTripCancelled) {
        this.isTripCancelled = isTripCancelled;
    }

    public String getProviderTripEndTime() {
        return providerTripEndTime;
    }

    public void setProviderTripEndTime(String providerTripEndTime) {
        this.providerTripEndTime = providerTripEndTime;
    }

    public int getIsTripCancelledByUser() {
        return isTripCancelledByUser;
    }

    public void setIsTripCancelledByUser(int isTripCancelledByUser) {
        this.isTripCancelledByUser = isTripCancelledByUser;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode;
    }

    public String getCurrentProvider() {
        return currentProvider;
    }

    public void setCurrentProvider(String currentProvider) {
        this.currentProvider = currentProvider;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIsProviderRated() {
        return isProviderRated;
    }

    public void setIsProviderRated(int isProviderRated) {
        this.isProviderRated = isProviderRated;
    }

    public int getIsTripCancelledByProvider() {
        return isTripCancelledByProvider;
    }

    public void setIsTripCancelledByProvider(int isTripCancelledByProvider) {
        this.isTripCancelledByProvider = isTripCancelledByProvider;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getIsUserRated() {
        return isUserRated;
    }

    public void setIsUserRated(int isUserRated) {
        this.isUserRated = isUserRated;
    }

    public int getIsTripCompleted() {
        return isTripCompleted;
    }

    public void setIsTripCompleted(int isTripCompleted) {
        this.isTripCompleted = isTripCompleted;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public double getProviderServiceFees() {
        return providerServiceFees;
    }

    public void setProviderServiceFees(double providerServiceFees) {
        this.providerServiceFees = providerServiceFees;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(String userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TripHistory{" + "provider_trip_end_time = '" + providerTripEndTime + '\'' + ",is_trip_cancelled_by_user = '" + isTripCancelledByUser + '\'' + ",timezone = '" + timezone + '\'' + ",currencycode = '" + currencycode + '\'' + ",current_provider = '" + currentProvider + '\'' + ",total = '" + total + '\'' + ",is_provider_rated = '" + isProviderRated + '\'' + ",is_trip_cancelled_by_provider = '" + isTripCancelledByProvider + '\'' + ",source_address = '" + sourceAddress + '\'' + ",total_distance = '" + totalDistance + '\'' + ",currency = '" + currency + '\'' + ",total_time = '" + totalTime + '\'' + ",invoice_number = '" + invoiceNumber + '\'' + ",first_name = '" + firstName + '\'' + ",is_user_rated = '" + isUserRated + '\'' + ",is_trip_completed = '" + isTripCompleted + '\'' + ",trip_id = '" + tripId + '\'' + ",unique_id = '" + uniqueId + '\'' + ",provider_service_fees = '" + providerServiceFees + '\'' + ",destination_address = '" + destinationAddress + '\'' + ",user_create_time = '" + userCreateTime + '\'' + ",last_name = '" + lastName + '\'' + ",picture = '" + picture + '\'' + ",unit = '" + unit + '\'' + ",_id = '" + id + '\'' + "}";
    }
}