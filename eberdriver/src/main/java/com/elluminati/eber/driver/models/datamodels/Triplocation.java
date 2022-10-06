package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Triplocation {

    @SerializedName("trip_unique_id")
    private int tripUniqueId;

    @SerializedName("endTripTime")
    private String endTripTime;

    @SerializedName("endTripLocation")
    private List<Double> endTripLocation;

    @SerializedName("providerStartTime")
    private String providerStartTime;

    @SerializedName("providerStartToStartTripLocations")
    private List<List<Double>> providerStartToStartTripLocations;

    @SerializedName("startTripLocation")
    private List<Double> startTripLocation;

    @SerializedName("startTripTime")
    private String startTripTime;

    @SerializedName("startTripToEndTripLocations")
    private List<List<Double>> startTripToEndTripLocations;

    @SerializedName("googlePickUpLocationToDestinationLocation")
    private String googlePickUpLocationToDestinationLocation;
    @SerializedName("googlePathStartLocationToPickUpLocation")
    private String googlePathStartLocationToPickUpLocation;
    @SerializedName("tripID")
    private String tripID;
    @SerializedName("_id")
    private String id;
    @SerializedName("providerStartLocation")
    private List<Double> providerStartLocation;

    public String getGooglePathStartLocationToPickUpLocation() {
        return googlePathStartLocationToPickUpLocation;
    }

    public void setGooglePathStartLocationToPickUpLocation(String googlePathStartLocationToPickUpLocation) {
        this.googlePathStartLocationToPickUpLocation = googlePathStartLocationToPickUpLocation;
    }

    public int getTripUniqueId() {
        return tripUniqueId;
    }

    public void setTripUniqueId(int tripUniqueId) {
        this.tripUniqueId = tripUniqueId;
    }

    public String getEndTripTime() {
        return endTripTime;
    }

    public void setEndTripTime(String endTripTime) {
        this.endTripTime = endTripTime;
    }

    public List<Double> getEndTripLocation() {
        return endTripLocation;
    }

    public void setEndTripLocation(List<Double> endTripLocation) {
        this.endTripLocation = endTripLocation;
    }

    public String getProviderStartTime() {
        return providerStartTime;
    }

    public void setProviderStartTime(String providerStartTime) {
        this.providerStartTime = providerStartTime;
    }

    public List<List<Double>> getProviderStartToStartTripLocations() {
        return providerStartToStartTripLocations;
    }

    public void setProviderStartToStartTripLocations(List<List<Double>> providerStartToStartTripLocations) {
        this.providerStartToStartTripLocations = providerStartToStartTripLocations;
    }

    public List<Double> getStartTripLocation() {
        return startTripLocation;
    }

    public void setStartTripLocation(List<Double> startTripLocation) {
        this.startTripLocation = startTripLocation;
    }

    public String getStartTripTime() {
        return startTripTime;
    }

    public void setStartTripTime(String startTripTime) {
        this.startTripTime = startTripTime;
    }

    public List<List<Double>> getStartTripToEndTripLocations() {
        return startTripToEndTripLocations;
    }

    public void setStartTripToEndTripLocations(List<List<Double>> startTripToEndTripLocations) {
        this.startTripToEndTripLocations = startTripToEndTripLocations;
    }

    public String getGooglePickUpLocationToDestinationLocation() {
        return googlePickUpLocationToDestinationLocation;
    }

    public void setGooglePickUpLocationToDestinationLocation(String googlePickUpLocationToDestinationLocation) {
        this.googlePickUpLocationToDestinationLocation = googlePickUpLocationToDestinationLocation;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Double> getProviderStartLocation() {
        return providerStartLocation;
    }

    public void setProviderStartLocation(List<Double> providerStartLocation) {
        this.providerStartLocation = providerStartLocation;
    }

    @Override
    public String toString() {
        return "Triplocation{" + "trip_unique_id = '" + tripUniqueId + '\'' + ",endTripTime = '" + endTripTime + '\'' + ",endTripLocation = '" + endTripLocation + '\'' + ",providerStartTime = '" + providerStartTime + '\'' + ",providerStartToStartTripLocations = '" + providerStartToStartTripLocations + '\'' + ",startTripLocation = '" + startTripLocation + '\'' + ",startTripTime = '" + startTripTime + '\'' + ",startTripToEndTripLocations = '" + startTripToEndTripLocations + '\'' + ",googlePickUpLocationToDestinationLocation = '" + googlePickUpLocationToDestinationLocation + '\'' + ",tripID = '" + tripID + '\'' + ",_id = '" + id + '\'' + ",providerStartLocation = '" + providerStartLocation + '\'' + "}";
    }
}