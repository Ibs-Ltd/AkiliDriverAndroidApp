package com.elluminati.eber.driver.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.PaymentActivity;
import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.adapter.CircularProgressViewAdapter;
import com.elluminati.eber.driver.adapter.InvoiceAdapter;
import com.elluminati.eber.driver.components.CustomCircularProgressView;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.components.MyFontTextViewMedium;
import com.elluminati.eber.driver.models.datamodels.CityType;
import com.elluminati.eber.driver.models.datamodels.Trip;
import com.elluminati.eber.driver.models.responsemodels.CardsResponse;
import com.elluminati.eber.driver.models.responsemodels.InvoiceResponse;
import com.elluminati.eber.driver.models.responsemodels.IsSuccessResponse;
import com.elluminati.eber.driver.models.responsemodels.PaymentResponse;
import com.elluminati.eber.driver.models.singleton.CurrentTrip;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.parse.ParseContent;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.Utils;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by elluminati on 26-07-2016.
 */
public class InvoiceFragment extends BaseFragments {


    private TextView tvPaymentWith, tvInvoiceNumber, tvInvoiceDistance, tvInvoiceTripTime, tvInvoiceTotal, tvTotalText;
    private String unit;
    private ImageView ivPaymentImage;
    private MyFontTextViewMedium tvInvoiceTripType;
    private MyFontTextView tvInvoiceMinFareApplied;
    private Dialog dialogProgress;
    private CustomCircularProgressView ivProgressBar;
    private RecyclerView rcvInvoice;
    private NumberFormat currencyFormat;
    private View viewDiv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View invoiceFrag = inflater.inflate(R.layout.fragment_invoice, container, false);

        getProviderInvoice();
        ivPaymentImage = invoiceFrag.findViewById(R.id.ivPaymentImage);
        tvPaymentWith = invoiceFrag.findViewById(R.id.tvPaymentWith);
        tvInvoiceNumber = invoiceFrag.findViewById(R.id.tvInvoiceNumber);

        tvInvoiceTripType = invoiceFrag.findViewById(R.id.tvInvoiceTripType);
        tvInvoiceMinFareApplied = invoiceFrag.findViewById(R.id.tvInvoiceMinFareApplied);
        tvInvoiceDistance = invoiceFrag.findViewById(R.id.tvInvoiceDistance);
        tvInvoiceTripTime = invoiceFrag.findViewById(R.id.tvInvoiceTripTime);
        tvInvoiceTotal = invoiceFrag.findViewById(R.id.tvInvoiceTotal);
        tvTotalText = invoiceFrag.findViewById(R.id.tvTotalText);
        rcvInvoice = invoiceFrag.findViewById(R.id.rcvInvoice);
        viewDiv = invoiceFrag.findViewById(R.id.viewDiv);
        return invoiceFrag;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currencyFormat = drawerActivity.currencyHelper.getCurrencyFormat(drawerActivity.preferenceHelper.getCurrencyCode());
        tvTotalText.setVisibility(View.GONE);
        tvInvoiceTotal.setVisibility(View.GONE);
        drawerActivity.setToolbarBackgroundAndElevation(false, R.color.color_white, 0);
        drawerActivity.preferenceHelper.putIsHaveTrip(false);
        drawerActivity.preferenceHelper.putLocationUniqueId(0);
        drawerActivity.setTitleOnToolbar(drawerActivity.getResources().getString(R.string.text_invoice));
        drawerActivity.setToolbarIcon(AppCompatResources.getDrawable(drawerActivity, R.drawable.ic_done_black_24dp), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                providerSubmitInvoice();
            }
        });
        rcvInvoice.setLayoutManager(new LinearLayoutManager(drawerActivity));
        rcvInvoice.setNestedScrollingEnabled(false);

    }

    private void getProviderInvoice() {
        Utils.hideCustomProgressDialog();
        showCustomProgressDialog(drawerActivity);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.TRIP_ID, drawerActivity.preferenceHelper.getTripId());
            jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.PROVIDER_ID, drawerActivity.preferenceHelper.getProviderId());

            Call<InvoiceResponse> call = ApiClient.getClient().create(ApiInterface.class).getInvoice(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<InvoiceResponse>() {
                @Override
                public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                    if (ParseContent.getInstance().isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            hideCustomProgressDialog();
                            Trip trip = response.body().getTrip();
                            setInvoiceData(trip, response.body().getTripService());
                            if (trip.getIsTripEnd() == Const.FALSE) {
                                payPayment();
                            } else if (trip.getPaymentStatus() == Const.PaymentStatus.WAITING) {
                                createStripePaymentIntent();
                            }
                        } else {
                            hideCustomProgressDialog();
                            Utils.showErrorToast(response.body().getErrorCode(), drawerActivity);
                        }

                    }
                }

                @Override
                public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                    AppLog.handleThrowable(InvoiceFragment.class.getSimpleName(), t);

                }
            });

        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }


    @Override
    public void onClick(View v) {

    }

    /**
     * Gets path of pdf file.
     *
     * @return the path of pdf file
     */
    public File getPathOfPdfFile() {
        File storageDir = new File(drawerActivity.imageHelper.getAlbumDir().getAbsolutePath(), drawerActivity.getResources().getString(R.string.text_invoice));

        if (storageDir != null) {
            if (!storageDir.mkdirs()) {
                if (!storageDir.exists()) {
                    Log.d("PdfSample", "failed to create directory");
                    return null;
                }
            }
        }
        Date date = new Date();
        String timeStamp = drawerActivity.parseContent.dateFormat.format(date);
        timeStamp = timeStamp + "_" + drawerActivity.parseContent.timeFormat.format(date);
        String imageFileName = "Invoice_" + timeStamp + ".pdf";
        File imageF = new File(storageDir.getAbsolutePath(), imageFileName);
        return imageF;
    }

    private void createPdfFile(View view) {

        PdfDocument document = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            document = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(view.getWidth(), view.getHeight(), 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            view.draw(page.getCanvas());
            document.finishPage(page);
            try {
                document.writeTo(new FileOutputStream(getPathOfPdfFile()));
            } catch (IOException e) {
                AppLog.handleException(TAG, e);
            }
            document.close();
        }
    }


    private void providerSubmitInvoice() {
        Utils.showCustomProgressDialog(drawerActivity, drawerActivity.getResources().getString(R.string.msg_waiting_for_invoice), false, null);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.TRIP_ID, drawerActivity.preferenceHelper.getTripId());
            jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.PROVIDER_ID, drawerActivity.preferenceHelper.getProviderId());
            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).submitInvoice(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {

                    if (ParseContent.getInstance().isSuccessful(response)) {
                        Utils.hideCustomProgressDialog();
                        if (response.body().isSuccess()) {
                            if (isAdded()) {
                                drawerActivity.goToFeedBackFragment();
                            }
                        } else {
                            Utils.showErrorToast(response.body().getErrorCode(), drawerActivity);
                        }
                    }

                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    AppLog.handleThrowable(InvoiceFragment.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }


    /**
     * Show custom progress dialog.
     *
     * @param context the context
     */
    public void showCustomProgressDialog(Context context) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        if (!appCompatActivity.isFinishing()) {
            if (dialogProgress != null && dialogProgress.isShowing()) {
                return;
            }
            dialogProgress = new Dialog(context);
            dialogProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogProgress.setContentView(R.layout.circuler_progerss_bar_two);
            dialogProgress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ivProgressBar = dialogProgress.findViewById(R.id.ivProgressBarTwo);
            ivProgressBar.addListener(new CircularProgressViewAdapter() {
                @Override
                public void onProgressUpdate(float currentProgress) {
                    Log.d("CPV", "onProgressUpdate: " + currentProgress);
                }

                @Override
                public void onProgressUpdateEnd(float currentProgress) {
                    Log.d("CPV", "onProgressUpdateEnd: " + currentProgress);
                }

                @Override
                public void onAnimationReset() {
                    Log.d("CPV", "onAnimationReset");
                }

                @Override
                public void onModeChanged(boolean isIndeterminate) {
                    Log.d("CPV", "onModeChanged: " + (isIndeterminate ? "indeterminate" : "determinate"));
                }
            });
            ivProgressBar.startAnimation();
            dialogProgress.setCancelable(false);
            WindowManager.LayoutParams params = dialogProgress.getWindow().getAttributes();
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialogProgress.getWindow().setAttributes(params);
            dialogProgress.getWindow().setDimAmount(0);
            dialogProgress.show();
        }
    }

    /**
     * Hide custom progress dialog.
     */
    public void hideCustomProgressDialog() {
        try {
            if (dialogProgress != null && ivProgressBar != null) {

                dialogProgress.dismiss();

            }
        } catch (Exception e) {
            AppLog.handleException(TAG, e);
        }
    }

    private void setInvoiceData(Trip trip, CityType tripService) {
        unit = Utils.showUnit(drawerActivity, trip.getUnit());
        if (trip.getIsMinFareUsed() == Const.TRUE /*&& trip.getTripType() == Const.TripType.NORMAL*/) {
            tvInvoiceMinFareApplied.setVisibility(View.VISIBLE);
            viewDiv.setVisibility(View.VISIBLE);
            tvInvoiceMinFareApplied.setText(drawerActivity.getResources().getString(R.string.start_min_fare) + " " + currencyFormat.format(tripService.getMinFare()) + " " + drawerActivity.getResources().getString(R.string.text_applied));
        }

        if (trip.getPaymentMode() == Const.CASH) {
            ivPaymentImage.setImageDrawable(AppCompatResources.getDrawable(drawerActivity, R.drawable.cash));
            tvPaymentWith.setText(drawerActivity.getResources().getString(R.string.text_payment_with_cash));
        } else {
            ivPaymentImage.setImageDrawable(AppCompatResources.getDrawable(drawerActivity, R.drawable.card));
            tvPaymentWith.setText(drawerActivity.getResources().getString(R.string.text_payment_with_card));
        }

        tvInvoiceNumber.setText(trip.getInvoiceNumber());

        tvInvoiceDistance.setText(ParseContent.getInstance().twoDigitDecimalFormat.format(trip.getTotalDistance()) + " " + unit);
        tvInvoiceTripTime.setText(ParseContent.getInstance().twoDigitDecimalFormat.format(trip.getTotalTime()) + " " + drawerActivity.getResources().getString(R.string.text_unit_mins));
        tvInvoiceTotal.setText(currencyFormat.format(trip.getTotal()));
        tvInvoiceTotal.setVisibility(View.VISIBLE);
        tvTotalText.setVisibility(View.VISIBLE);
        CurrentTrip.getInstance().setTime(trip.getTotalTime());
        CurrentTrip.getInstance().setDistance(trip.getTotalDistance());
        CurrentTrip.getInstance().setUnit(trip.getUnit());
        switch (trip.getTripType()) {
            case Const.TripType.AIRPORT:
                tvInvoiceTripType.setVisibility(View.VISIBLE);
                tvInvoiceTripType.setText(drawerActivity.getResources().getString(R.string.text_airport_trip));
                break;
            case Const.TripType.ZONE:
                tvInvoiceTripType.setVisibility(View.VISIBLE);
                tvInvoiceTripType.setText(drawerActivity.getResources().getString(R.string.text_zone_trip));
                break;
            case Const.TripType.CITY:
                tvInvoiceTripType.setVisibility(View.VISIBLE);
                tvInvoiceTripType.setText(drawerActivity.getResources().getString(R.string.text_city_trip));
                break;
            default:
                //Default case here..
                if (trip.isFixedFare()) {
                    tvInvoiceTripType.setVisibility(View.VISIBLE);
                    tvInvoiceTripType.setText(drawerActivity.getResources().getString(R.string.text_fixed_price));
                } else {
                    tvInvoiceTripType.setVisibility(View.GONE);
                }
                break;
        }

        if (rcvInvoice != null) {
            rcvInvoice.setAdapter(new InvoiceAdapter(drawerActivity.parseContent.parseInvoice(drawerActivity, trip, tripService, currencyFormat)));
        }


    }

    private void payPayment() {
        Utils.showCustomProgressDialog(drawerActivity, "", false, null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, drawerActivity.preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.TRIP_ID, drawerActivity.preferenceHelper.getTripId());
            jsonObject.put(Const.Params.TIP_AMOUNT, 0);
            Call<PaymentResponse> call = ApiClient.getClient().create(ApiInterface.class).payPayment(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<PaymentResponse>() {
                @Override
                public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                    if (ParseContent.getInstance().isSuccessful(response)) {
                        Utils.hideCustomProgressDialog();
                        if (response.body().isSuccess()) {
                            if (response.body().getPaymentStatus() == Const.PaymentStatus.FAILED) {
                                failStripPaymentIntentPayment();
                            } else if (response.body().getPaymentStatus() == Const.PaymentStatus.COMPLETED) {
                                getProviderInvoice();
                            } else {
                                if (TextUtils.isEmpty(response.body().getPaymentMethod())) {
                                    failStripPaymentIntentPayment();
                                } else {
                                    if (drawerActivity.stripe != null) {
                                        ConfirmPaymentIntentParams paymentIntentParams = ConfirmPaymentIntentParams.createWithPaymentMethodId(response.body().getPaymentMethod(), response.body().getClientSecret());
                                        drawerActivity.stripe.confirmPayment(InvoiceFragment.this, paymentIntentParams);
                                    } else {
                                        Utils.showToast(getString(R.string.msg_error_stripe), drawerActivity);
                                    }
                                }
                            }
                        } else {
                            failStripPaymentIntentPayment();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PaymentResponse> call, Throwable t) {
                    AppLog.handleThrowable(TripFragment.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }

    private void createStripePaymentIntent() {
        Utils.showCustomProgressDialog(drawerActivity, "", false, null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.TYPE, Const.PROVIDER_UNIQUE_NUMBER);
            jsonObject.put(Const.Params.TRIP_ID, drawerActivity.preferenceHelper.getTripId());
            jsonObject.put(Const.Params.USER_ID, drawerActivity.preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());
            Call<CardsResponse> call = ApiClient.getClient().create(ApiInterface.class).getStripPaymentIntent(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<CardsResponse>() {
                @Override
                public void onResponse(Call<CardsResponse> call, Response<CardsResponse> response) {
                    if (drawerActivity.parseContent.isSuccessful(response)) {
                        if (response.body().isSuccess() && TextUtils.isEmpty(response.body().getError())) {
                            if (TextUtils.isEmpty(response.body().getPaymentMethod())) {
                                Utils.hideCustomProgressDialog();
                                failStripPaymentIntentPayment();
                            } else {
                                if (drawerActivity.stripe != null) {
                                    ConfirmPaymentIntentParams paymentIntentParams = ConfirmPaymentIntentParams.createWithPaymentMethodId(response.body().getPaymentMethod(), response.body().getClientSecret());
                                    drawerActivity.stripe.confirmPayment(InvoiceFragment.this, paymentIntentParams);
                                } else {
                                    Utils.showToast(getString(R.string.msg_error_stripe), drawerActivity);
                                }
                            }
                        } else {
                            Utils.hideCustomProgressDialog();
                            failStripPaymentIntentPayment();
                        }
                        if (!TextUtils.isEmpty(response.body().getError())) {
                            Utils.showToast(response.body().getError(), drawerActivity);
                        }
                    }

                }

                @Override
                public void onFailure(Call<CardsResponse> call, Throwable t) {
                    Utils.hideCustomProgressDialog();
                    AppLog.handleThrowable(PaymentActivity.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (drawerActivity.stripe != null) {
            drawerActivity.stripe.onPaymentResult(requestCode, data, new ApiResultCallback<PaymentIntentResult>() {
                @Override
                public void onSuccess(@NonNull PaymentIntentResult result) {
                    final PaymentIntent paymentIntent = result.getIntent();
                    final PaymentIntent.Status status = paymentIntent.getStatus();
                    if (status == PaymentIntent.Status.Succeeded) {
                        payStripPaymentIntentPayment();
                    } else {
                        failStripPaymentIntentPayment();
                        Utils.showToast(getString(R.string.error_payment_cancel), drawerActivity);
                    }
                }

                @Override
                public void onError(@NonNull Exception e) {
                    Utils.hideCustomProgressDialog();
                    Utils.showToast(e.getMessage(), drawerActivity);
                    failStripPaymentIntentPayment();
                }
            });
        }
    }

    private void payStripPaymentIntentPayment() {
        Utils.showCustomProgressDialog(drawerActivity, "", false, null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.TYPE, Const.PROVIDER_UNIQUE_NUMBER);
            jsonObject.put(Const.Params.TRIP_ID, drawerActivity.preferenceHelper.getTripId());
            jsonObject.put(Const.Params.USER_ID, drawerActivity.preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());
            Call<PaymentResponse> call = ApiClient.getClient().create(ApiInterface.class).getPayStripPaymentIntentPayment(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<PaymentResponse>() {
                @Override
                public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                    if (drawerActivity.parseContent.isSuccessful(response)) {
                        Utils.hideCustomProgressDialog();
                        if (!response.body().isSuccess()) {
                            Utils.showErrorToast(response.body().getErrorCode(), drawerActivity);
                        } else {
                            getProviderInvoice();
                        }
                    }

                }

                @Override
                public void onFailure(Call<PaymentResponse> call, Throwable t) {
                    Utils.hideCustomProgressDialog();
                    AppLog.handleThrowable(PaymentActivity.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }

    private void failStripPaymentIntentPayment() {
        Utils.showCustomProgressDialog(drawerActivity, "", false, null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.TYPE, Const.PROVIDER_UNIQUE_NUMBER);
            jsonObject.put(Const.Params.TRIP_ID, drawerActivity.preferenceHelper.getTripId());
            jsonObject.put(Const.Params.USER_ID, drawerActivity.preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());
            Call<PaymentResponse> call = ApiClient.getClient().create(ApiInterface.class).getFailStripPaymentIntentPayment(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<PaymentResponse>() {
                @Override
                public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                    if (drawerActivity.parseContent.isSuccessful(response)) {
                        Utils.hideCustomProgressDialog();
                        if (!response.body().isSuccess()) {
                            Utils.showErrorToast(response.body().getErrorCode(), drawerActivity);
                        }
                    }

                }

                @Override
                public void onFailure(Call<PaymentResponse> call, Throwable t) {
                    Utils.hideCustomProgressDialog();
                    AppLog.handleThrowable(PaymentActivity.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }

}