package com.elluminati.eber.driver.parse;


import com.elluminati.eber.driver.models.datamodels.Document;
import com.elluminati.eber.driver.models.responsemodels.AddWalletResponse;
import com.elluminati.eber.driver.models.responsemodels.AppKeyResponse;
import com.elluminati.eber.driver.models.responsemodels.BankDetailResponse;
import com.elluminati.eber.driver.models.responsemodels.CardsResponse;
import com.elluminati.eber.driver.models.responsemodels.CitiesResponse;
import com.elluminati.eber.driver.models.responsemodels.CountriesResponse;
import com.elluminati.eber.driver.models.responsemodels.DocumentResponse;
import com.elluminati.eber.driver.models.responsemodels.ETAResponse;
import com.elluminati.eber.driver.models.responsemodels.EarningResponse;
import com.elluminati.eber.driver.models.responsemodels.GenerateFirebaseTokenResponse;
import com.elluminati.eber.driver.models.responsemodels.HeatMapResponse;
import com.elluminati.eber.driver.models.responsemodels.InvoiceResponse;
import com.elluminati.eber.driver.models.responsemodels.IsSuccessResponse;
import com.elluminati.eber.driver.models.responsemodels.LanguageResponse;
import com.elluminati.eber.driver.models.responsemodels.PaymentResponse;
import com.elluminati.eber.driver.models.responsemodels.ProviderDataResponse;
import com.elluminati.eber.driver.models.responsemodels.ProviderDetailResponse;
import com.elluminati.eber.driver.models.responsemodels.ProviderLocationResponse;
import com.elluminati.eber.driver.models.responsemodels.SettingsDetailsResponse;
import com.elluminati.eber.driver.models.responsemodels.TripHistoryDetailResponse;
import com.elluminati.eber.driver.models.responsemodels.TripHistoryResponse;
import com.elluminati.eber.driver.models.responsemodels.TripPathResponse;
import com.elluminati.eber.driver.models.responsemodels.TripStatusResponse;
import com.elluminati.eber.driver.models.responsemodels.TripsResponse;
import com.elluminati.eber.driver.models.responsemodels.TypesResponse;
import com.elluminati.eber.driver.models.responsemodels.VehicleDetailResponse;
import com.elluminati.eber.driver.models.responsemodels.VehicleDocumentResponse;
import com.elluminati.eber.driver.models.responsemodels.VehiclesResponse;
import com.elluminati.eber.driver.models.responsemodels.VerificationResponse;
import com.elluminati.eber.driver.models.responsemodels.WalletHistoryResponse;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("providerslogin")
    Call<ProviderDataResponse> login(@Body RequestBody requestBody);

    @Multipart
    @POST("providerupdatedetail")
    Call<ProviderDataResponse> updateProfile(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> partMap);

    @Multipart
    @POST("providerregister")
    Call<ProviderDataResponse> register(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> partMap);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("forgotpassword")
    Call<IsSuccessResponse> forgotPassword(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("get_provider_setting_detail")
    Call<SettingsDetailsResponse> getProviderSettingDetail(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("getappkeys")
    Call<AppKeyResponse> getAppKeys(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("cards")
    Call<CardsResponse> getCards(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("addcard")
    Call<IsSuccessResponse> addCard(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("delete_card")
    Call<IsSuccessResponse> deleteCard(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("providerlogout")
    Call<IsSuccessResponse> logout(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("apply_provider_referral_code")
    Call<IsSuccessResponse> applyReferralCode(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("providergiverating")
    Call<IsSuccessResponse> giveRating(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("updateproviderdevicetoken")
    Call<IsSuccessResponse> updateDeviceToken(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("add_wallet_amount")
    Call<AddWalletResponse> addWalletAmount(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("get_wallet_history")
    Call<WalletHistoryResponse> getWalletHistory(@Body RequestBody requestBody);

    @GET("api/distancematrix/json")
    Call<ResponseBody> getGoogleDistanceMatrix(@QueryMap Map<String, String> stringMap);

    @GET("api/geocode/json")
    Call<ResponseBody> getGoogleGeocode(@QueryMap Map<String, String> stringMap);

    @GET("api/directions/json")
    Call<ResponseBody> getGoogleDirection(@QueryMap Map<String, String> stringMap);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("verification")
    Call<VerificationResponse> verification(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("getlanguages")
    Call<LanguageResponse> getLanguageForTrip(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("tripcancelbyprovider")
    Call<IsSuccessResponse> cancelTrip(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("get_country_city_list")
    Call<CountriesResponse> getCountries();

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("getproviderdocument")
    Call<DocumentResponse> getDocuments(@Body RequestBody requestBody);

    @Multipart
    @POST("uploaddocument")
    Call<Document> uploadDocument(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> partMap);

    @Multipart
    @POST("upload_vehicle_document")
    Call<VehicleDocumentResponse> uploadVehicleDocument(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> partMap);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("provider_location")
    Call<ProviderLocationResponse> uploadProviderLocation(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("togglestate")
    Call<IsSuccessResponse> toggleState(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("gettrips")
    Call<TripsResponse> getTrips(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("respondstrip")
    Call<IsSuccessResponse> respondsTrip(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("providergettripstatus")
    Call<TripStatusResponse> getTripStatus(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("providerhistory")
    Call<TripHistoryResponse> getTripHistory(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("providertripdetail")
    Call<TripHistoryDetailResponse> getTripHistoryDetail(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("get_provider_detail")
    Call<ProviderDetailResponse> getProviderDetail(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("get_provider_vehicle_list")
    Call<VehiclesResponse> getVehicles(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("get_provider_vehicle_detail")
    Call<VehicleDetailResponse> getVehicleDetail(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("provider_update_vehicle_detail")
    Call<IsSuccessResponse> updateVehicle(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("change_current_vehicle")
    Call<IsSuccessResponse> changeCurrentVehicle(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("typelist_selectedcountrycity")
    Call<TypesResponse> getVehicleTypes(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("providerupdatetype")
    Call<IsSuccessResponse> updateType(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("getgooglemappath")
    Call<TripPathResponse> getTripPath(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("setgooglemappath")
    Call<IsSuccessResponse> setTripPath(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("completetrip")
    Call<IsSuccessResponse> completeTrip(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("getproviderinvoice")
    Call<InvoiceResponse> getInvoice(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("provider_submit_invoice")
    Call<IsSuccessResponse> submitInvoice(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("card_selection")
    Call<IsSuccessResponse> setSelectedCard(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("provider_add_vehicle")
    Call<VehicleDetailResponse> addVehicleDetail(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("provider_update_vehicle_detail")
    Call<IsSuccessResponse> updateVehicleDetail(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("get_bank_detail")
    Call<BankDetailResponse> getBankDetail(@Body RequestBody requestBody);


    @Multipart
    @POST("add_bank_detail")
    Call<IsSuccessResponse> addBankDetail(@PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part file, @Part MultipartBody.Part file2, @Part MultipartBody.Part file3);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("delete_bank_detail")
    Call<IsSuccessResponse> deleteBankDetail(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("citilist_selectedcountry")
    Call<CitiesResponse> getCities(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("settripstatus")
    Call<TripStatusResponse> setProviderStatus(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("pay_payment")
    Call<PaymentResponse> payPayment(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("check_destination")
    Call<IsSuccessResponse> checkDestination(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("provider_createtrip")
    Call<IsSuccessResponse> providerCreateTrip(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("getfareestimate")
    Call<ETAResponse> getETAForeTrip(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("update_provider_setting")
    Call<IsSuccessResponse> updateProviderSetting(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("get_provider_weekly_earning_detail")
    Call<EarningResponse> getProviderWeeklyEarningDetail(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("get_provider_daily_earning_detail")
    Call<EarningResponse> getProviderDailyEarningDetail(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("provider_heat_map")
    Call<HeatMapResponse> getProviderHeatMap(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("twilio_voice_call")
    Call<IsSuccessResponse> twilioCall(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("get_provider_referal_credit")
    Call<IsSuccessResponse> getReferralCredit(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("get_stripe_add_card_intent")
    Call<CardsResponse> getStripSetupIntent(@Body HashMap<String, Object> map);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("get_stripe_payment_intent")
    Call<CardsResponse> getStripPaymentIntent(@Body RequestBody requestBody);


    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("pay_stripe_intent_payment")
    Call<PaymentResponse> getPayStripPaymentIntentPayment(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("fail_stripe_intent_payment")
    Call<PaymentResponse> getFailStripPaymentIntentPayment(@Body RequestBody requestBody);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("send_paystack_required_detail")
    Call<CardsResponse> sendPayStackRequiredDetails(@Body HashMap<String, Object> map);

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("generate_firebase_access_token")
    Call<GenerateFirebaseTokenResponse> generateFirebaseAccessToken(@Body RequestBody requestBody);
}