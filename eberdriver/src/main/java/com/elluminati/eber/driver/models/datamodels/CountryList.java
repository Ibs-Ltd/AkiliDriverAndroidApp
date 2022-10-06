package com.elluminati.eber.driver.models.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CountryList implements Parcelable {
    public static final Creator<CountryList> CREATOR = new Creator<CountryList>() {
        @Override
        public CountryList createFromParcel(Parcel source) {
            return new CountryList(source);
        }

        @Override
        public CountryList[] newArray(int size) {
            return new CountryList[size];
        }
    };
    @SerializedName("country_code")
    private String countryCode;
    @SerializedName("country_flag")
    private String countryFlag;
    @SerializedName("is_referral")
    private boolean isReferral;
    @SerializedName("countryphonecode")
    private String countryPhoneCode;
    @SerializedName("phone_number_min_length")
    private int phoneNumberMinLength;
    @SerializedName("countryname")
    private String countryName;
    @SerializedName("_id")
    private String id;
    @SerializedName("phone_number_length")
    private int phoneNumberLength;
    @SerializedName("flag_url")
    private String flagUrl;

    public CountryList() {
    }

    protected CountryList(Parcel in) {
        this.countryCode = in.readString();
        this.countryFlag = in.readString();
        this.isReferral = in.readByte() != 0;
        this.countryPhoneCode = in.readString();
        this.phoneNumberMinLength = in.readInt();
        this.countryName = in.readString();
        this.id = in.readString();
        this.phoneNumberLength = in.readInt();
        this.flagUrl = in.readString();
    }

    public boolean isReferral() {
        return isReferral;
    }

    public void setReferral(boolean referral) {
        isReferral = referral;
    }

    public String getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public void setCountryPhoneCode(String countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }

    public int getPhoneNumberMinLength() {
        return phoneNumberMinLength;
    }

    public void setPhoneNumberMinLength(int phoneNumberMinLength) {
        this.phoneNumberMinLength = phoneNumberMinLength;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    @Override
    public String toString() {
        return "Country{" + "countryPhoneCode = '" + countryPhoneCode + '\'' + ",phone_number_min_length = '" + phoneNumberMinLength + '\'' + ",countryName = '" + countryName + '\'' + ",_id = '" + id + '\'' + ",phone_number_length = '" + phoneNumberLength + '\'' + ",flag_url = '" + flagUrl + '\'' + "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.countryCode);
        dest.writeString(this.countryFlag);
        dest.writeByte(this.isReferral ? (byte) 1 : (byte) 0);
        dest.writeString(this.countryPhoneCode);
        dest.writeInt(this.phoneNumberMinLength);
        dest.writeString(this.countryName);
        dest.writeString(this.id);
        dest.writeInt(this.phoneNumberLength);
        dest.writeString(this.flagUrl);
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }
}