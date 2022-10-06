package com.elluminati.eber.driver.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.elluminati.eber.driver.BuildConfig;

/**
 * Created by elluminati on 29-03-2016.
 */
public class PreferenceHelper {

    /**
     * Preference Const
     */
    private static final String PREF_NAME = "TaxiAnyTimeAnyWhereProvider";
    private static final PreferenceHelper preferenceHelper = new PreferenceHelper();
    private static SharedPreferences app_preferences;
    public final String IS_DOCUMENTS_EXPIRED = "is_documents_expired";
    private final String PROVIDER_ID = "provider_id";
    private final String EMAIL = "email";
    private final String DEVICE_TOKEN = "device_token";
    private final String SESSION_TOKEN = "session_token";
    private final String CONTACT = "contact";
    private final String COUNTRY_CODE = "country_code";
    private final String IS_PROVIDER_ONLINE = "is_provider_online";
    private final String LOGIN_BY = "login_by";
    private final String SOCIAL_ID = "social_id";
    private final String FIRST_NAME = "first_name";
    private final String LAST_NAME = "last_name";
    private final String BIO = "bio";
    private final String ADDRESS = "address";
    private final String ZIP_CODE = "zip_code";
    private final String PROFILE_PIC = "profile_pic";
    private final String TRIP_ID = "trip_id";
    private final String IS_HAVE_TRIP = "is_have_trip";
    private final String IS_APPROVED = "is_approved";
    private final String IS_SOUND_ON = "is_sound_on";
    private final String IS_PICK_UP_SOUND_ON = "is_pick_up_sound_on";
    private final String IS_ALL_DOCUMENT_UPLOAD = "is_all_document_upload";
    private final String UPDATE_COUNT = "update_count";
    private final String GOOGLE_SERVER_KEY = "google_server_key";
    private final String USER_EMAIL_VERIFICATION = "user_email_verification";
    private final String IS_PROVIDER_SOCIAL_LOGIN = "is_provider_social_login";
    private final String USER_SMS_VERIFICATION = "user_sms_verification";
    private final String CONTACT_US_EMAIL = "contact_us_email";
    private final String MAIN_SCREEN = "main_screen";
    private final String SCREEN_LOCK = "screen_lock";
    private final String IS_PATH_DRAW = "is_path_draw";
    private final String HOT_LINE_APP_ID = "hot_line_app_id";
    private final String HOT_LINE_APP_KEY = "hot_line_app_key";
    private final String IS_PARTNER_APPROVED_BY_ADMIN = "is_partner_approved_by_admin";
    private final String PARTNER_EMAIL = "partner_email";
    private final String PROVIDER_TYPE = "provider_type";
    private final String IS_PUSH_SOUND_ON = "is_push_sound_on";
    private final String MANUFACTURER_DEPENDENCY = "manufacturer_dependency";
    private final String TWILIO_NUMBER = "twilio_number";
    private final String IS_HEAT_MAP_ON = "is_heat_map_on";
    private final String IS_PROVIDER_INITIATE_TRIP = "is_provider_initiate_trip";
    private final String LANGUAGE = "language";
    private final String ADMIN_PHONE = "admin_phone";
    private final String STRIPE_PUBLIC_KEY = "stripe_public_key";
    private final String RATING = "rating";
    private final String GENDER = "gender";
    private final String IS_REQUEST = "is_request";
    private final String UNIQUE_ID_FOR_LOCATION = "unique_id_for_location";
    private final String T_AND_C = "t_and_c";
    private final String POLICY = "policy";
    private final String TWILIO_CALL_MASKING = "twilio_call_masking";
    private final String CURRENCY_CODE = "currency_code";
    private final String REFERRAL_CODE = "referral_code";
    private final String IS_REFERRAL_APPLY = "is_referral_apply";
    private final String IS_HAVE_REFERRAL = "is_have_referral";
    private final String IS_SHOW_ESTIMATION = "is_show_estimation";
    private final String ANDROID_PLACES_AUTOCOMPLETE_KEY = "android_places_autocomplete_key";
    private final String ASK_BACKGROUND_APP_RESTRICTION = "ask_background_app_restriction";
    private final String FIREBASE_USER_TOKEN = "firebase_user_token";
    private final String MINIMUM_PHONE_NUMBER_LENGTH = "minimum_phone_number_length";
    private final String MAXIMUM_PHONE_NUMBER_LENGTH = "maximum_phone_number_length";
    private final String BASE_URL = "base_url";

    private PreferenceHelper() {
    }

    public static PreferenceHelper getInstance(Context context) {
        app_preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferenceHelper;
    }

    public void putDeviceToken(String deviceToken) {
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString(DEVICE_TOKEN, deviceToken);
        editor.commit();
    }

    public String getDeviceToken() {
        return app_preferences.getString(DEVICE_TOKEN, null);
    }

    public void putEmail(String email) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(EMAIL, email);
        edit.commit();
    }

    public void putLanguageCode(String code) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(LANGUAGE, code);
        edit.commit();
    }

    public String getLanguageCode() {
        return app_preferences.getString(LANGUAGE, "");

    }

    public String getEmail() {
        return app_preferences.getString(EMAIL, null);
    }

    public void putProviderId(String providerId) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(PROVIDER_ID, providerId);
        edit.commit();
    }

    public String getProviderId() {
        return app_preferences.getString(PROVIDER_ID, null);
    }

    public void putLoginBy(String loginBy) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(LOGIN_BY, loginBy);
        edit.commit();
    }

    public String getLoginBy() {
        return app_preferences.getString(LOGIN_BY, Const.MANUAL);
    }

    public void putSocialId(String id) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(SOCIAL_ID, id);
        edit.commit();
    }

    public String getSocialId() {
        return app_preferences.getString(SOCIAL_ID, null);
    }

    public void putSessionToken(String sessionToken) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(SESSION_TOKEN, sessionToken);
        edit.commit();
    }

    public void putAdminPhone(String phone) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(ADMIN_PHONE, phone);
        edit.commit();
    }

    public String getAdminPhone() {
        return app_preferences.getString(ADMIN_PHONE, null);
    }

    public String getSessionToken() {
        return app_preferences.getString(SESSION_TOKEN, null);

    }

    public void putContact(String contact) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(CONTACT, contact);
        edit.commit();
    }

    public String getContact() {
        return app_preferences.getString(CONTACT, null);

    }

    public void putIsProviderOnline(int isProviderOnline) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putInt(IS_PROVIDER_ONLINE, isProviderOnline);
        edit.commit();
    }

    public int getIsProviderOnline() {
        return app_preferences.getInt(IS_PROVIDER_ONLINE, 0);
    }


    public void putBio(String bio) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(BIO, bio);
        edit.commit();
    }

    public String getBio() {
        return app_preferences.getString(BIO, null);

    }

    public void putAddress(String address) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(ADDRESS, address);
        edit.commit();
    }

    public String getAddress() {
        return app_preferences.getString(ADDRESS, null);

    }

    public void putZipCode(String zipCode) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(ZIP_CODE, zipCode);
        edit.commit();
    }

    public String getZipCode() {
        return app_preferences.getString(ZIP_CODE, "");

    }

    public void putFirstName(String firstName) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(FIRST_NAME, firstName);
        edit.commit();
    }

    public String getFirstName() {
        return app_preferences.getString(FIRST_NAME, null);

    }

    public void putLastName(String lastName) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(LAST_NAME, lastName);
        edit.commit();
    }

    public String getLastName() {
        return app_preferences.getString(LAST_NAME, null);

    }

    public void putProfilePic(String profilePic) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(PROFILE_PIC, profilePic);
        edit.commit();
    }

    public String getProfilePic() {
        return app_preferences.getString(PROFILE_PIC, null);

    }

    public void putTripId(String TripID) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(TRIP_ID, TripID);
        edit.commit();
    }

    public String getTripId() {
        return app_preferences.getString(TRIP_ID, null);
    }

    public void putIsApproved(int is_approved) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putInt(IS_APPROVED, is_approved);
        edit.commit();
    }

    public int getIsApproved() {
        return app_preferences.getInt(IS_APPROVED, 0);
    }

    public void putAllDocUpload(int is_Upload) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putInt(IS_ALL_DOCUMENT_UPLOAD, is_Upload);
        edit.commit();
    }

    public int getAllDocUpload() {
        return app_preferences.getInt(IS_ALL_DOCUMENT_UPLOAD, 0);
    }

    public void putCountryPhoneCode(String code) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(COUNTRY_CODE, code);
        edit.commit();
    }

    public String getCountryPhoneCode() {
        return app_preferences.getString(COUNTRY_CODE, null);
    }

    public void putIsSoundOn(boolean isSoundOn) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(IS_SOUND_ON, isSoundOn);
        edit.commit();

    }

    public boolean getIsSoundOn() {

        return app_preferences.getBoolean(IS_SOUND_ON, true);
    }

    public void putIsPickUpSoundOn(boolean isSoundOn) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(IS_PICK_UP_SOUND_ON, isSoundOn);
        edit.commit();

    }

    public boolean getIsPickUpSoundOn() {

        return app_preferences.getBoolean(IS_PICK_UP_SOUND_ON, true);
    }

    public void putCheckCountForLocation(int updateCount) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putInt(UPDATE_COUNT, updateCount);
        edit.commit();
    }

    public int getCheckCountForLocation() {

        return app_preferences.getInt(UPDATE_COUNT, 0);
    }

    public void putIsHaveTrip(boolean isHaveTrip) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(IS_HAVE_TRIP, isHaveTrip);
        edit.commit();
    }

    public boolean getIsHaveTrip() {
        return app_preferences.getBoolean(IS_HAVE_TRIP, false);
    }

    public void putGoogleServerKey(String serverKey) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(GOOGLE_SERVER_KEY, serverKey);
        edit.commit();
    }

    public String getGoogleServerKey() {
        return app_preferences.getString(GOOGLE_SERVER_KEY, null);
    }

    public void putGoogleAutoCompleteKey(String serverKey) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(ANDROID_PLACES_AUTOCOMPLETE_KEY, serverKey);
        edit.commit();
    }

    public String getGoogleAutoCompleteKey() {
        return app_preferences.getString(ANDROID_PLACES_AUTOCOMPLETE_KEY, null);
    }

    public void putIsProviderEmailVerification(boolean isEmailVerify) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(USER_EMAIL_VERIFICATION, isEmailVerify);
        edit.commit();

    }

    public boolean getIsProviderEmailVerification() {
        return app_preferences.getBoolean(USER_EMAIL_VERIFICATION, false);
    }

    public void putIsProviderSocialLogin(boolean isEmailVerify) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(IS_PROVIDER_SOCIAL_LOGIN, isEmailVerify);
        edit.commit();

    }

    public boolean getIsProviderSocialLogin() {
        return app_preferences.getBoolean(IS_PROVIDER_SOCIAL_LOGIN, false);
    }

    public void putIsProviderSMSVerification(boolean isSMSVerify) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(USER_SMS_VERIFICATION, isSMSVerify);
        edit.commit();

    }

    public boolean getIsProviderSMSVerification() {
        return app_preferences.getBoolean(USER_SMS_VERIFICATION, false);
    }

    public void putContactUsEmail(String email) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(CONTACT_US_EMAIL, email);
        edit.commit();
    }

    public String getContactUsEmail() {
        return app_preferences.getString(CONTACT_US_EMAIL, null);
    }

    public void putIsMainScreenVisible(boolean isVisible) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(MAIN_SCREEN, isVisible);
        edit.commit();
    }

    public boolean getIsMainScreenVisible() {
        return app_preferences.getBoolean(MAIN_SCREEN, false);
    }

    public void putIsPathDraw(boolean isPthDraw) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(IS_PATH_DRAW, isPthDraw);
        edit.commit();
    }

    public boolean getIsPathDraw() {
        return app_preferences.getBoolean(IS_PATH_DRAW, true);
    }

    public void putHotLineAppId(String appId) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(HOT_LINE_APP_ID, appId);
        edit.commit();
    }

    public String getHotLineAppId() {
        return app_preferences.getString(HOT_LINE_APP_ID, null);
    }

    public void putHotLineAppKey(String appKey) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(HOT_LINE_APP_KEY, appKey);
        edit.commit();
    }

    public String getHotLineAppKey() {
        return app_preferences.getString(HOT_LINE_APP_KEY, null);
    }

    public void putIsPartnerApprovedByAdmin(int approved) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putInt(IS_PARTNER_APPROVED_BY_ADMIN, approved);
        edit.commit();
    }

    public int getIsPartnerApprovedByAdmin() {
        return app_preferences.getInt(IS_PARTNER_APPROVED_BY_ADMIN, 0);
    }

    public void putProviderType(int id) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putInt(PROVIDER_TYPE, id);
        edit.commit();
    }

    public int getProviderType() {
        return app_preferences.getInt(PROVIDER_TYPE, 0);
    }

    public void putPartnerEmail(String email) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(PARTNER_EMAIL, email);
        edit.commit();
    }

    public String getPartnerEmail() {
        return app_preferences.getString(PARTNER_EMAIL, null);
    }

    public void putIsPushNotificationSoundOn(boolean isSoundOn) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(IS_PUSH_SOUND_ON, isSoundOn);
        edit.commit();

    }

    public boolean getIsPushNotificationSoundOn() {

        return app_preferences.getBoolean(IS_PUSH_SOUND_ON, true);
    }

    public void putIsManufacturerDependency(boolean isChecked) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(MANUFACTURER_DEPENDENCY, isChecked);
        edit.commit();

    }

    public boolean getIsManufacturerDependency() {

        return app_preferences.getBoolean(MANUFACTURER_DEPENDENCY, true);
    }

    public void putTwilioNumber(String number) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(TWILIO_NUMBER, number);
        edit.commit();
    }

    public String getTwilioNumber() {
        return app_preferences.getString(TWILIO_NUMBER, "");
    }


    public void putIsHeatMapOn(boolean isHeatMap) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(IS_HEAT_MAP_ON, isHeatMap);
        edit.commit();

    }

    public boolean getIsHeatMapOn() {
        return app_preferences.getBoolean(IS_HEAT_MAP_ON, false);
    }


    public void putIsProviderInitiateTrip(boolean isProviderInitiateTrip) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(IS_PROVIDER_INITIATE_TRIP, isProviderInitiateTrip);
        edit.commit();

    }

    public boolean getIsProviderInitiateTrip() {
        return app_preferences.getBoolean(IS_PROVIDER_INITIATE_TRIP, false);
    }

    public void putIsScreenLock(boolean isLock) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(SCREEN_LOCK, isLock);
        edit.commit();
    }

    public boolean getIsScreenLock() {
        return app_preferences.getBoolean(SCREEN_LOCK, false);
    }

    public void putStripePublicKey(String stripeKey) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(STRIPE_PUBLIC_KEY, stripeKey);
        edit.commit();
    }

    public String getStripePublicKey() {
        return app_preferences.getString(STRIPE_PUBLIC_KEY, null);
    }

    public void putRating(String rate) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(RATING, rate);
        edit.commit();
    }

    public String getRating() {
        return app_preferences.getString(RATING, "");
    }

    public void putGender(String gender) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(GENDER, gender);
        edit.commit();
    }

    public String getGender() {
        return app_preferences.getString(GENDER, "");
    }

    public void putLocationUniqueId(int id) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putInt(UNIQUE_ID_FOR_LOCATION, id);
        edit.commit();
    }

    public int getLocationUniqueId() {
        return app_preferences.getInt(UNIQUE_ID_FOR_LOCATION, 0);
    }


    public void putIsDocumentExpire(boolean isExpire) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(IS_DOCUMENTS_EXPIRED, isExpire);
        edit.commit();
    }

    public boolean getIsDocumentExpire() {
        return app_preferences.getBoolean(IS_DOCUMENTS_EXPIRED, false);
    }

    public void putTermsANdConditions(String tandc) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(T_AND_C, tandc);
        edit.commit();
    }

    public String getTermsANdConditions() {
        return app_preferences.getString(T_AND_C, null);

    }


    public void putPolicy(String policy) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(POLICY, policy);
        edit.commit();
    }

    public String getPolicy() {
        return app_preferences.getString(POLICY, null);

    }

    public void logout() {
        preferenceHelper.putSessionToken(null);
        preferenceHelper.putProviderId(null);
    }

    public void putTwilioCallMaskEnable(boolean isEnable) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(TWILIO_CALL_MASKING, isEnable);
        edit.commit();
    }

    public boolean getTwilioCallMaskEnable() {
        return app_preferences.getBoolean(TWILIO_CALL_MASKING, false);
    }

    public void putCurrencyCode(String code) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(CURRENCY_CODE, code);
        edit.commit();
    }

    public String getCurrencyCode() {
        return app_preferences.getString(CURRENCY_CODE, "INR");

    }

    public void putReferralCode(String referralCode) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(REFERRAL_CODE, referralCode);
        edit.commit();
    }

    public String getReferralCode() {
        return app_preferences.getString(REFERRAL_CODE, "");

    }

    public void putIsApplyReferral(int isApplyReferral) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putInt(IS_REFERRAL_APPLY, isApplyReferral);
        edit.commit();

    }

    public int getIsApplyReferral() {

        return app_preferences.getInt(IS_REFERRAL_APPLY, 0);
    }

    public void putIsHaveReferral(boolean isHaveReferral) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(IS_HAVE_REFERRAL, isHaveReferral);
        edit.commit();

    }

    public boolean getIsHaveReferral() {

        return app_preferences.getBoolean(IS_HAVE_REFERRAL, true);
    }

    public void putIsShowEstimation(boolean isShow) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(IS_SHOW_ESTIMATION, isShow);
        edit.commit();

    }

    public boolean getIsShowEstimation() {
        return app_preferences.getBoolean(IS_SHOW_ESTIMATION, false);
    }

    public void putIsAskBackgroundAppRestriction(boolean isAsk) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(ASK_BACKGROUND_APP_RESTRICTION, isAsk);
        edit.commit();

    }

    public boolean getIsAskBackgroundAppRestriction() {
        return app_preferences.getBoolean(ASK_BACKGROUND_APP_RESTRICTION, false);
    }

    public void putFirebaseUserToken(String firebaseUserToken) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(FIREBASE_USER_TOKEN, firebaseUserToken);
        edit.commit();
    }

    public String getFirebaseUserToken() {
        return app_preferences.getString(FIREBASE_USER_TOKEN, null);
    }

    public void putBaseUrl(String baseUrl) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(BASE_URL, baseUrl);
        edit.commit();
    }

    public String getBaseUrl() {
        return app_preferences.getString(BASE_URL, BuildConfig.BASE_URL);
    }

    public void putMinimumPhoneNumberLength(int length) {
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putInt(MINIMUM_PHONE_NUMBER_LENGTH, length);
        editor.commit();
    }

    public int getMinimumPhoneNumberLength() {
        return app_preferences.getInt(MINIMUM_PHONE_NUMBER_LENGTH, Const.PhoneNumber.MINIMUM_PHONE_NUMBER_LENGTH);
    }

    public void putMaximumPhoneNumberLength(int length) {
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putInt(MAXIMUM_PHONE_NUMBER_LENGTH, length);
        editor.commit();
    }

    public int getMaximumPhoneNumberLength() {
        return app_preferences.getInt(MAXIMUM_PHONE_NUMBER_LENGTH, Const.PhoneNumber.MAXIMUM_PHONE_NUMBER_LENGTH);
    }
}

