package com.elluminati.eber.driver.models.singleton;

import com.elluminati.eber.driver.models.datamodels.VehicleDetail;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;

import java.util.ArrayList;

/**
 * Created by elluminati on 08-07-2016.
 */
public class CurrentTrip {
    private static final CurrentTrip currentTrip = new CurrentTrip();
    private final ArrayList<String> speakingLanguages = new ArrayList<>();
    private final ArrayList<String> genderWiseRequests = new ArrayList<>();
    private String userFirstName;
    private String userLastName;
    private String userProfileImage;
    private String userPhone;
    private double totalDistance;
    private int totalTime;
    private String currency;
    private int unit;
    private int timeLeft;
    private String phoneCountryCode;
    private double providerPartnerWalletAmount;
    private String walletCurrencyCode;
    private double distance;
    private double time;
    private double userRate;
    private AutocompleteSessionToken autocompleteSessionToken;
    private VehicleDetail currentVehicle;
    private int tripType;

    private CurrentTrip() {

    }

    public static CurrentTrip getInstance() {
        return currentTrip;
    }

    public AutocompleteSessionToken getAutocompleteSessionToken() {
        return autocompleteSessionToken;
    }

    public void setAutocompleteSessionToken(AutocompleteSessionToken autocompleteSessionToken) {
        this.autocompleteSessionToken = autocompleteSessionToken;
    }

    public double getUserRate() {
        return userRate;
    }

    public void setUserRate(double userRate) {
        this.userRate = userRate;
    }

    public int getTripType() {
        return tripType;
    }

    public void setTripType(int tripType) {
        this.tripType = tripType;
    }

    public VehicleDetail getCurrentVehicle() {
        return currentVehicle;
    }

    public void setCurrentVehicle(VehicleDetail currentVehicle) {
        this.currentVehicle = currentVehicle;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getWalletCurrencyCode() {
        return walletCurrencyCode;
    }

    public void setWalletCurrencyCode(String walletCurrencyCode) {
        this.walletCurrencyCode = walletCurrencyCode;
    }

    public double getProviderPartnerWalletAmount() {
        return providerPartnerWalletAmount;
    }

    public void setProviderPartnerWalletAmount(double providerPartnerWalletAmount) {
        this.providerPartnerWalletAmount = providerPartnerWalletAmount;
    }

    public ArrayList<String> getSpeakingLanguages() {
        return speakingLanguages;
    }

    public void setSpeakingLanguages(ArrayList<String> languages) {
        this.speakingLanguages.clear();
        this.speakingLanguages.addAll(languages);
    }

    public ArrayList<String> getGenderWiseRequests() {
        return genderWiseRequests;
    }


    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }


    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
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

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }


    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }


    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }


    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }


    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void clearData() {
        this.totalDistance = 0;
        this.totalTime = 0;
    }


    public void clear() {
        userFirstName = "";
        userLastName = "";
        userProfileImage = "";
        userPhone = "";
        totalDistance = 0;
        totalTime = 0;
        currency = "";
        unit = 0;
        timeLeft = 0;
        phoneCountryCode = "";
        speakingLanguages.clear();
        genderWiseRequests.clear();
        currentVehicle = null;
    }
}
