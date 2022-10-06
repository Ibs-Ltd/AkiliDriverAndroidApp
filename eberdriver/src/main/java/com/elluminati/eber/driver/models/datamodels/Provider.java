package com.elluminati.eber.driver.models.datamodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Provider {

    @SerializedName("provider_type")
    private int providerType;

    @SerializedName("provider_type_id")
    private Object providerTypeId;

    @SerializedName("is_partner_approved_by_admin")
    private int isPartnerApprovedByAdmin;

    @SerializedName("country")
    private String country;

    @SerializedName("total_request")
    private int totalRequest;

    @SerializedName("app_version")
    private String appVersion;

    @SerializedName("received_trip_from_gender")
    private List<Object> receivedTripFromGender;

    @SerializedName("is_trip")
    private List<Object> isTrip;

    @SerializedName("bio")
    private String bio;

    @SerializedName("device_type")
    private String deviceType;

    @SerializedName("is_vehicle_document_uploaded")
    private boolean isVehicleDocumentUploaded;

    @SerializedName("last_transfered_date")
    private String lastTransferedDate;

    @SerializedName("location_updated_time")
    private String locationUpdatedTime;

    @SerializedName("password")
    private String password;

    @SerializedName("cancelled_request")
    private int cancelledRequest;

    @SerializedName("country_phone_code")
    private String countryPhoneCode;

    @SerializedName("bank_id")
    private String bankId;

    @SerializedName("__v")
    private int V;

    @SerializedName("device_unique_code")
    private String deviceUniqueCode;

    @SerializedName("is_use_google_distance")
    private boolean isUseGoogleDistance;

    @SerializedName("start_online_time")
    private String startOnlineTime;

    @SerializedName("vehicle_detail")
    private List<VehicleDetail> vehicleDetail;

    @SerializedName("unique_id")
    private int uniqueId;

    @SerializedName("providerLocation")
    private List<Double> providerLocation;

    @SerializedName("last_transferred_date")
    private String lastTransferredDate;

    @SerializedName("bearing")
    private double bearing;

    @SerializedName("cityid")
    private String cityid;

    @SerializedName("zipcode")
    private String zipcode;

    @SerializedName("rate_count")
    private int rateCount;

    @SerializedName("phone")
    private String phone;

    @SerializedName("rejected_request")
    private int rejectedRequest;

    @SerializedName("_id")
    private String id;

    @SerializedName("country_id")
    private String countryId;

    @SerializedName("gender")
    private String gender;

    @SerializedName("city")
    private String city;

    @SerializedName("device_timezone")
    private String deviceTimezone;

    @SerializedName("is_documents_expired")
    private boolean isDocumentsExpired;

    @SerializedName("is_document_uploaded")
    private int isDocumentUploaded;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("providerPreviousLocation")
    private List<Double> providerPreviousLocation;

    @SerializedName("wallet_currency_code")
    private String walletCurrencyCode;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("rate")
    private double rate;

    @SerializedName("social_unique_id")
    private String socialUniqueId;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("email")
    private String email;

    @SerializedName("car_model")
    private String carModel;

    @SerializedName("is_active")
    private int isActive;

    @SerializedName("address")
    private String address;

    @SerializedName("wallet")
    private double wallet;

    @SerializedName("languages")
    private List<String> languages;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("completed_request")
    private int completedRequest;

    @SerializedName("picture")
    private String picture;

    @SerializedName("is_available")
    private int isAvailable;

    @SerializedName("token")
    private String token;

    @SerializedName("service_type")
    private String serviceType;

    @SerializedName("account_id")
    private String accountId;

    @SerializedName("accepted_request")
    private int acceptedRequest;

    @SerializedName("admintypeid")
    private String admintypeid;

    @SerializedName("device_token")
    private String deviceToken;

    @SerializedName("car_number")
    private String carNumber;

    @SerializedName("is_approved")
    private int isApproved;

    @SerializedName("login_by")
    private String loginBy;

    public int getProviderType() {
        return providerType;
    }

    public void setProviderType(int providerType) {
        this.providerType = providerType;
    }

    public Object getProviderTypeId() {
        return providerTypeId;
    }

    public void setProviderTypeId(Object providerTypeId) {
        this.providerTypeId = providerTypeId;
    }

    public int getIsPartnerApprovedByAdmin() {
        return isPartnerApprovedByAdmin;
    }

    public void setIsPartnerApprovedByAdmin(int isPartnerApprovedByAdmin) {
        this.isPartnerApprovedByAdmin = isPartnerApprovedByAdmin;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTotalRequest() {
        return totalRequest;
    }

    public void setTotalRequest(int totalRequest) {
        this.totalRequest = totalRequest;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public List<Object> getReceivedTripFromGender() {
        return receivedTripFromGender;
    }

    public void setReceivedTripFromGender(List<Object> receivedTripFromGender) {
        this.receivedTripFromGender = receivedTripFromGender;
    }

    public List<Object> getIsTrip() {
        return isTrip;
    }

    public void setIsTrip(List<Object> isTrip) {
        this.isTrip = isTrip;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public boolean isIsVehicleDocumentUploaded() {
        return isVehicleDocumentUploaded;
    }

    public void setIsVehicleDocumentUploaded(boolean isVehicleDocumentUploaded) {
        this.isVehicleDocumentUploaded = isVehicleDocumentUploaded;
    }

    public String getLastTransferedDate() {
        return lastTransferedDate;
    }

    public void setLastTransferedDate(String lastTransferedDate) {
        this.lastTransferedDate = lastTransferedDate;
    }

    public String getLocationUpdatedTime() {
        return locationUpdatedTime;
    }

    public void setLocationUpdatedTime(String locationUpdatedTime) {
        this.locationUpdatedTime = locationUpdatedTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCancelledRequest() {
        return cancelledRequest;
    }

    public void setCancelledRequest(int cancelledRequest) {
        this.cancelledRequest = cancelledRequest;
    }

    public String getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public void setCountryPhoneCode(String countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public int getV() {
        return V;
    }

    public void setV(int V) {
        this.V = V;
    }

    public String getDeviceUniqueCode() {
        return deviceUniqueCode;
    }

    public void setDeviceUniqueCode(String deviceUniqueCode) {
        this.deviceUniqueCode = deviceUniqueCode;
    }

    public boolean isIsUseGoogleDistance() {
        return isUseGoogleDistance;
    }

    public void setIsUseGoogleDistance(boolean isUseGoogleDistance) {
        this.isUseGoogleDistance = isUseGoogleDistance;
    }

    public String getStartOnlineTime() {
        return startOnlineTime;
    }

    public void setStartOnlineTime(String startOnlineTime) {
        this.startOnlineTime = startOnlineTime;
    }

    public List<VehicleDetail> getVehicleDetail() {
        return vehicleDetail;
    }

    public void setVehicleDetail(List<VehicleDetail> vehicleDetail) {
        this.vehicleDetail = vehicleDetail;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public List<Double> getProviderLocation() {
        return providerLocation;
    }

    public void setProviderLocation(List<Double> providerLocation) {
        this.providerLocation = providerLocation;
    }

    public String getLastTransferredDate() {
        return lastTransferredDate;
    }

    public void setLastTransferredDate(String lastTransferredDate) {
        this.lastTransferredDate = lastTransferredDate;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public int getRateCount() {
        return rateCount;
    }

    public void setRateCount(int rateCount) {
        this.rateCount = rateCount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRejectedRequest() {
        return rejectedRequest;
    }

    public void setRejectedRequest(int rejectedRequest) {
        this.rejectedRequest = rejectedRequest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDeviceTimezone() {
        return deviceTimezone;
    }

    public void setDeviceTimezone(String deviceTimezone) {
        this.deviceTimezone = deviceTimezone;
    }

    public boolean isIsDocumentsExpired() {
        return isDocumentsExpired;
    }

    public void setIsDocumentsExpired(boolean isDocumentsExpired) {
        this.isDocumentsExpired = isDocumentsExpired;
    }

    public int getIsDocumentUploaded() {
        return isDocumentUploaded;
    }

    public void setIsDocumentUploaded(int isDocumentUploaded) {
        this.isDocumentUploaded = isDocumentUploaded;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Double> getProviderPreviousLocation() {
        return providerPreviousLocation;
    }

    public void setProviderPreviousLocation(List<Double> providerPreviousLocation) {
        this.providerPreviousLocation = providerPreviousLocation;
    }

    public String getWalletCurrencyCode() {
        return walletCurrencyCode;
    }

    public void setWalletCurrencyCode(String walletCurrencyCode) {
        this.walletCurrencyCode = walletCurrencyCode;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getSocialUniqueId() {
        return socialUniqueId;
    }

    public void setSocialUniqueId(String socialUniqueId) {
        this.socialUniqueId = socialUniqueId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getCompletedRequest() {
        return completedRequest;
    }

    public void setCompletedRequest(int completedRequest) {
        this.completedRequest = completedRequest;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getAcceptedRequest() {
        return acceptedRequest;
    }

    public void setAcceptedRequest(int acceptedRequest) {
        this.acceptedRequest = acceptedRequest;
    }

    public String getAdmintypeid() {
        return admintypeid;
    }

    public void setAdmintypeid(String admintypeid) {
        this.admintypeid = admintypeid;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public int getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(int isApproved) {
        this.isApproved = isApproved;
    }

    public String getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(String loginBy) {
        this.loginBy = loginBy;
    }

    @Override
    public String toString() {
        return "Provider{" + "provider_type = '" + providerType + '\'' + ",provider_type_id = '" + providerTypeId + '\'' + ",is_partner_approved_by_admin = '" + isPartnerApprovedByAdmin + '\'' + ",country = '" + country + '\'' + ",total_request = '" + totalRequest + '\'' + ",app_version = '" + appVersion + '\'' + ",received_trip_from_gender = '" + receivedTripFromGender + '\'' + ",is_trip = '" + isTrip + '\'' + ",bio = '" + bio + '\'' + ",device_type = '" + deviceType + '\'' + ",is_vehicle_document_uploaded = '" + isVehicleDocumentUploaded + '\'' + ",last_transfered_date = '" + lastTransferedDate + '\'' + ",location_updated_time = '" + locationUpdatedTime + '\'' + ",password = '" + password + '\'' + ",cancelled_request = '" + cancelledRequest + '\'' + ",country_phone_code = '" + countryPhoneCode + '\'' + ",bank_id = '" + bankId + '\'' + ",__v = '" + V + '\'' + ",device_unique_code = '" + deviceUniqueCode + '\'' + ",is_use_google_distance = '" + isUseGoogleDistance + '\'' + ",start_online_time = '" + startOnlineTime + '\'' + ",vehicle_detail = '" + vehicleDetail + '\'' + ",unique_id = '" + uniqueId + '\'' + ",providerLocation = '" + providerLocation + '\'' + ",last_transferred_date = '" + lastTransferredDate + '\'' + ",bearing = '" + bearing + '\'' + ",cityid = '" + cityid + '\'' + ",zipcode = '" + zipcode + '\'' + ",rate_count = '" + rateCount + '\'' + ",phone = '" + phone + '\'' + ",rejected_request = '" + rejectedRequest + '\'' + ",_id = '" + id + '\'' + ",country_id = '" + countryId + '\'' + ",gender = '" + gender + '\'' + ",city = '" + city + '\'' + ",device_timezone = '" + deviceTimezone + '\'' + ",is_documents_expired = '" + isDocumentsExpired + '\'' + ",is_document_uploaded = '" + isDocumentUploaded + '\'' + ",created_at = '" + createdAt + '\'' + ",providerPreviousLocation = '" + providerPreviousLocation + '\'' + ",wallet_currency_code = '" + walletCurrencyCode + '\'' + ",updated_at = '" + updatedAt + '\'' + ",rate = '" + rate + '\'' + ",social_unique_id = '" + socialUniqueId + '\'' + ",first_name = '" + firstName + '\'' + ",email = '" + email + '\'' + ",car_model = '" + carModel + '\'' + ",is_active = '" + isActive + '\'' + ",address = '" + address + '\'' + ",wallet = '" + wallet + '\'' + ",languages = '" + languages + '\'' + ",last_name = '" + lastName + '\'' + ",completed_request = '" + completedRequest + '\'' + ",picture = '" + picture + '\'' + ",is_available = '" + isAvailable + '\'' + ",token = '" + token + '\'' + ",service_type = '" + serviceType + '\'' + ",account_id = '" + accountId + '\'' + ",accepted_request = '" + acceptedRequest + '\'' + ",admintypeid = '" + admintypeid + '\'' + ",device_token = '" + deviceToken + '\'' + ",car_number = '" + carNumber + '\'' + ",is_approved = '" + isApproved + '\'' + ",login_by = '" + loginBy + '\'' + "}";
    }
}