package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Country {
    @SerializedName("city_list")
    private List<City> cities;
    @SerializedName("is_referral")
    private boolean isReferral;
    @SerializedName("countryphonecode")
    private String countryphonecode;
    @SerializedName("phone_number_min_length")
    private int phoneNumberMinLength;
    @SerializedName("countryname")
    private String countryname;
    @SerializedName("_id")
    private String id = "";
    @SerializedName("phone_number_length")
    private int phoneNumberLength;
    @SerializedName("flag_url")
    private String flagUrl;
    @SerializedName("alpha2")
    private String countryCode;


    public boolean isReferral() {
        return isReferral;
    }

    public void setReferral(boolean referral) {
        isReferral = referral;
    }

    public String getCountryphonecode() {
        return countryphonecode;
    }

    public void setCountryphonecode(String countryphonecode) {
        this.countryphonecode = countryphonecode;
    }

    public int getPhoneNumberMinLength() {
        return phoneNumberMinLength;
    }

    public void setPhoneNumberMinLength(int phoneNumberMinLength) {
        this.phoneNumberMinLength = phoneNumberMinLength;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPhoneNumberLength() {
        return phoneNumberLength;
    }

    public void setPhoneNumberLength(int phoneNumberLength) {
        this.phoneNumberLength = phoneNumberLength;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "Country{" +
                "cities=" + cities +
                ", isReferral=" + isReferral +
                ", countryphonecode='" + countryphonecode + '\'' +
                ", phoneNumberMinLength=" + phoneNumberMinLength +
                ", countryname='" + countryname + '\'' +
                ", id='" + id + '\'' +
                ", phoneNumberLength=" + phoneNumberLength +
                ", flagUrl='" + flagUrl + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}