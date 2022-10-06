package com.elluminati.eber.driver;

import static com.elluminati.eber.driver.utils.Const.IMAGE_BASE_URL;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.adapter.InvoiceAdapter;
import com.elluminati.eber.driver.components.CustomEventMapView;
import com.elluminati.eber.driver.components.MyFontButton;
import com.elluminati.eber.driver.components.MyFontEdittextView;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.components.MyFontTextViewMedium;
import com.elluminati.eber.driver.fragments.FeedbackFragment;
import com.elluminati.eber.driver.models.datamodels.CityType;
import com.elluminati.eber.driver.models.datamodels.Trip;
import com.elluminati.eber.driver.models.datamodels.TripHistory;
import com.elluminati.eber.driver.models.datamodels.User;
import com.elluminati.eber.driver.models.responsemodels.IsSuccessResponse;
import com.elluminati.eber.driver.models.responsemodels.TripHistoryDetailResponse;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.parse.ParseContent;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.GlideApp;
import com.elluminati.eber.driver.utils.Utils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TripHistoryDetailActivity extends BaseAppCompatActivity implements OnMapReadyCallback {

    private static boolean isMapTouched = false;
    private TextView tvHistoryDetailSrc, tvHistoryDetailDest, tvHistoryDetailTripTime, tvHistoryDetailDistance, tvHistoryDetailCost, tvHistoryTripDate, tvHistoryTripCreateTime, tvHistoryDetailDriverName, tvProviderEarning, tvHistoryTripNumber;
    private ImageView ivHistoryDetailPhotoDialog, ivFullScreen;
    private String tripId;
    private String unit;
    private LinearLayout llHistoryRate, llDetails, llFromAndTo, llTimeAndDistance;
    private CustomEventMapView mapView;
    private GoogleMap googleMap;
    private ArrayList<LatLng> markerList;
    private Marker pickUpMarker, destinationMarker;
    private PolylineOptions currentPathPolylineOptions;
    private CityType tripService;
    private Trip trip;
    private NumberFormat currencyFormat;

    /**
     * FeedBack Dialog component
     **/
    private Dialog feedBackDialog;
    private MyFontTextView tvDriverNameDialog;
    private RatingBar feedbackDialogRatingBar;
    private MyFontEdittextView etDialogComment;
    private MyFontButton btnDialogSubmitFeedback;
    private FrameLayout mapFrameLayout;
    private TripHistory tripDetailHistory;

    /**
     * Full Map dialog component
     **/
    private WebView webViewMap;
    private Dialog mapDialog;

    private static void setMapTouched(boolean isTouched) {
        isMapTouched = isTouched;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history_detail);
        tripDetailHistory = new TripHistory();

        initToolBar();
        setTitleOnToolbar(getResources().getString(R.string.text_history));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tripId = bundle.getString(Const.Params.TRIP_ID);
            unit = Utils.showUnit(this, bundle.getInt(Const.Params.UNIT));
        }
        currencyFormat = currencyHelper.getCurrencyFormat(preferenceHelper.getCurrencyCode());
        mapFrameLayout = findViewById(R.id.mapFrameLayout);
        mapView = findViewById(R.id.mapView);
        mapView.getMapAsync(this);
        llTimeAndDistance = findViewById(R.id.llTimeAndDistance);
        tvHistoryDetailCost = findViewById(R.id.tvHistoryDetailCost);
        tvHistoryDetailDest = findViewById(R.id.tvFareDest);
        tvHistoryDetailTripTime = findViewById(R.id.tvHistoryDetailTripTime);
        tvHistoryDetailDistance = findViewById(R.id.tvHistoryDetailDistance);
        tvHistoryTripCreateTime = findViewById(R.id.tvHistoryTripCreateTime);
        tvHistoryDetailSrc = findViewById(R.id.tvFareSrc);
        tvHistoryTripDate = findViewById(R.id.tvHistoryDetailDate);
        tvHistoryDetailDriverName = findViewById(R.id.tvHistoryDetailClientName);
        ivHistoryDetailPhotoDialog = findViewById(R.id.ivHistoryDetailPhotoDialog);
        tvProviderEarning = findViewById(R.id.tvProviderEarning);
        tvHistoryTripNumber = findViewById(R.id.tvHistoryTripNumber);
        llHistoryRate = findViewById(R.id.llHistoryRate);
        llDetails = findViewById(R.id.llDetails);
        llFromAndTo = findViewById(R.id.llFromAndTo);
        ivFullScreen = findViewById(R.id.ivFullScreen);

        markerList = new ArrayList<>();

        llHistoryRate.setOnClickListener(this);
        ivFullScreen.setOnClickListener(this);

        mapView.onCreate(savedInstanceState);

    }

    private void setUpMap() {

        this.googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        this.googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            final boolean doNotMoveCameraToCenterMarker = true;

            public boolean onMarkerClick(Marker marker) {
                return doNotMoveCameraToCenterMarker;
            }
        });

        this.googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                if (!isMapTouched) {

                }
                setMapTouched(false);
            }

        });
        getUserTripDetail(tripId);
    }

    private void setMarkerOnLocation(LatLng pickUpLatLng, LatLng destLatLng) {
        BitmapDescriptor bitmapDescriptor;
        markerList.clear();

        if (pickUpLatLng != null) {
            if (pickUpMarker == null) {
                bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(Utils.drawableToBitmap(ResourcesCompat.getDrawable(this.getResources(), R.drawable.user_pin, null)));
                pickUpMarker = googleMap.addMarker(new MarkerOptions().position(pickUpLatLng).title(this.getResources().getString(R.string.text_pick_up_loc)).icon(bitmapDescriptor));
            } else {
                pickUpMarker.setPosition(pickUpLatLng);
            }
        }
        if (destLatLng != null) {
            if (destinationMarker == null) {
                bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(Utils.drawableToBitmap(ResourcesCompat.getDrawable(this.getResources(), R.drawable.destination_pin, null)));
                destinationMarker = googleMap.addMarker(new MarkerOptions().position(destLatLng).title(this.getResources().getString(R.string.text_drop_location)).icon(bitmapDescriptor));
            } else {
                destinationMarker.setPosition(destLatLng);
            }
        }
        markerList.add(pickUpLatLng);
        markerList.add(destLatLng);
        setLocationBounds(false, markerList);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setUpMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setConnectivityListener(this);
        setAdminApprovedListener(this);
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void initCurrentPathDraw() {
        currentPathPolylineOptions = new PolylineOptions();
        currentPathPolylineOptions.color(ResourcesCompat.getColor(this.getResources(), R.color.color_app_red_path, null));
        currentPathPolylineOptions.width(15);
    }

    private void drawCurrentPath() {
        googleMap.addPolyline(currentPathPolylineOptions);
    }

    @Override
    protected boolean isValidate() {
        return false;
    }

    @Override
    public void goWithBackArrow() {
        onBackPressed();
    }


    private void getUserTripDetail(String tripId) {
        Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_waiting_for_detail_history), false, null);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.TRIP_ID, tripId);

            Call<TripHistoryDetailResponse> call = ApiClient.getClient().create(ApiInterface.class).getTripHistoryDetail(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<TripHistoryDetailResponse>() {
                @Override
                public void onResponse(Call<TripHistoryDetailResponse> call, Response<TripHistoryDetailResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            setTripData(response.body());
                            setTripProviderData(response.body().getUser());
                            setTripMapData(response.body());
                            // init model for set invoice data
                            tripService = response.body().getTripService();
                            trip = response.body().getTrip();
                            Utils.hideCustomProgressDialog();
                        } else {
                            Utils.hideCustomProgressDialog();
                        }


                    }

                }

                @Override
                public void onFailure(Call<TripHistoryDetailResponse> call, Throwable t) {
                    AppLog.handleThrowable(TripHistoryDetailActivity.class.getSimpleName(), t);

                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llHistoryRate:
                openFeedbackDialog();
                break;

            case R.id.btnDialogSubmitFeedback:
                if (feedbackDialogRatingBar.getRating() != 0) {
                    giveFeedBack();
                } else {
                    Utils.showToast(this.getResources().getString(R.string.msg_give_ratting), this);
                }
                break;
            case R.id.ivFullScreen:
                expandMap();
                break;
            default:
                //default here....
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private String getYesterdayDateString() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return parseContent.dateFormat.format(cal.getTime());
    }

    private void setTripDate(String tripDate) {
        Date currentDate = new Date();
        String date = parseContent.dateFormat.format(currentDate);
        if (tripDate.equals(date)) {
            tvHistoryTripDate.setText(getResources().getString(R.string.text_today));
        } else if (tripDate.equals(getYesterdayDateString())) {
            tvHistoryTripDate.setText(getResources().getString(R.string.text_yesterday));
        } else {
            try {
                Date returnDate = parseContent.dateFormat.parse(tripDate);
                String daySuffix = Utils.getDayOfMonthSuffix(Integer.valueOf(parseContent.day.format(returnDate)));
                tvHistoryTripDate.setText(daySuffix + " " + parseContent.dateFormatMonth.format(returnDate));

            } catch (ParseException e) {
                AppLog.handleException(TAG, e);
            }

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void openInvoiceDialog() {
        TextView tvPaymentWith, tvInvoiceNumber, tvInvoiceDistance, tvInvoiceTripTime, tvInvoiceTotal, tvTotalText;
        String unit;
        ImageView ivPaymentImage;
        MyFontTextViewMedium tvInvoiceTripType;
        MyFontTextView tvInvoiceMinFareApplied;
        RecyclerView rcvInvoice;
        LinearLayout llInvoiceInfo;
        CardView cvInvoiceDetails;
        int margin = getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin);

        Dialog invoiceDialog = new Dialog(this);
        invoiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        invoiceDialog.setContentView(R.layout.fragment_invoice);

        ivPaymentImage = invoiceDialog.findViewById(R.id.ivPaymentImage);
        tvPaymentWith = invoiceDialog.findViewById(R.id.tvPaymentWith);
        tvInvoiceNumber = invoiceDialog.findViewById(R.id.tvInvoiceNumber);
        tvInvoiceTripType = invoiceDialog.findViewById(R.id.tvInvoiceTripType);
        tvInvoiceMinFareApplied = invoiceDialog.findViewById(R.id.tvInvoiceMinFareApplied);
        tvInvoiceDistance = invoiceDialog.findViewById(R.id.tvInvoiceDistance);
        tvInvoiceTripTime = invoiceDialog.findViewById(R.id.tvInvoiceTripTime);
        rcvInvoice = invoiceDialog.findViewById(R.id.rcvInvoice);

        cvInvoiceDetails = invoiceDialog.findViewById(R.id.cvInvoiceDetails);
        cvInvoiceDetails.setRadius(getResources().getDimensionPixelOffset(R.dimen.dimen_address_padding));
        ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) cvInvoiceDetails.getLayoutParams();
        cardViewMarginParams.setMargins(margin, margin, margin, margin);
        cvInvoiceDetails.requestLayout();

        rcvInvoice.setLayoutManager(new LinearLayoutManager(this));
        rcvInvoice.setNestedScrollingEnabled(false);
        tvInvoiceTotal = invoiceDialog.findViewById(R.id.tvInvoiceTotal);
        invoiceDialog.findViewById(R.id.viewDiv).setVisibility(View.VISIBLE);
        tvTotalText = invoiceDialog.findViewById(R.id.tvTotalText);
        tvTotalText.setVisibility(View.GONE);
        tvInvoiceTotal.setVisibility(View.GONE);
        llInvoiceInfo = invoiceDialog.findViewById(R.id.llInvoiceInfo);
        llInvoiceInfo.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.color_white, null));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            llInvoiceInfo.setElevation(0);
        }
        if (trip != null && tripService != null) {
            unit = Utils.showUnit(this, trip.getUnit());
            if (trip.getIsMinFareUsed() == Const.TRUE && trip.getTripType() == Const.TripType.NORMAL) {
                tvInvoiceMinFareApplied.setVisibility(View.VISIBLE);
                tvInvoiceMinFareApplied.setText(getResources().getString(R.string.start_min_fare) + " " + currencyFormat.format(tripService.getMinFare()) + " " + getResources().getString(R.string.text_applied));
            }
            if (trip.getPaymentMode() == Const.CASH) {
                ivPaymentImage.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.cash));
                tvPaymentWith.setText(this.getResources().getString(R.string.text_payment_with_cash));
            } else {
                ivPaymentImage.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.card));
                tvPaymentWith.setText(this.getResources().getString(R.string.text_payment_with_card));
            }

            tvInvoiceNumber.setText(trip.getInvoiceNumber());

            tvInvoiceDistance.setText(ParseContent.getInstance().twoDigitDecimalFormat.format(trip.getTotalDistance()) + " " + unit);
            tvInvoiceTripTime.setText(ParseContent.getInstance().twoDigitDecimalFormat.format(trip.getTotalTime()) + " " + this.getResources().getString(R.string.text_unit_mins));
            tvInvoiceTotal.setText(currencyFormat.format(trip.getTotal()));
            tvInvoiceTotal.setVisibility(View.VISIBLE);
            tvTotalText.setVisibility(View.VISIBLE);
            switch (trip.getTripType()) {
                case Const.TripType.AIRPORT:
                    tvInvoiceTripType.setVisibility(View.VISIBLE);
                    tvInvoiceTripType.setText(this.getResources().getString(R.string.text_airport_trip));
                    break;
                case Const.TripType.ZONE:
                    tvInvoiceTripType.setVisibility(View.VISIBLE);
                    tvInvoiceTripType.setText(this.getResources().getString(R.string.text_zone_trip));
                    break;
                case Const.TripType.CITY:
                    tvInvoiceTripType.setVisibility(View.VISIBLE);
                    tvInvoiceTripType.setText(this.getResources().getString(R.string.text_city_trip));
                    break;
                default:
                    //Default case here..
                    if (trip.isFixedFare()) {
                        tvInvoiceTripType.setVisibility(View.VISIBLE);
                        tvInvoiceTripType.setText(this.getResources().getString(R.string.text_fixed_price));
                    } else {
                        tvInvoiceTripType.setVisibility(View.GONE);
                    }
                    break;
            }

            if (rcvInvoice != null) {
                InvoiceAdapter invoiceAdapter = new InvoiceAdapter(parseContent.parseInvoice(this, trip, tripService, currencyFormat));
                rcvInvoice.setAdapter(invoiceAdapter);
                invoiceAdapter.notifyDataSetChanged();
            }
        }

        WindowManager.LayoutParams params = invoiceDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        invoiceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        invoiceDialog.getWindow().setAttributes(params);
        invoiceDialog.show();
    }

    public String validSuffix(double value, String unit) {

        if (value <= 1) {
            return unit;
        } else {
            return Const.SLASH + value + unit;

        }
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            closedEnableDialogInternet();
        } else {
            openInternetDialog();
        }
    }

    @Override
    public void onGpsConnectionChanged(boolean isConnected) {
        if (isConnected) {
            closedEnableDialogGps();
        } else {
            openGpsDialog();

        }
    }

    @Override
    public void onAdminApproved() {
        goWithAdminApproved();
    }

    @Override
    public void onAdminDeclined() {
        goWithAdminDecline();
    }

    private void openFeedbackDialog() {
        if (feedBackDialog != null && feedBackDialog.isShowing()) {
            return;
        }

        feedBackDialog = new Dialog(this);
        feedBackDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        feedBackDialog.setContentView(R.layout.dialog_feedback_history);

        tvDriverNameDialog = feedBackDialog.findViewById(R.id.tvDriverNameDialog);
        feedbackDialogRatingBar = feedBackDialog.findViewById(R.id.feedbackDialogRatingBar);
        etDialogComment = feedBackDialog.findViewById(R.id.etDialogComment);
        btnDialogSubmitFeedback = feedBackDialog.findViewById(R.id.btnDialogSubmitFeedback);

        btnDialogSubmitFeedback.setOnClickListener(this);
        tvDriverNameDialog.setText(tvHistoryDetailDriverName.getText().toString());

        WindowManager.LayoutParams params = feedBackDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        feedBackDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        feedBackDialog.getWindow().setAttributes(params);
        feedBackDialog.show();
    }

    public void giveFeedBack() {
        Utils.showCustomProgressDialog(this, this.getResources().getString(R.string.msg_waiting_for_ratting), false, null);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TRIP_ID, tripId);
            jsonObject.put(Const.Params.REVIEW, etDialogComment.getText().toString());
            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.RATING, feedbackDialogRatingBar.getRating());


            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).giveRating(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {
                    if (ParseContent.getInstance().isSuccessful(response)) {
                        Utils.hideCustomProgressDialog();
                        feedBackDialog.dismiss();
                        if (response.body().isSuccess()) {
                            Utils.showToast(getResources().getString(R.string.text_succesfully_rated), TripHistoryDetailActivity.this);
                            llHistoryRate.setVisibility(View.GONE);
                        } else {
                            Utils.showErrorToast(response.body().getErrorCode(), TripHistoryDetailActivity.this);
                        }
                    }
                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    AppLog.handleThrowable(FeedbackFragment.class.getSimpleName(), t);

                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }

    private void setLocationBounds(boolean isCameraAnim, ArrayList<LatLng> markerList) {
        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
        int driverListSize = markerList.size();
        if (driverListSize > 0) {
            for (int i = 0; i < driverListSize; i++) {
                bounds.include(markerList.get(i));
            }
            CameraUpdate cu;
            //Change the padding as per needed
            if (getResources().getDisplayMetrics().density > DisplayMetrics.DENSITY_HIGH) {
                cu = CameraUpdateFactory.newLatLngBounds(bounds.build(), Const.MAP_BOUNDS);
            } else {
                cu = CameraUpdateFactory.newLatLngBounds(bounds.build(), 0);
            }
            if (isCameraAnim) {
                googleMap.animateCamera(cu);
            } else {
                googleMap.moveCamera(cu);
            }
            drawCurrentPath();
        }


    }


    private void expandMap() {
        if (llFromAndTo.getVisibility() == View.VISIBLE) {
            llDetails.setVisibility(View.GONE);
            llFromAndTo.setVisibility(View.GONE);
            llTimeAndDistance.setVisibility(View.GONE);
        } else {
            llDetails.setVisibility(View.VISIBLE);
            llFromAndTo.setVisibility(View.VISIBLE);
            llTimeAndDistance.setVisibility(View.VISIBLE);
        }
    }

    private void setTripProviderData(User user) {
        tvHistoryDetailDriverName.setText(user.getFirstName() + " " + user.getLastName());
        GlideApp.with(TripHistoryDetailActivity.this).load(IMAGE_BASE_URL + user.getPicture()).override(200, 200).placeholder(R.drawable.ellipse).fallback(R.drawable.ellipse).into(ivHistoryDetailPhotoDialog);
    }

    private void setTripMapData(TripHistoryDetailResponse response) {
        if (response.getStartTripToEndTripLocations() != null) {
            List<List<Double>> pathWayPoints = response.getStartTripToEndTripLocations();
            int size = pathWayPoints.size();
            for (int i = 0; i < size; i++) {
                List<Double> location = pathWayPoints.get(i);
                LatLng latLng = new LatLng(location.get(0), location.get(1));
                currentPathPolylineOptions.add(latLng);
            }
        }
        Trip trip = response.getTrip();
        setMarkerOnLocation(new LatLng(trip.getSourceLocation().get(0), trip.getSourceLocation().get(1)), new LatLng(trip.getDestinationLocation().get(0), trip.getDestinationLocation().get(1)));
    }

    private void setTripData(TripHistoryDetailResponse response) {
        Trip trip = response.getTrip();
        initCurrentPathDraw();
        tvHistoryDetailCost.setText(currencyFormat.format(trip.getTotal()));
        tvHistoryDetailDest.setText(trip.getDestinationAddress());
        tvHistoryDetailSrc.setText(trip.getSourceAddress());
        tvHistoryDetailTripTime.setText(parseContent.twoDigitDecimalFormat.format(trip.getTotalTime()) + " " + getResources().getString(R.string.text_unit_mins));
        tvHistoryDetailDistance.setText(parseContent.twoDigitDecimalFormat.format(trip.getTotalDistance()) + " " + "" + unit);
        tvProviderEarning.setText(currencyFormat.format(trip.getProviderServiceFees()));
        Date date = new Date();
        try {
            date = parseContent.webFormat.parse(trip.getUserCreateTime());
        } catch (ParseException e) {
            AppLog.handleException(TripHistoryDetailActivity.class.getSimpleName(), e);
        }
        tvHistoryTripCreateTime.setText(parseContent.timeFormat_am.format(date));
        setTripDate(parseContent.dateFormat.format(date));
        tvHistoryTripNumber.setText(getResources().getString(R.string.text_trip_id) + trip.getUniqueId());
        if (trip.getIsUserRated() == Const.FALSE && trip.getIsTripCancelled() == Const.FALSE) {
            llHistoryRate.setVisibility(View.VISIBLE);
        } else {
            llHistoryRate.setVisibility(View.GONE);
        }

        if (trip.getIsTripCompleted() == Const.TRUE) {
            setToolbarRightSideButton(getResources().getString(R.string.text_invoice), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openInvoiceDialog();
                }
            });
        } else {
            if (trip.getIsCancellationFee() == Const.TRUE) {
                setToolbarRightSideButton(getResources().getString(R.string.text_invoice), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openInvoiceDialog();
                    }
                });
            }

        }

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
