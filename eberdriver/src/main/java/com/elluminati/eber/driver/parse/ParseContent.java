package com.elluminati.eber.driver.parse;

import static com.elluminati.eber.driver.utils.Const.IMAGE_BASE_URL;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.models.datamodels.AdminSettings;
import com.elluminati.eber.driver.models.datamodels.Analytic;
import com.elluminati.eber.driver.models.datamodels.CityType;
import com.elluminati.eber.driver.models.datamodels.CountryList;
import com.elluminati.eber.driver.models.datamodels.EarningData;
import com.elluminati.eber.driver.models.datamodels.Invoice;
import com.elluminati.eber.driver.models.datamodels.ProviderDailyAnalytic;
import com.elluminati.eber.driver.models.datamodels.ProviderData;
import com.elluminati.eber.driver.models.datamodels.ProviderEarning;
import com.elluminati.eber.driver.models.datamodels.Trip;
import com.elluminati.eber.driver.models.datamodels.User;
import com.elluminati.eber.driver.models.responsemodels.EarningResponse;
import com.elluminati.eber.driver.models.responsemodels.ProviderDataResponse;
import com.elluminati.eber.driver.models.responsemodels.SettingsDetailsResponse;
import com.elluminati.eber.driver.models.responsemodels.TripsResponse;
import com.elluminati.eber.driver.models.singleton.CurrentTrip;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.CurrencyHelper;
import com.elluminati.eber.driver.utils.PreferenceHelper;
import com.elluminati.eber.driver.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Response;

/**
 * Created by elluminati on 03-06-2016.
 */
public class ParseContent {
    private static final String TAG = "ParseContent";
    private static final ParseContent parseContent = new ParseContent();
    private static PreferenceHelper preferenceHelper;
    public SimpleDateFormat webFormat, webFormatWithLocalTimeZone;
    public SimpleDateFormat dateTimeFormat;
    public SimpleDateFormat timeFormat;
    public SimpleDateFormat timeFormat_am;
    public SimpleDateFormat dateFormat;
    public DecimalFormat twoDigitDecimalFormat, timeDecimalFormat, oneDigitDecimalFormat;
    public SimpleDateFormat dateFormatMonth;
    public SimpleDateFormat day;
    public SimpleDateFormat dailyEarningDateFormat;
    public DecimalFormat singleDigit;
    private Context context;

    private ParseContent() {
        initDateTimeFormat();
    }

    public static ParseContent getInstance() {
        return parseContent;
    }

    public void getContext(Context context) {
        preferenceHelper = PreferenceHelper.getInstance(context);
        this.context = context;
    }

    public void initDateTimeFormat() {
        webFormat = new SimpleDateFormat(Const.DATE_TIME_FORMAT_WEB, Locale.getDefault());
        webFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateTimeFormat = new SimpleDateFormat(Const.DATE_TIME_FORMAT, Locale.getDefault());
        timeFormat = new SimpleDateFormat(Const.TIME_FORMAT, Locale.getDefault());
        timeFormat_am = new SimpleDateFormat(Const.TIME_FORMAT_AM, Locale.getDefault());
        dateFormat = new SimpleDateFormat(Const.DATE_FORMAT, Locale.getDefault());
        twoDigitDecimalFormat = new DecimalFormat("0.00");
        oneDigitDecimalFormat = new DecimalFormat("0.0");
        timeDecimalFormat = new DecimalFormat("#");
        dateFormatMonth = new SimpleDateFormat(Const.DATE_FORMAT_MONTH, Locale.getDefault());
        day = new SimpleDateFormat(Const.DAY, Locale.getDefault());
        dailyEarningDateFormat = new SimpleDateFormat(Const.DATE_FORMAT_EARNING, Locale.getDefault());
        singleDigit = new DecimalFormat("0");
        webFormatWithLocalTimeZone = new SimpleDateFormat(Const.DATE_TIME_FORMAT_WEB, Locale.getDefault());
        webFormatWithLocalTimeZone.setTimeZone(TimeZone.getDefault());
    }

    public SimpleDateFormat getDateFormatInEnglish(String format) {
        return new SimpleDateFormat(format, Locale.ENGLISH);
    }

    public boolean saveProviderData(ProviderDataResponse response, boolean isSaveFullData) {


        if (response.isSuccess()) {
            ProviderData providerData = response.getProviderData();
            Utils.showMessageToast(response.getMessage(), context);
            preferenceHelper.putProviderId(providerData.getProviderId());
            preferenceHelper.putContact(providerData.getPhone());
            preferenceHelper.putBio(providerData.getBio());
            preferenceHelper.putAddress(providerData.getAddress());
            preferenceHelper.putFirstName(providerData.getFirstName());
            preferenceHelper.putLastName(providerData.getLastName());
            preferenceHelper.putProfilePic(IMAGE_BASE_URL + providerData.getPicture());
            preferenceHelper.putCountryPhoneCode(providerData.getCountryPhoneCode());
            preferenceHelper.putGender(providerData.getGender());
            preferenceHelper.putEmail(providerData.getEmail());
            if (isSaveFullData) {
                preferenceHelper.putCurrencyCode(providerData.getWalletCurrencyCode());
                preferenceHelper.putIsProviderOnline(providerData.getIsActive());
                preferenceHelper.putSessionToken(providerData.getToken());
                preferenceHelper.putSocialId(providerData.getSocialUniqueId());
                preferenceHelper.putAllDocUpload(providerData.getIsDocumentUploaded());
                preferenceHelper.putIsApproved(providerData.getIsApproved());
                preferenceHelper.putLoginBy(providerData.getLoginBy());
                preferenceHelper.putProviderType(providerData.getProviderType());
                preferenceHelper.putReferralCode(providerData.getReferralCode());
                preferenceHelper.putIsApplyReferral(providerData.getIsReferral());
                if (providerData.getProviderType() == Const.ProviderStatus.PROVIDER_TYPE_PARTNER) {
                    preferenceHelper.putIsPartnerApprovedByAdmin(providerData.getIsPartnerApprovedByAdmin());
                    preferenceHelper.putPartnerEmail(providerData.getPartnerEmail());
                }
                if (providerData.getCountryDetail() != null) {
                    preferenceHelper.putIsHaveReferral(providerData.getCountryDetail().isReferral());
                }
            }

            return true;
        } else {
            Utils.showErrorToast(response.getErrorCode(), context);
        }
        return false;
    }

    public ArrayList<CountryList> getRawCountryCodeList() {
        InputStream inputStream = context.getResources().openRawResource(R.raw.country_list);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<CountryList>>() {
        }.getType();
        return gson.fromJson(byteArrayOutputStream.toString(), listType);
    }

    public void parsUser(User user) {
        CurrentTrip currentTrip = CurrentTrip.getInstance();
        currentTrip.setUserFirstName(user.getFirstName());
        currentTrip.setUserLastName(user.getLastName());
        currentTrip.setUserProfileImage(IMAGE_BASE_URL + user.getPicture());
        currentTrip.setUserPhone(user.getPhone());
        currentTrip.setPhoneCountryCode(user.getCountryPhoneCode());
        currentTrip.setUserRate(user.getRate());
    }

    public boolean parseTrips(TripsResponse response) {
        if (response.isSuccess()) {
            parsUser(response.getUser());
            CurrentTrip currentTrip = CurrentTrip.getInstance();
            currentTrip.setTimeLeft(response.getTimeLeftToRespondsTrip());
            preferenceHelper.putTripId(response.getTripId());
            return true;
        }
        preferenceHelper.putTripId("");
        return false;
    }

    public void parseProviderSettingDetail(SettingsDetailsResponse response) {
        AdminSettings adminSettings = response.getAdminSettings();
        if (!TextUtils.isEmpty(response.getAdminSettings().getImageBaseUrl())) {
            Const.IMAGE_BASE_URL = response.getAdminSettings().getImageBaseUrl();
        }
        preferenceHelper.putIsShowEstimation(adminSettings.isShowEstimationInProviderApp());
        preferenceHelper.putGoogleServerKey(adminSettings.getAndroidProviderAppGoogleKey());
        if (!TextUtils.isEmpty(adminSettings.getAndroidPlacesAutoCompleteKey())) {
            preferenceHelper.putGoogleAutoCompleteKey(adminSettings.getAndroidPlacesAutoCompleteKey());
        } else if (!TextUtils.isEmpty(adminSettings.getAndroidProviderAppGoogleKey())) {
            preferenceHelper.putGoogleAutoCompleteKey(adminSettings.getAndroidProviderAppGoogleKey());
        } else {
            preferenceHelper.putGoogleAutoCompleteKey(context.getResources().getString(R.string.GOOGLE_ANDROID_API_KEY));
        }
        preferenceHelper.putTwilioNumber(adminSettings.getTwilioNumber());
        preferenceHelper.putStripePublicKey(adminSettings.getStripePublishableKey());

        preferenceHelper.putIsProviderEmailVerification(adminSettings.isProviderEmailVerification());
        preferenceHelper.putIsProviderSocialLogin(adminSettings.isProviderSocialLogin());
        preferenceHelper.putIsProviderSMSVerification(adminSettings.isProviderSms());
        preferenceHelper.putContactUsEmail(adminSettings.getContactUsEmail());
        preferenceHelper.putAdminPhone(adminSettings.getAdminPhone());
        preferenceHelper.putIsPathDraw(adminSettings.isProviderPath());
        preferenceHelper.putIsProviderInitiateTrip(adminSettings.isIsProviderInitiateTrip());
        preferenceHelper.putTermsANdConditions(adminSettings.getTermsAndConditionUrl());
        preferenceHelper.putPolicy(adminSettings.getPrivacyPolicyUrl());
        preferenceHelper.putTripId("");
        preferenceHelper.putTwilioCallMaskEnable(adminSettings.isTwilioCallMasking());
        preferenceHelper.putMinimumPhoneNumberLength(adminSettings.getMinimumPhoneNumberLength());
        preferenceHelper.putMaximumPhoneNumberLength(adminSettings.getMaximumPhoneNumberLength());
        if (response.getProviderData() != null) {

            ProviderData providerData = response.getProviderData();
            preferenceHelper.putIsApplyReferral(providerData.getIsReferral());
            preferenceHelper.putAllDocUpload(providerData.getIsDocumentUploaded());
            if (!TextUtils.isEmpty(providerData.getWalletCurrencyCode())) {
                preferenceHelper.putCurrencyCode(providerData.getWalletCurrencyCode());
                CurrencyHelper.getInstance(context).getCurrencyFormat(providerData.getWalletCurrencyCode());
            }
            if (providerData.getCountryDetail() != null) {
                preferenceHelper.putIsHaveReferral(providerData.getCountryDetail().isReferral());
            }
        } else {
            preferenceHelper.putSessionToken(null);
        }
        TripsResponse tripsResponse = response.getTripsResponse();
        if (tripsResponse != null) {
            tripsResponse.setSuccess(true);
            parseTrips(tripsResponse);
        }

    }


    public HashMap<String, String> parsDistanceMatrix(String response) {
        HashMap<String, String> map = new HashMap<>();
        String destAddress, distance, time, originAddress, text;
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString(Const.google.STATUS).equals(Const.google.OK)) {
                destAddress = jsonObject.getJSONArray(Const.google.DESTINATION_ADDRESSES).getString(0);
                JSONObject rowsJson = jsonObject.getJSONArray(Const.google.ROWS).getJSONObject(0);
                JSONObject elementsJson = rowsJson.getJSONArray(Const.google.ELEMENTS).getJSONObject(0);
                distance = elementsJson.getJSONObject(Const.google.DISTANCE).getString(Const.google.VALUE);
                time = elementsJson.getJSONObject(Const.google.DURATION).getString(Const.google.VALUE);
                map.put(Const.google.DESTINATION_ADDRESSES, destAddress);
                map.put(Const.google.DISTANCE, distance);
                map.put(Const.google.DURATION, time);
                return map;
            } else {
                Utils.showToast(jsonObject.optString(Const.google.ERROR_MESSAGE) + "", context);
            }
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
        return null;
    }


    public HashMap<String, String> parsGeocode(String response) {
        HashMap<String, String> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString(Const.google.STATUS).equals(Const.google.OK)) {

                JSONObject resultObject = jsonObject.getJSONArray(Const.google.RESULTS).getJSONObject(0);

                JSONArray addressComponent = resultObject.getJSONArray(Const.google.ADDRESS_COMPONENTS);
                String formattedAddress = resultObject.optString(Const.google.FORMATTED_ADDRESS, "No address found");
                map.put(Const.google.FORMATTED_ADDRESS, formattedAddress);
                JSONObject geometryObject = resultObject.getJSONObject(Const.google.GEOMETRY);
                map.put(Const.google.LAT, geometryObject.getJSONObject(Const.google.LOCATION).getString(Const.google.LAT));
                map.put(Const.google.LNG, geometryObject.getJSONObject(Const.google.LOCATION).getString(Const.google.LNG));

                int addressSize = addressComponent.length();
                for (int i = 0; i < addressSize; i++) {
                    JSONObject address = addressComponent.getJSONObject(i);
                    JSONArray typesArray = address.getJSONArray(Const.google.TYPES);
                    if (typesArray.length() > 0) {

                        if (Const.google.LOCALITY.equals(typesArray.get(0).toString())) {
                            map.put(Const.google.LOCALITY, address.getString(Const.google.LONG_NAME));

                        } else if (Const.google.ADMINISTRATIVE_AREA_LEVEL_2.equals(typesArray.get(0).toString())) {
                            map.put(Const.google.ADMINISTRATIVE_AREA_LEVEL_2, address.getString(Const.google.LONG_NAME));
                        } else if (Const.google.ADMINISTRATIVE_AREA_LEVEL_1.equals(typesArray.get(0).toString())) {
                            map.put(Const.google.ADMINISTRATIVE_AREA_LEVEL_1, address.getString(Const.google.LONG_NAME));
                        } else if (Const.google.COUNTRY.equals(typesArray.get(0).toString())) {
                            map.put(Const.google.COUNTRY, address.getString(Const.google.LONG_NAME));
                            map.put(Const.google.COUNTRY_CODE, address.getString(Const.google.SHORT_NAME));
                        }
                    }
                }
                return map;
            }

        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
        return null;
    }


    public void parseEarning(EarningResponse earningResponse, ArrayList<ArrayList<EarningData>> arrayListForEarning, ArrayList<Analytic> arrayListProviderAnalytic, boolean isWeekEarning, Context context) {
        ProviderEarning providerEarning;
        if (isWeekEarning) {
            providerEarning = earningResponse.getProviderWeekEarning();
        } else {
            providerEarning = earningResponse.getProviderDayEarning();
        }

        if (providerEarning == null) {
            providerEarning = new ProviderEarning();
        }

        Resources res = context.getResources();
        String tag1 = res.getString(R.string.text_trip_earning);
        String tag2 = res.getString(R.string.text_provider_transactions);
        String tag3 = res.getString(R.string.text_payment);

        ArrayList<EarningData> earningDataArrayList1 = new ArrayList<>();

        earningDataArrayList1.add(loadEarningData(tag1, res.getString(R.string.text_service_price), "", providerEarning.getServiceTotal()));
        earningDataArrayList1.add(loadEarningData(tag1, res.getString(R.string.text_surge_price), "+", providerEarning.getTotalServiceSurgeFees()));
        earningDataArrayList1.add(loadEarningData(tag1, res.getString(R.string.text_tax_fees), "", providerEarning.getTotalProviderTaxFees()));
        earningDataArrayList1.add(loadEarningData(tag1, res.getString(R.string.text_miscellaneous_fees), "", providerEarning.getTotalProviderMiscellaneousFees()));
        earningDataArrayList1.add(loadEarningData(tag1, res.getString(R.string.text_toll), "", providerEarning.getTotalTollAmount()));
        earningDataArrayList1.add(loadEarningData(tag1, res.getString(R.string.text_tip_amount), "", providerEarning.getTotalTipAmount()));
        arrayListForEarning.add(earningDataArrayList1);


        ArrayList<EarningData> earningDataArrayList2 = new ArrayList<>();

        earningDataArrayList2.add(loadEarningData(tag2, res.getString(R.string.text_provider_have_cash), "", providerEarning.getTotalProviderHaveCash()));
        earningDataArrayList2.add(loadEarningData(tag2, res.getString(R.string.text_deduct_wallet_amount), "", providerEarning.getTotalDeductWalletAmount()));

        earningDataArrayList2.add(loadEarningData(tag2, res.getString(R.string.text_added_wallet_amount), "", providerEarning.getTotalAddedWalletAmount()));


        arrayListForEarning.add(earningDataArrayList2);


        ArrayList<EarningData> earningDataArrayList3 = new ArrayList<>();
        earningDataArrayList3.add(loadEarningData(tag3, res.getString(R.string.text_total_earning), "", providerEarning.getTotalProviderServiceFees()));
        earningDataArrayList3.add(loadEarningData(tag3, res.getString(R.string.text_paid_in_wallet), "", providerEarning.getTotalPaidInWalletPayment()));
        earningDataArrayList3.add(loadEarningData(tag3, res.getString(R.string.text_admin_paid), "", providerEarning.getTotalTransferredAmount()));
        earningDataArrayList3.add(loadEarningData(tag3, res.getString(R.string.text_pay_to_provider), "", providerEarning.getTotalPayToProvider()));

        arrayListForEarning.add(earningDataArrayList3);


        ProviderDailyAnalytic analyticDaily;

        if (isWeekEarning) {
            analyticDaily = earningResponse.getProviderWeekAnalytic();
        } else {
            analyticDaily = earningResponse.getProviderDailyAnalytic();
        }


        arrayListProviderAnalytic.add(loadAnalyticData(res.getString(R.string.text_time_online), Utils.secondsToHoursMinutesSeconds(analyticDaily.getTotalOnlineTime())));
        arrayListProviderAnalytic.add(loadAnalyticData(res.getString(R.string.text_received_request), String.valueOf(analyticDaily.getReceived())));


        arrayListProviderAnalytic.add(loadAnalyticData(res.getString(R.string.text_accepted_order), String.valueOf(analyticDaily.getAccepted())));
        arrayListProviderAnalytic.add(loadAnalyticData(res.getString(R.string.text_accepted_ratio), twoDigitDecimalFormat.format(analyticDaily.getAcceptionRatio()) + "%"));
        arrayListProviderAnalytic.add(loadAnalyticData(res.getString(R.string.text_completed_order), String.valueOf(analyticDaily.getCompleted())));
        arrayListProviderAnalytic.add(loadAnalyticData(res.getString(R.string.text_complete_ratio), twoDigitDecimalFormat.format(analyticDaily.getCompletedRatio()) + "%"));
        arrayListProviderAnalytic.add(loadAnalyticData(res.getString(R.string.text_rejected_order), String.valueOf(analyticDaily.getRejected())));
        arrayListProviderAnalytic.add(loadAnalyticData(res.getString(R.string.text_rejected_ratio), twoDigitDecimalFormat.format(analyticDaily.getRejectionRatio()) + "%"));
        arrayListProviderAnalytic.add(loadAnalyticData(res.getString(R.string.text_canceled_order), String.valueOf(analyticDaily.getCancelled())));
        arrayListProviderAnalytic.add(loadAnalyticData(res.getString(R.string.text_canceled_ratio), twoDigitDecimalFormat.format(analyticDaily.getCancellationRatio()) + "%"));


    }

    private EarningData loadEarningData(String titleMain, String title, String currency, double mainPrice) {

        EarningData earningData = new EarningData();
        earningData.setTitle(title);
        earningData.setTitleMain(titleMain);
        earningData.setPrice(currency + parseContent.twoDigitDecimalFormat.format(mainPrice));

        return earningData;
    }

    private Analytic loadAnalyticData(String title, String value) {
        Analytic analytic = new Analytic();
        analytic.setTitle(title);
        analytic.setValue(value);
        return analytic;
    }

    public boolean isSuccessful(Response<?> response) {
        if (response.isSuccessful() && response.body() != null) {

            return true;

        } else {
            Utils.showHttpErrorToast(response.code(), context);
            Utils.hideCustomProgressDialog();
        }
        return false;
    }

    public ArrayList<Invoice> parseInvoice(Context context, Trip trip, CityType tripService, NumberFormat currencyFormat) {
        ArrayList<Invoice> invoices = new ArrayList<>();
        if (trip != null && tripService != null) {
            String unit = Utils.showUnit(context, trip.getUnit());
            Resources res = context.getResources();
            if (trip.getTripType() == Const.TripType.NORMAL) {
                if (trip.isFixedFare() && trip.getFixedPrice() > 0) {
                    invoices.add(loadInvoiceData(res.getString(R.string.text_fixed_rate), currencyFormat.format(trip.getFixedPrice())));
                }
            } else if (trip.getTripTypeAmount() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_fixed_rate), currencyFormat.format(trip.getTripTypeAmount())));
            }

            if (!TextUtils.isEmpty(trip.getCarRentalId())) {
                String baseTimeAndDistance = ParseContent.getInstance().twoDigitDecimalFormat.format(tripService.getBasePriceTime()) + context.getResources().getString(R.string.text_unit_mins) + " & " + ParseContent.getInstance().twoDigitDecimalFormat.format(tripService.getBasePriceDistance()) + Utils.showUnit(context, trip.getUnit());
                invoices.add(loadInvoiceData(res.getString(R.string.text_base_price), currencyFormat.format(tripService.getBasePrice()), baseTimeAndDistance));
            } else if (tripService.getBasePrice() > 0 && !trip.isFixedFare()) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_base_price), currencyFormat.format(tripService.getBasePrice()), currencyFormat.format(tripService.getBasePrice()) + appendString(tripService.getBasePriceDistance(), unit)));
            }
            if (trip.getDistanceCost() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_distance_cost), currencyFormat.format(trip.getDistanceCost()), currencyFormat.format(tripService.getPricePerUnitDistance()) + appendString(0.0, unit)));
            }


            if (trip.getTimeCost() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_time_cost), currencyFormat.format(trip.getTimeCost()), currencyFormat.format(tripService.getPriceForTotalTime()) + getStrings(R.string.text_unit_per_time)));
            }

            if (trip.getWaitingTimeCost() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_wait_time_cost), currencyFormat.format(trip.getWaitingTimeCost()), currencyFormat.format(tripService.getPriceForWaitingTime()) + getStrings(R.string.text_unit_per_time)));
            }

            if (trip.getTaxFee() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_tax), currencyFormat.format(trip.getTaxFee()), ParseContent.getInstance().twoDigitDecimalFormat.format(tripService.getTax()) + Const.PERCENTAGE));
            }


            if (trip.getSurgeFee() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_surge_price), currencyFormat.format(trip.getSurgeFee()), "x" + ParseContent.getInstance().twoDigitDecimalFormat.format(trip.getSurgeMultiplier())));
            }

            if (trip.getTipAmount() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_tip), currencyFormat.format(trip.getTipAmount())));
            }

            if (trip.getTollAmount() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_toll), currencyFormat.format(trip.getTollAmount())));
            }


            if (trip.getUserMiscellaneousFee() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_user_miscellaneous_fee), currencyFormat.format(trip.getUserMiscellaneousFee())));
            }

            if (trip.getUserTaxFee() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_user_city_tax), currencyFormat.format(trip.getUserTaxFee()), ParseContent.getInstance().twoDigitDecimalFormat.format(tripService.getUserTax()) + Const.PERCENTAGE));
            }

            if (trip.getProviderMiscellaneousFee() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_provider_miscellaneous_fee), currencyFormat.format(trip.getProviderMiscellaneousFee())));
            }

            if (trip.getProviderTaxFee() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_provider_city_tax), currencyFormat.format(trip.getProviderTaxFee()), ParseContent.getInstance().twoDigitDecimalFormat.format(tripService.getProviderTax()) + Const.PERCENTAGE));

            }

            if (trip.getReferralPayment() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_referral_bonus), currencyFormat.format(trip.getReferralPayment())));
            }
            if (trip.getPromoPayment() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_promo_bonus), currencyFormat.format(trip.getPromoPayment())));
            }

            if (trip.getWalletPayment() > 0) {
                invoices.add(loadInvoiceData(res.getString(R.string.text_wallet), currencyFormat.format(trip.getWalletPayment())));
            }

            if (trip.getPaymentMode() == Const.CARD || trip.getPaymentMode() == Const.APPLE) {
                if (trip.getRemainingPayment() > 0) {
                    invoices.add(loadInvoiceData(res.getString(R.string.text_remain), currencyFormat.format(trip.getRemainingPayment())));
                } else {
                    invoices.add(loadInvoiceData(res.getString(R.string.text_payment_with_card), currencyFormat.format(trip.getCardPayment())));
                }
            } else {
                invoices.add(loadInvoiceData(res.getString(R.string.text_payment_with_cash), currencyFormat.format(trip.getCashPayment())));
            }
        }
        return invoices;
    }


    private Invoice loadInvoiceData(String title, String mainPrice, String subString) {

        Invoice invoice = new Invoice();
        invoice.setPrice(mainPrice);
        invoice.setSubTitle(subString);
        invoice.setTitle(title);
        return invoice;
    }

    private Invoice loadInvoiceData(String title, String mainPrice) {

        Invoice invoice = new Invoice();
        invoice.setPrice(mainPrice);
        invoice.setTitle(title);
        return invoice;
    }

    private String appendString(Double value, String unit) {
        if (value <= 1) {
            return Const.SLASH + unit;
        } else {
            return Const.SLASH + value + unit;

        }
    }

    private String getStrings(int resId) {
        return context.getResources().getString(resId);
    }
}