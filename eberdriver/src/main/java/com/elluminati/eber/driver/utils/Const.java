package com.elluminati.eber.driver.utils;

import static com.elluminati.eber.driver.utils.ServerConfig.BASE_URL;

import android.content.ComponentName;
import android.content.Intent;

import com.elluminati.eber.driver.BuildConfig;

/**
 * Created by elluminati on 29-03-2016.
 */
public class Const {

    public static final int PUSH_NEW_TRIP_NOTIFICATION_ID = 2667;

    /***
     * Google url
     */
    public static final String GOOGLE_API_URL = BASE_URL + "gmapsapi/maps/";

    /**
     * location displacement in meter
     */
    public static final float DISPLACEMENT = 5; //meter

    /**
     * speed measure
     */
    public static final float KM_COEFFICIENT = 3.6f; /// meter/second to km/h

    /**
     * Default font scale for used when app font scale change
     */
    public static final float DEFAULT_FONT_SCALE = 1.0f;

    /**
     * Pickup Alert sound distance
     */
    public static final int PICKUP_THRESHOLD = 300;//miter

    /**
     * Timer Scheduled in Second for heat map
     */
    public static final long HEAT_MAP_SCHEDULED_SECOND = 30; //seconds

    /**
     * set LatLngBounce padding
     */
    public static final int MAP_BOUNDS = 180;

    /**
     * general const
     */
    public static final int SHOW_BOTH_ADDRESS = 0;
    public static final int SHOW_PICK_UP_ADDRESS = 1;
    public static final int SHOW_DESTINATION_ADDRESS = 2;
    public static final String HTTP_ERROR_CODE_PREFIX = "http_error_";
    public static final int PUSH_NOTIFICATION_ID = 2688;
    public static final int FOREGROUND_NOTIFICATION_ID = 2687;
    public static final int PROVIDER_UNIQUE_NUMBER = 11;
    public static final int USER_UNIQUE_NUMBER = 10;
    public static final int ERROR_CODE_YOUR_TRIP_PAYMENT_IS_PENDING = 464;
    public static final String UNIT_PREFIX = "unit_code_";
    public static final int TRUE = 1;
    public static final int FALSE = 0;
    public static final int SERVICE_NOTIFICATION_ID = 2017;
    public static final int PROVIDER = 0;
    public static final int ERROR_CODE_INVALID_TOKEN = 451;
    public static final int APPLE = 2;
    public static final int CASH = 1;
    public static final int CARD = 0;
    public static final String SOCIAL_FACEBOOK = "facebook";
    public static final String SOCIAL_GOOGLE = "google";
    public static final String MANUAL = "manual";
    public static final String DEVICE_TYPE_ANDROID = "android";
    public static final String COUNTRY_CODE = "country-code";
    public static final String IS_CLICK_INSIDE_DRAWER = "is_click_inside_drawer";
    public static final String ERROR_CODE_PREFIX = "error_code_";
    public static final String MESSAGE_CODE_PREFIX = "message_code_";
    public static final String PUSH_MESSAGE_PREFIX = "PUSH_MESSAGE_";
    public static final String DATE_TIME_FORMAT_WEB = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_MONTH = "MMMM yyyy";
    public static final String DATE_FORMAT_EARNING = "dd MMM yyyy";
    public static final String DAY = "d";
    public static final String TIME_FORMAT_AM = "h:mm a";
    public static final String UTF_8 = "utf-8";
    public static final String SLASH = "/";
    public static final String PERCENTAGE = "%";
    public static final String STRING = "string";
    public static final int ACTION_SETTINGS = 2;
    public static final String PIC_URI = "picUri";
    public static final int CODE_USER_CANCEL_TRIP = 807;
    public static final String ADD_VEHICLE = "add_vehicle";
    public static final String IS_ADD_VEHICLE = "is_add_vehicle";
    public static final String VEHICLE_ID = "vehicle_id";
    public static final int ERROR_PROVIDER_DETAIL_NOT_FOUND = 479;
    public static final String BUNDLE = "BUNDLE";

    /**
     * Permission requestCode
     */
    public static final int REQUEST_ADD_CARD = 33;
    public static final int REQUEST_ADD_VEHICLE = 37;
    public static final int PERMISSION_FOR_LOCATION = 2;
    public static final int PERMISSION_FOR_CAMERA_AND_EXTERNAL_STORAGE = 3;
    public static final int REQUEST_UPDATE_APP = 9;
    public static final int REQUEST_PAYU = 334;

    /**
     * Broadcast Action
     */
    public static final String NETWORK_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String GPS_ACTION = "android.location.PROVIDERS_CHANGED";
    public static final String ACTION_DECLINE_PROVIDER = "eber.provider.PROVIDER_DECLINE";
    public static final String ACTION_APPROVED_PROVIDER = "eber.provider.PROVIDER_APPROVED";
    public static final String ACTION_NEW_TRIP = "eber.provider" + ".PROVIDER_HAVE_NEW_TRIP";
    public static final String ACTION_ACCEPT_NOTIFICATION = "eber.provider" + ".ACCEPT_NOTIFICATION";
    public static final String ACTION_CANCEL_NOTIFICATION = "eber.provider" + ".CANCEL_NOTIFICATION";
    public static final String ACTION_CANCEL_TRIP = "eber.provider" + ".USER_CANCEL_TRIP";
    public static final String ACTION_DESTINATION_UPDATE = "eber.provider" + ".USER_DESTINATION_UPDATE";
    public static final String ACTION_PAYMENT_CASH = "eber.provider.PAYMENT_CASH";
    public static final String ACTION_PAYMENT_CARD = "eber.provider.PAYMENT_CARD";
    public static final String ACTION_PROVIDER_TRIP_END = "eber.provider.PROVIDER_TRIP_END";
    public static final String ACTION_TRIP_ACCEPTED_BY_ANOTHER_PROVIDER = "eber.provider" + ".TRIP_ACCEPTED_BY_ANOTHER_PROVIDER";
    public static final String ACTION_PROVIDER_OFFLINE = "android.provider.Telephony" + ".ACTION_PROVIDER_OFFLINE";
    public static final String ACTION_TRIP_CANCEL_BY_ADMIN = "eber.client" + ".ACTION_TRIP_CANCEL_BY_ADMIN";
    public static final Intent[] POWERMANAGER_INTENTS = {new Intent().setComponent(new ComponentName("com.miui.securitycenter", "com.miui" + ".permcenter.autostart.AutoStartManagementActivity")), new Intent().setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv" + ".android.letvsafe.AutobootManageActivity")), new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei" + ".systemmanager.startupmgr.ui.StartupNormalAppListActivity")), new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei" + ".systemmanager.optimize.process.ProtectActivity")), new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei" + ".systemmanager.appcontrol.activity.StartupAppControlActivity")), new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros" + ".safecenter.permission.startup.StartupAppListActivity")), new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros" + ".safecenter.startupapp.StartupAppListActivity")), new Intent().setComponent(new ComponentName("com.oppo.safe", "com.oppo.safe" + ".permission.startup.StartupAppListActivity")), new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui" + ".phoneoptimize.AddWhiteListActivity")), new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui" + ".phoneoptimize.BgStartUpManager")), new Intent().setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo" + ".permissionmanager.activity.BgStartUpManagerActivity")), new Intent().setComponent(new ComponentName("com.samsung.android.lool", "com.samsung" + ".android.sm.ui.battery.BatteryActivity")), new Intent().setComponent(new ComponentName("com.htc.pitroad", "com.htc.pitroad" + ".landingpage.activity.LandingPageActivity")), new Intent().setComponent(new ComponentName("com.asus.mobilemanager", "com.asus" + ".mobilemanager.MainActivity")), new Intent().setComponent(new ComponentName("com.transsion.phonemanager", "com.itel" + ".autobootmanager.activity.AutoBootMgrActivity"))};

    /**
     * Image server URL
     */
    public static String IMAGE_BASE_URL = BASE_URL;

    /**
     * service parameters
     */
    public interface Params {
        String EMAIL = "email";
        String PASSWORD = "password";
        String FIRST_NAME = "first_name";
        String LAST_NAME = "last_name";
        String PHONE = "phone";
        String DEVICE_TOKEN = "device_token";
        String DEVICE_TYPE = "device_type";
        String BIO = "bio";
        String TYPE = "type";
        String ADDRESS = "address";
        String COUNTRY = "country";
        String COUNTRY_ID = "country_id";
        String ZIPCODE = "zipcode";
        String LOGIN_BY = "login_by";
        String SOCIAL_UNIQUE_ID = "social_unique_id";
        String COUNTRY_PHONE_CODE = "country_phone_code";
        String CITY = "city";
        String CITY_ID = "city_id";
        String DEVICE_TIMEZONE = "device_timezone";
        String PROVIDER_ID = "provider_id";
        String USER_ID = "user_id";
        String TOKEN = "token";
        String SERVICE_TYPE = "service_type";
        String LATITUDE = "latitude";
        String LONGITUDE = "longitude";
        String SUB_ADMIN_CITY = "subAdminCity";
        String TRIP_ID = "trip_id";
        String SOURCE_ADDRESS = "source_address";
        String DESTINATION_ADDRESS = "destination_address";
        String NEW_PASSWORD = "new_password";
        String OLD_PASSWORD = "old_password";
        String IS_PROVIDER_ACCEPTED = "is_provider_accepted";
        String IS_PROVIDER_STATUS = "is_provider_status";
        String IS_ACTIVE = "is_active";
        String TIME = "time";
        String REVIEW = "review";
        String RATING = "rating";
        String PAYMENT_MODE = "payment_mode";
        String UNIT = "unit";
        String CURRENCY = "currency";
        String CANCEL_REASON = "cancel_reason";
        String TYPE_ID = "typeid";
        String PICTURE_DATA = "pictureData";
        String BEARING = "bearing";
        String GOOGLE_PATH_START_LOCATION_TO_PICKUP_LOCATION = "googlePathStartLocationToPickUpLocation";
        String GOOGLE_PICKUP_LOCATION_TO_DESTINATION_LOCATION = "googlePickUpLocationToDestinationLocation";
        String BANK_ACCOUNT_NUMBER = "account_number";
        String BANK_ACCOUNT_HOLDER_NAME = "account_holder_name";
        String BANK_PERSONAL_ID_NUMBER = "personal_id_number";
        String DOB = "dob";
        String BANK_ROUTING_NUMBER = "routing_number";
        String BANK_ACCOUNT_HOLDER_TYPE = "account_holder_type";
        String BANK_CODE = "bank_code";
        String UNIQUE_CODE = "unique_code";
        String EXPIRED_DATE = "expired_date";
        String DOCUMENT_ID = "document_id";
        String TOLL_AMOUNT = "toll_amount";
        String TIP_AMOUNT = "tip_amount";
        String APP_VERSION = "app_version";
        String TIMEZONE = "timezone";
        String DATE = "date";
        String PICK_UP_LATITUDE = "latitude";
        String PICK_UP_LONGITUDE = "longitude";
        String DEST_LATITUDE = "d_latitude";
        String DEST_LONGITUDE = "d_longitude";
        String IS_SURGE_HOURS = "is_surge_hours";
        String SERVICE_TYPE_ID = "service_type_id";
        String DISTANCE = "distance";
        String PICKUP_LAT = "pickup_latitude";
        String PICKUP_LON = "pickup_longitude";
        String DEST_LAT = "destination_latitude";
        String DEST_LON = "destination_longitude";
        String VEHICLE_NAME = "vehicle_name";
        String PLATE_NO = "plate_no";
        String MODEL = "model";
        String COLOR = "color";
        String PASSING_YEAR = "passing_year";
        String VEHICLE_ID = "vehicle_id";
        String CARD_ID = "card_id";
        String ACCESSIBILITY = "accessibility";
        String LANGUAGES = "languages";
        String LOCATION_UNIQUE_ID = "location_unique_id";
        String START_DATE = "start_date";
        String END_DATE = "end_date";
        String SURGE_MULTIPLIER = "surge_multiplier";
        String IS_REQUEST_TIMEOUT = "is_request_timeout";
        String REFERRAL_CODE = "referral_code";
        String IS_SKIP = "is_skip";
        String STATE = "state";
        String POSTAL_CODE = "postal_code";
        String GENDER = "gender";
        String LOCATION = "location";
        String PAGE = "page";
        String TIME_LEFT_TO_RESPONDS_TRIP = "time_left_to_responds_trip";
        String PAYMENT_METHOD = "payment_method";
        String AMOUNT = "amount";
        String PAYMENT_INTENT_ID = "payment_intent_id";
        String IS_FROM_NOTIFICATION = "is_from_notification";
        String IS_PAYMENT_FOR_TIP = "is_payment_for_tip";
        String PAYMENT_GATEWAY_TYPE = "payment_gateway_type";
        String AUTHORIZATION_URL = "authorization_url";
        String REFERENCE = "reference";
        String REQUIRED_PARAM = "required_param";
        String PIN = "pin";
        String OTP = "otp";
        String BIRTHDAY = "birthday";
        String PAYU_HTML = "payu_html";
    }

    /**
     * app request code
     */
    public interface ServiceCode {
        int CHOOSE_PHOTO = 4;
        int TAKE_PHOTO = 5;
        int PATH_DRAW = 7;
        int GET_GOOGLE_MAP_PATH = 39;
    }

    /**
     * provider status
     */
    public interface ProviderStatus {
        int IS_REFERRAL_SKIP = 1;
        int IS_REFERRAL_APPLY = 0;
        int PROVIDER_STATUS_ONLINE = 1;
        int PROVIDER_STATUS_OFFLINE = 0;
        int PROVIDER_STATUS_TRIP_CANCELLED = 1;
        int PROVIDER_STATUS_ACCEPTED_PENDING = 2;
        int PROVIDER_STATUS_ACCEPTED = 1;
        int PROVIDER_STATUS_REJECTED = 0;
        int PROVIDER_STATUS_IDEAL = 0;
        int PROVIDER_STATUS_STARTED = 2;
        int PROVIDER_STATUS_ARRIVED = 4;
        int PROVIDER_STATUS_TRIP_STARTED = 6;
        int PROVIDER_STATUS_TRIP_END = 9;
        int IS_UPLOADED = 1;
        int IS_APPROVED = 1;
        int IS_DECLINED = 0;
        int PROVIDER_TYPE_PARTNER = 1;
        int IS_DEFAULT = 1;

    }

    /**
     * all activity and fragment TAG for log
     */
    public interface Tag {
        String FCM_MESSAGING_SERVICE = "FcmMessagingService";
        String MAP_FRAGMENT = "MapFragmentDriver";
        String TRIP_FRAGMENT = "trip_fragment";
        String FEEDBACK_FRAGMENT = "FeedbackFragment";
        String INVOICE_FRAGMENT = "invoice_fragment";
    }

    /**
     * params for google
     */
    public interface google {
        String LAT_LNG = "latlng";
        String ERROR_MESSAGE = "error_message";
        String FORMATTED_ADDRESS = "formatted_address";
        String DESTINATION_ADDRESSES = "destination_addresses";
        String ROWS = "rows";
        String ELEMENTS = "elements";
        String DISTANCE = "distance";
        String VALUE = "value";
        String DURATION = "duration";
        String ROUTES = "routes";
        String LEGS = "legs";
        String STEPS = "steps";
        String POLYLINE = "polyline";
        String POINTS = "points";
        String LAT = "lat";
        String LNG = "lng";
        String ORIGIN = "origin";
        String ORIGINS = "origins";
        String DESTINATION = "destination";
        String DESTINATIONS = "destinations";
        String KEY = "key";
        String ID = "id";
        int RC_SIGN_IN = 2001;
        String OK = "OK";
        String ADDRESS_COMPONENTS = "address_components";
        String TYPES = "types";
        String LOCALITY = "locality";
        String LONG_NAME = "long_name";
        String ADMINISTRATIVE_AREA_LEVEL_2 = "administrative_area_level_2";
        String ADMINISTRATIVE_AREA_LEVEL_1 = "administrative_area_level_1";
        String COUNTRY = "country";
        String RESULTS = "results";
        String GEOMETRY = "geometry";
        String LOCATION = "location";
        String STATUS = "status";
        String COUNTRY_CODE = "country_code";
        String SHORT_NAME = "short_name";
    }

    public interface TripType {
        int AIRPORT = 11;
        int ZONE = 12;
        int CITY = 13;
        int NORMAL = 0;
        int SCHEDULE_TRIP = 5;
        int HOTEL_PICKUP = 2;
        int TRIP_TYPE_CAR_RENTAL = 14;
        int TRIP_TYPE_CORPORATE = 7;
    }

    public interface Wallet {
        int ADD_WALLET_AMOUNT = 1;
        int REMOVE_WALLET_AMOUNT = 2;

        int ADDED_BY_ADMIN = 1;
        int ADDED_BY_CARD = 2;
        int ADDED_BY_REFERRAL = 3;
        int ORDER_PROFIT = 6;
    }

    public interface Accessibility {
        String HANDICAP = "handicap";
        String BABY_SEAT = "baby_seat";
        String HOTSPOT = "hotspot";
    }

    public interface Gender {
        String MALE = "male";
        String FEMALE = "female";
    }

    public interface Bank {
        String BANK_ACCOUNT_HOLDER_TYPE = "individual";
    }

    public interface Action {
        String START_FOREGROUND_ACTION = BuildConfig.APPLICATION_ID + ".startforeground";
    }

    public interface Pending {
        int PENDING_FOR_ADMIN_APPROVAL = 0;
        int ADD_VEHICLE = 1;
        int DOCUMENT_EXPIRE = 3;
    }

    public interface PaymentStatus {
        int WAITING = 0;
        int COMPLETED = 1;
        int FAILED = 2;
    }

    public class PaymentMethod {
        public static final int STRIPE = 10;
        public static final int PAY_STACK = 11;
        public static final int PAYU = 12;

    }

    public class VerificationParam {
        public static final String SEND_PIN = "send_pin";
        public static final String SEND_OTP = "send_otp";
        public static final String SEND_PHONE = "send_phone";
        public static final String SEND_BIRTHDATE = "send_birthdate";
        public static final String SEND_ADDRESS = "send_address";
    }

    /**
     * Phone number default length
     */
    public class PhoneNumber {
        public static final int MINIMUM_PHONE_NUMBER_LENGTH = 7;
        public static final int MAXIMUM_PHONE_NUMBER_LENGTH = 12;
    }
}
