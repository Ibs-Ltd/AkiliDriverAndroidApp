package com.elluminati.eber.driver.fragments;

import static com.elluminati.eber.driver.utils.Const.IMAGE_BASE_URL;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.elluminati.eber.driver.AddVehicleDetailActivity;
import com.elluminati.eber.driver.ContactUsActivity;
import com.elluminati.eber.driver.DocumentActivity;
import com.elluminati.eber.driver.MainDrawerActivity;
import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.adapter.PlaceAutocompleteAdapter;
import com.elluminati.eber.driver.adapter.VehicleSelectionAdapter;
import com.elluminati.eber.driver.components.CustomCountryDialog;
import com.elluminati.eber.driver.components.CustomDialogBigLabel;
import com.elluminati.eber.driver.components.CustomEventMapView;
import com.elluminati.eber.driver.components.MyAppTitleFontTextView;
import com.elluminati.eber.driver.components.MyFontAutocompleteView;
import com.elluminati.eber.driver.components.MyFontButton;
import com.elluminati.eber.driver.components.MyFontEdittextView;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.components.MyFontTextViewMedium;
import com.elluminati.eber.driver.models.datamodels.CityType;
import com.elluminati.eber.driver.models.datamodels.Country;
import com.elluminati.eber.driver.models.datamodels.CountryList;
import com.elluminati.eber.driver.models.datamodels.DayTime;
import com.elluminati.eber.driver.models.datamodels.Provider;
import com.elluminati.eber.driver.models.datamodels.SurgeResult;
import com.elluminati.eber.driver.models.datamodels.SurgeTime;
import com.elluminati.eber.driver.models.datamodels.TypeDetails;
import com.elluminati.eber.driver.models.datamodels.VehicleDetail;
import com.elluminati.eber.driver.models.responsemodels.ETAResponse;
import com.elluminati.eber.driver.models.responsemodels.HeatMapResponse;
import com.elluminati.eber.driver.models.responsemodels.IsSuccessResponse;
import com.elluminati.eber.driver.models.responsemodels.PickupLocations;
import com.elluminati.eber.driver.models.responsemodels.ProviderDetailResponse;
import com.elluminati.eber.driver.models.responsemodels.TripsResponse;
import com.elluminati.eber.driver.models.responsemodels.TypesResponse;
import com.elluminati.eber.driver.models.responsemodels.VehiclesResponse;
import com.elluminati.eber.driver.models.singleton.CurrentTrip;
import com.elluminati.eber.driver.models.validations.Validator;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.parse.ParseContent;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.FieldValidation;
import com.elluminati.eber.driver.utils.GlideApp;
import com.elluminati.eber.driver.utils.LatLngInterpolator;
import com.elluminati.eber.driver.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by elluminati on 20-06-2016.
 */
public class MapFragment extends BaseFragments implements OnMapReadyCallback, MainDrawerActivity.LocationReceivedListener, MainDrawerActivity.NetworkListener {

    private GoogleMap googleMap;
    private CameraPosition cameraPosition;
    private CustomEventMapView mapView;
    private FloatingActionButton ivTargetLocation;
    private ImageView ivCarImage;
    private MyFontButton btnGoOnline;
    private RelativeLayout llNotApproved;
    private TripStatusReceiver tripStatusReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private LinearLayout llGoOnline, llBlackView;
    private FrameLayout llCarDetails;
    private Marker myLocationMarker;
    private String unit, providerCurrentCity, countryName, serviceTypeId, adminTypeId, countryId;
    private LatLng currentLatLng;
    private Dialog vehicleTypeDialog;
    private ArrayList<CityType> vehicleTypeList;
    private ArrayList<LatLng> heatMapLocationList;
    private HeatmapTileProvider heatmapTileProvider;
    private TileOverlay tileOverlay;
    private Handler heatMapHandler;
    private ScheduledExecutorService heatMapScheduleService;
    private boolean isSchedularStart, isHeatMapLoaded;
    private ImageView ivDriverCar;
    private ImageView llCreateTrip;
    private Dialog createTripDialog;
    private PlaceAutocompleteAdapter placeAutocompleteAdapter;
    private String pickupAddress, destinationAddress;
    private LatLng destinationLatLng;
    private String serverTime;
    private int surgeStartTime, surgeEndTime;
    private String cityTimeZone;
    private MyFontTextView tvTagWallet, tvAdminMessage;
    private MyFontTextViewMedium tvCarName;
    private TextView tvCarType, tvCarPlateNo, tvCarBasePrice, tvCarDistancePrice, tvCarTimePrice;
    private boolean isCameraIdeal = true;
    private boolean isCreateTripPress = false;
    private Intent actionIntent;
    private BottomSheetBehavior vehicleSheetBehavior;
    private final View.OnKeyListener onBackKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                if (vehicleSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    vehicleSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    return true;
                }
            }
            return false;
        }
    };
    private LinearLayout llVehicleTag;
    private TextView tvYourVehicle;
    private IntentFilter intentFilter;
    /**
     * Create trip dialog component.
     */
    private ImageView ivCloseTripDialog;
    private MyFontTextView tvCreateSrcAddress, tvFareEst, tvSpeed;
    private MyFontAutocompleteView actvCreateDesAddress;
    private MyFontEdittextView edtCreateFirstName, edtCreateLastName, edtCreateEmail;
    private MyFontEdittextView edtCreatePhone;
    private MyFontEdittextView edtCreatePhoneCode;
    private ImageView ivClearTextDestAddress;
    private LinearLayout llRideNow;
    private MyFontButton btnPendingWorkAction;
    private ArrayList<CountryList> codeArrayList;
    private MyFontButton btnAddVehicle, btnGoOffline;
    private RecyclerView rcvSelectVehicle;
    private ArrayList<VehicleDetail> listVehicle;
    private VehicleSelectionAdapter vehicleSelectionAdapter;
    private LinearLayout llVehicleDetail;
    private CityType cityType;
    private NumberFormat currencyFormat;
    private CountryList country;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mapFragView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = mapFragView.findViewById(R.id.mapView);
        llGoOnline = mapFragView.findViewById(R.id.llGoOnLine);
        btnGoOnline = mapFragView.findViewById(R.id.btnGoOnlineDialog);
        ivTargetLocation = mapFragView.findViewById(R.id.ivTargetLocation);
        llCarDetails = mapFragView.findViewById(R.id.llCarDetails);
        ivCarImage = mapFragView.findViewById(R.id.ivCarImage);
        ivDriverCar = mapFragView.findViewById(R.id.ivDriverCar);
        llCreateTrip = mapFragView.findViewById(R.id.llCreateTrip);
        tvCarName = mapFragView.findViewById(R.id.tvCarName);
        tvCarPlateNo = mapFragView.findViewById(R.id.tvCarPlateNo);
        tvCarBasePrice = mapFragView.findViewById(R.id.tvCarBasePrice);
        tvCarDistancePrice = mapFragView.findViewById(R.id.tvCarDistancePrice);
        tvCarTimePrice = mapFragView.findViewById(R.id.tvCarTimePrice);
        tvCarType = mapFragView.findViewById(R.id.tvCarType);
        tvTagWallet = mapFragView.findViewById(R.id.tvTagWallet);
        llNotApproved = mapFragView.findViewById(R.id.llNotApproved);
        tvAdminMessage = mapFragView.findViewById(R.id.tvAdminMessage);
        tvSpeed = mapFragView.findViewById(R.id.tvSpeed);
        btnPendingWorkAction = mapFragView.findViewById(R.id.btnAction);
        llVehicleTag = mapFragView.findViewById(R.id.llVehicleTag);
        tvYourVehicle = mapFragView.findViewById(R.id.tvYourVehicle);
        btnAddVehicle = mapFragView.findViewById(R.id.btnAddVehicle);
        rcvSelectVehicle = mapFragView.findViewById(R.id.rcvSelectVehicle);
        btnGoOffline = mapFragView.findViewById(R.id.btnGoOffline);
        llVehicleDetail = mapFragView.findViewById(R.id.llVehicleDetail);
        llBlackView = mapFragView.findViewById(R.id.llBlackView);
        return mapFragView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currencyFormat = drawerActivity.currencyHelper.getCurrencyFormat(drawerActivity.preferenceHelper.getCurrencyCode());
        drawerActivity.setToolbarBackgroundAndElevation(true, R.drawable.toolbar_bg_rounded_bottom, R.dimen.toolbar_elevation);
        drawerActivity.setTitleOnToolbar(drawerActivity.getResources().getString(R.string.app_name));
        drawerActivity.closedProgressDialog();
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        btnGoOnline.setOnClickListener(this);
        ivTargetLocation.setOnClickListener(this);
        llCreateTrip.setOnClickListener(this);
        codeArrayList = drawerActivity.parseContent.getRawCountryCodeList();
        initTripStatusReceiver();
        vehicleTypeList = new ArrayList<>();
        heatMapLocationList = new ArrayList<>();
        initHeatMapHandler();
        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(drawerActivity);
        btnPendingWorkAction.setOnClickListener(this);
        initVehicleBottomSheet();
        btnAddVehicle.setOnClickListener(this);
        btnGoOffline.setOnClickListener(this);
        initVehicleList();
        getTrips();
    }

    private void initVehicleList() {
        listVehicle = new ArrayList<>();
        rcvSelectVehicle.setLayoutManager(new LinearLayoutManager(drawerActivity));
        vehicleSelectionAdapter = new VehicleSelectionAdapter(drawerActivity, listVehicle) {
            @Override
            public void onVehicleSelect(int position, String vehicleId, boolean isHaveServiceTypeID) {
                if (!listVehicle.get(position).isIsSelected()) {
                    if (!isHaveServiceTypeID) {
                        Utils.showToast(drawerActivity.getResources().getString(R.string.message_vehicle_not_approved), drawerActivity);
                    } else {
                        changeCurrentVehicle(listVehicle.get(position).getId());
                        vehicleSelectionAdapter.changeSelection(position);
                    }
                }
            }

            @Override
            public void onVehicleClick(String vehicleId) {
                goToAddVehicleDetailActivity(false, vehicleId);
            }
        };
        rcvSelectVehicle.setAdapter(vehicleSelectionAdapter);

    }

    private void setPlaceFilter(String countryCode) {
        if (placeAutocompleteAdapter != null) {
            placeAutocompleteAdapter.setPlaceFilter(countryCode);
            drawerActivity.locationHelper.getLastLocation(location -> {
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    RectangularBounds latLngBounds = RectangularBounds.newInstance(latLng, latLng);
                    placeAutocompleteAdapter.setBounds(latLngBounds);
                } else {
                    Utils.showToast(drawerActivity.getResources().getString(R.string.text_location_not_found), drawerActivity);
                }
            });

        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        mapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(onBackKeyListener);
        if (drawerActivity.preferenceHelper.getIsHeatMapOn()) {
            isHeatMapLoaded = false;
            startHeatMapScheduler();
        } else {
            removeHeatMap();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        drawerActivity.setLocationListener(this);
        drawerActivity.preferenceHelper.putIsHaveTrip(false);
        drawerActivity.setNetworkListener(this);
        getProviderDetail();
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getBoolean(Const.Params.IS_FROM_NOTIFICATION)) {
            getTrips();
        }
        localBroadcastManager.registerReceiver(tripStatusReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        stopHeatMapScheduler();
        localBroadcastManager.unregisterReceiver(tripStatusReceiver);
        drawerActivity.setNetworkListener(null);
        super.onStop();
    }


    private void getVehicleTypeResponse(TypesResponse response) {

        double typeBasePrice = 0, typeDistancePrice = 0, typeTimePrice = 0;
        double typeBasePriceDistance = 0;
        boolean isTypeExist = false;
        String currency = "", typeName = "", unit = "", vehicleTypeId = "";
        if (response.isSuccess()) {
            cityType = null;
            if (TextUtils.equals(response.getCityDetail().getCountryid(), countryId)) {
                vehicleTypeList.clear();
                vehicleTypeList.addAll(response.getCityTypes());
                int vehicleTypeListSize = vehicleTypeList.size();
                for (int i = 0; i < vehicleTypeListSize; i++) {
                    vehicleTypeId = vehicleTypeList.get(i).getTypeid();
                    // check is current vehicle type available in this city
                    if (TextUtils.equals(vehicleTypeId, adminTypeId) && TextUtils.equals(vehicleTypeList.get(i).getId(), serviceTypeId)) {
                        cityType = vehicleTypeList.get(i);
                        isTypeExist = true;
                        break;
                    } else if (TextUtils.equals(vehicleTypeId, adminTypeId) && !TextUtils.equals(vehicleTypeList.get(i).getId(), serviceTypeId)) {
                        serviceTypeId = vehicleTypeList.get(i).getId();
                        typeBasePrice = vehicleTypeList.get(i).getBasePrice();
                        typeDistancePrice = vehicleTypeList.get(i).getPricePerUnitDistance();
                        typeTimePrice = vehicleTypeList.get(i).getPriceForTotalTime();
                        typeBasePriceDistance = vehicleTypeList.get(i).getBasePriceDistance();
                        unit = Utils.showUnit(drawerActivity, response.getCityDetail().getUnit());
                        currency = response.getCurrency();
                        typeName = vehicleTypeList.get(i).getTypeDetails().getTypename();
                        openVisitorTypeDialog(drawerActivity.getResources().getString(R.string.msg_visitor_type_exist), true, typeName, typeBasePrice, typeBasePriceDistance, typeDistancePrice, typeTimePrice, unit, currency);
                        isTypeExist = true;
                        break;
                    }

                }
                if (!isTypeExist) {
                    for (int i = 0; i < vehicleTypeListSize; i++) {
                        // check is have visitor type available in this city
                        if (vehicleTypeList.get(i).getTypeDetails().getServiceType() == Const.TRUE) {
                            serviceTypeId = vehicleTypeList.get(i).getId();
                            typeBasePrice = vehicleTypeList.get(i).getBasePrice();
                            typeDistancePrice = vehicleTypeList.get(i).getPricePerUnitDistance();
                            typeTimePrice = vehicleTypeList.get(i).getPriceForTotalTime();
                            typeBasePriceDistance = vehicleTypeList.get(i).getBasePriceDistance();
                            unit = Utils.showUnit(drawerActivity, response.getCityDetail().getUnit());
                            currency = response.getCurrency();
                            typeName = vehicleTypeList.get(i).getTypeDetails().getTypename();
                            openVisitorTypeDialog(drawerActivity.getResources().getString(R.string.msg_visitor_type_not_exist), true, typeName, typeBasePrice, typeBasePriceDistance, typeDistancePrice, typeTimePrice, unit, currency);
                            isTypeExist = true;
                            break;
                        }
                    }
                    if (!isTypeExist) {
                        openVisitorTypeDialog(drawerActivity.getResources().getString(R.string.msg_service_not_provide), false, typeName, typeBasePrice, typeBasePriceDistance, typeDistancePrice, typeTimePrice, unit, currency);
                    }
                }
            } else {
                openVisitorTypeDialog(drawerActivity.getResources().getString(R.string.msg_service_not_provide), false, typeName, typeBasePrice, typeBasePriceDistance, typeDistancePrice, typeTimePrice, unit, currency);
            }

        } else {
            Utils.showErrorToast(response.getErrorCode(), drawerActivity);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivTargetLocation:
                if (googleMap != null && drawerActivity.checkLocationPermission()) {
                    moveCameraFirstMyLocation(true);
                }
                break;
            case R.id.btnGoOffline:
                if (drawerActivity.preferenceHelper.getIsProviderOnline() == Const.ProviderStatus.PROVIDER_STATUS_ONLINE) {
                    setProviderIsOnline(Const.ProviderStatus.PROVIDER_STATUS_OFFLINE);
                }
                break;
            case R.id.btnGoOnlineDialog:
                if (drawerActivity.checkLocationPermission()) {
                    if (drawerActivity.preferenceHelper.getIsProviderOnline() == Const.ProviderStatus.PROVIDER_STATUS_OFFLINE) {
                        setProviderIsOnline(Const.ProviderStatus.PROVIDER_STATUS_ONLINE);
                    }
                }
                break;
            case R.id.llCreateTrip:
                if (Utils.isGpsEnable(drawerActivity)) {
                    drawerActivity.locationHelper.getLastLocation(location -> {
                        drawerActivity.currentLocation = location;
                        if (location != null) {
                            new GetCityAndCountryTask().execute();
                            openCreateTripDialog();
                        }
                    });
                } else {
                    Utils.showToast(drawerActivity.getResources().getString(R.string.msg_gps_enable), getActivity());
                }
                break;
            case R.id.ivCloseTripDialog:
                closeCreateTripDialog();
                break;
            case R.id.llRideNow:
                if (isValidTripData() && !isCreateTripPress) {
                    isCreateTripPress = true;
                    createTrip();
                }
                break;
            case R.id.ivClearTextDestAddress:
                actvCreateDesAddress.getText().clear();
                tvFareEst.setText(currencyFormat.format(0));
                break;
            case R.id.btnAction:
                if (actionIntent != null) {
                    drawerActivity.startActivity(actionIntent);
                    drawerActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    vehicleSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.btnAddVehicle:
                goToAddVehicleDetailActivity(true, null);
                break;
            default:

                break;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setUpMap();
        moveCameraFirstMyLocation(false);
        drawerActivity.setLocationListener(MapFragment.this);

    }

    private void setUpMap() {
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    /**
     * Move camera first my location.
     *
     * @param isAnimate the is animate
     */
    public void moveCameraFirstMyLocation(final boolean isAnimate) {

        drawerActivity.locationHelper.getLastLocation(location -> {
            drawerActivity.currentLocation = location;
            if (drawerActivity.currentLocation != null) {
                currentLatLng = new LatLng(drawerActivity.currentLocation.getLatitude(), drawerActivity.currentLocation.getLongitude());
                cameraPosition = new CameraPosition.Builder().target(currentLatLng).zoom(17).build();
                if (isAnimate) {
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                } else {
                    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                setMarkerOnLocation(drawerActivity.currentLocation, drawerActivity.getBearing(drawerActivity.lastLocation, drawerActivity.currentLocation), false);
            } else {
                Utils.showToast(drawerActivity.getResources().getString(R.string.text_location_not_found), drawerActivity);
            }
        });

    }

    @Override
    public void onLocationReceived(Location location) {
        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        setMarkerOnLocation(location, drawerActivity.getBearing(drawerActivity.lastLocation, location), true);
        float speed = location.getSpeed() * Const.KM_COEFFICIENT;
        if (!Float.isNaN(speed)) {
            tvSpeed.setText(drawerActivity.parseContent.singleDigit.format(speed));
        }


    }

    private float getDistanceBetweenTwoLatLng(LatLng startLatLng, LatLng endLatLang) {
        Location startLocation = new Location("start");
        Location endlocation = new Location("end");
        endlocation.setLatitude(endLatLang.latitude);
        endlocation.setLongitude(endLatLang.longitude);
        startLocation.setLatitude(startLatLng.latitude);
        startLocation.setLongitude(startLatLng.longitude);
        return startLocation.distanceTo(endlocation);

    }

    private void updateCamera(float bearing, LatLng positionLatLng) {
        isCameraIdeal = false;
        CameraPosition oldPos = googleMap.getCameraPosition();

        CameraPosition pos = CameraPosition.builder(oldPos).bearing(bearing).target(positionLatLng).zoom(17).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos), 3000, new GoogleMap.CancelableCallback() {

            @Override
            public void onFinish() {
                isCameraIdeal = true;
            }

            @Override
            public void onCancel() {
                isCameraIdeal = true;
            }
        });
    }


    private void setMarkerOnLocation(Location location, float bearing, boolean moveCamera) {

        if (googleMap != null) {
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(Utils.drawableToBitmap(ResourcesCompat.getDrawable(drawerActivity.getResources(), R.drawable.driver_car, null)));
            if (myLocationMarker == null) {
                myLocationMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title(drawerActivity.getResources().getString(R.string.text_my_location)).icon(bitmapDescriptor));
                myLocationMarker.setAnchor(0.5f, 0.5f);

            } else {

                animateMarkerToGB(myLocationMarker, new LatLng(location.getLatitude(), location.getLongitude()), new LatLngInterpolator.Linear(), bearing, moveCamera);
            }
        }
        drawerActivity.setLastLocation(location);


    }

    private void getProviderDetail() {
        if (vehicleSheetBehavior != null) {
            vehicleSheetBehavior.setPeekHeight(drawerActivity.getResources().getDimensionPixelSize(R.dimen.bottom_vehicle_sheet_pick_height));
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, drawerActivity.preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.APP_VERSION, drawerActivity.getAppVersion());
            Call<ProviderDetailResponse> call = ApiClient.getClient().create(ApiInterface.class).getProviderDetail(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<ProviderDetailResponse>() {
                @Override
                public void onResponse(Call<ProviderDetailResponse> call, Response<ProviderDetailResponse> response) {
                    if (ParseContent.getInstance().isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            Provider provider = response.body().getProvider();
                            currencyFormat = drawerActivity.currencyHelper.getCurrencyFormat(drawerActivity.preferenceHelper.getCurrencyCode());
                            drawerActivity.currentTrip.getSpeakingLanguages().clear();
                            drawerActivity.currentTrip.setSpeakingLanguages((ArrayList<String>) provider.getLanguages());
                            drawerActivity.preferenceHelper.putProviderType(provider.getProviderType());
                            drawerActivity.preferenceHelper.putRating(String.valueOf(provider.getRate()));
                            drawerActivity.preferenceHelper.putIsProviderOnline(provider.getIsActive());
                            drawerActivity.preferenceHelper.putIsApproved(provider.getIsApproved());
                            drawerActivity.preferenceHelper.putIsPartnerApprovedByAdmin(provider.getIsPartnerApprovedByAdmin());
                            drawerActivity.preferenceHelper.putIsDocumentExpire(provider.isIsDocumentsExpired());
                            drawerActivity.preferenceHelper.putProviderType(provider.getProviderType());
                            if (vehicleSelectionAdapter != null) {
                                vehicleSelectionAdapter.setVehicleChangeEnable(drawerActivity.preferenceHelper.getProviderType() != Const.ProviderStatus.PROVIDER_TYPE_PARTNER);

                            }
                            adminTypeId = provider.getAdmintypeid();
                            serviceTypeId = provider.getServiceType();
                            drawerActivity.currentTrip.setWalletCurrencyCode(provider.getWalletCurrencyCode());
                            if (provider.isIsDocumentsExpired()) {
                                updateUIForAdminApproved(false, Const.Pending.DOCUMENT_EXPIRE);
                            } else if (provider.getVehicleDetail() == null || provider.getVehicleDetail().isEmpty()) {
                                updateUIForAdminApproved(false, Const.Pending.ADD_VEHICLE);
                            } else {
                                for (VehicleDetail vehicleDetail : provider.getVehicleDetail()) {
                                    if (vehicleDetail.isIsSelected()) {
                                        drawerActivity.currentTrip.setCurrentVehicle(vehicleDetail);
                                        break;
                                    } else {
                                        drawerActivity.currentTrip.setCurrentVehicle(null);
                                    }

                                }
                                if (drawerActivity.currentTrip.getCurrentVehicle() != null) {
                                    String vehicleName = drawerActivity.currentTrip.getCurrentVehicle().getName() + " " + drawerActivity.currentTrip.getCurrentVehicle().getModel();
                                    tvCarName.setText(vehicleName);
                                    tvCarPlateNo.setText(drawerActivity.currentTrip.getCurrentVehicle().getPlateNo());
                                    TypeDetails typeDetails = response.body().getTypeDetails();
                                    if (response.body().getTypeDetails() != null) {
                                        if (typeDetails.getCountryId() != null) {
                                            countryId = typeDetails.getCountryId();
                                        }
                                        if (typeDetails.isAutoTransfer()) {
                                            drawerActivity.drawerAdapter.clearDrawerNotification(drawerActivity.getResources().getString(R.string.text_bank_detail));
                                        } else {
                                            drawerActivity.drawerAdapter.setDrawerNotification(drawerActivity.getResources().getString(R.string.text_bank_detail));
                                        }
                                        if (typeDetails.isCheckProviderWalletAmountForReceivedCashRequest() && provider.getWallet() < typeDetails.getProviderMinWalletAmountSetForReceivedCashRequest()) {
                                            String msg = drawerActivity.getResources().getString(R.string.msg_min_wallet_required) + " " + currencyFormat.format(typeDetails.getProviderMinWalletAmountSetForReceivedCashRequest());
                                            tvTagWallet.setText(msg);
                                            tvTagWallet.setVisibility(View.VISIBLE);
                                        } else {
                                            tvTagWallet.setVisibility(View.GONE);
                                        }
                                        driverRemainInfoShouldUpdate();
                                        unit = Utils.showUnit(drawerActivity, typeDetails.getUnit());
                                        ivCarImage.setVisibility(View.VISIBLE);
                                        GlideApp.with(drawerActivity.getApplicationContext()).load(IMAGE_BASE_URL + typeDetails.getTypeImageUrl()).fallback(R.drawable.ellipse).override(200, 200).dontAnimate().into(ivCarImage);
                                        GlideApp.with(drawerActivity.getApplicationContext()).load(IMAGE_BASE_URL + typeDetails.getMapPinImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.driver_car).override(drawerActivity.getResources().getDimensionPixelSize(R.dimen.vehicle_pin_width), drawerActivity.getResources().getDimensionPixelSize(R.dimen.vehicle_pin_height)).into(ivDriverCar);
                                        tvCarBasePrice.setText(currencyFormat.format(typeDetails.getBasePrice()) + Utils.validBasePriceSuffix(typeDetails.getBasePriceDistance(), unit));
                                        tvCarDistancePrice.setText(currencyFormat.format(typeDetails.getDistancePrice()) + Const.SLASH + unit);
                                        tvCarTimePrice.setText(currencyFormat.format(typeDetails.getTimePrice()) + drawerActivity.getResources().getString(R.string.text_unit_per_min));
                                        tvCarType.setText(typeDetails.getTypename());
                                        serverTime = typeDetails.getServerTime();
                                        surgeStartTime = typeDetails.getSurgeStartHour();
                                        surgeEndTime = typeDetails.getSurgeEndHour();
                                        cityTimeZone = typeDetails.getTimezone();
                                        setDriverCarPin(IMAGE_BASE_URL + typeDetails.getMapPinImageUrl());
                                    }

                                    checkIsProviderApproved(provider.getVehicleDetail());
                                    updateUIIsPartnerProvider(provider.getProviderType() == Const.ProviderStatus.PROVIDER_TYPE_PARTNER);
                                } else {
                                    updateUIForAdminApproved(false, Const.Pending.PENDING_FOR_ADMIN_APPROVAL);
                                }

                            }


                        }
                    }
                }

                @Override
                public void onFailure(Call<ProviderDetailResponse> call, Throwable t) {
                    AppLog.handleThrowable(MapFragment.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }


    private void checkProviderOnline() {
        if (Const.ProviderStatus.PROVIDER_STATUS_OFFLINE == drawerActivity.preferenceHelper.getIsProviderOnline()) {
            llCreateTrip.setVisibility(View.GONE);
            ivTargetLocation.setVisibility(View.GONE);
            drawerActivity.hideToolbarButton();
            llGoOnline.setVisibility(llNotApproved.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            btnGoOffline.setVisibility(View.GONE);
            drawerActivity.countUpdateForLocation = 0;
            drawerActivity.preferenceHelper.putCheckCountForLocation(0);
            drawerActivity.stopLocationUpdateService();
            vehicleSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            //vehicleSheetBehavior.setPeekHeight(0);

        } else {
            llGoOnline.setVisibility(View.GONE);
            btnGoOffline.setVisibility(View.VISIBLE);
            ivTargetLocation.show();
            llCreateTrip.setVisibility(drawerActivity.preferenceHelper.getIsProviderInitiateTrip() ? View.VISIBLE : View.GONE);
            drawerActivity.countUpdateForLocation = 0;
            drawerActivity.preferenceHelper.putCheckCountForLocation(0);
            drawerActivity.startLocationUpdateService();
            ivTargetLocation.animate().scaleX(1).scaleY(1).setDuration(300).start();

        }
    }

    private void getVehicleTypeOfCurrentCity(String countryName, String cityName, double cityLatitude, double cityLongitude, String subAdminCity, final boolean isForFareETA) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.COUNTRY, countryName);
            jsonObject.put(Const.Params.CITY, cityName);
            jsonObject.put(Const.Params.LATITUDE, cityLatitude);
            jsonObject.put(Const.Params.LONGITUDE, cityLongitude);
            jsonObject.put(Const.Params.SUB_ADMIN_CITY, subAdminCity);
            jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.PROVIDER_ID, drawerActivity.preferenceHelper.getProviderId());
            Call<TypesResponse> call = ApiClient.getClient().create(ApiInterface.class).getVehicleTypes(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<TypesResponse>() {
                @Override
                public void onResponse(Call<TypesResponse> call, Response<TypesResponse> response) {
                    if (ParseContent.getInstance().isSuccessful(response)) {
                        getVehicleTypeResponse(response.body());
                        if (isForFareETA) {
                            getDistanceMatrix(currentLatLng, destinationLatLng);
                        }
                    }
                }

                @Override
                public void onFailure(Call<TypesResponse> call, Throwable t) {
                    AppLog.handleThrowable(MapFragment.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }

    }

    private void openVisitorTypeDialog(String message, final boolean isVisitor, String typeName, double basePrice, double basePriceDistance, double distancePrice, double timePrice, String unit, String currency) {

        if (vehicleTypeDialog != null && vehicleTypeDialog.isShowing()) {
            return;
        }

        MyFontTextView tvDialogMessage, tvTypeBasePriceValue, tvTypeDistancePriceValue, tvTypeTimePriceValue;
        LinearLayout llTypeDetail;
        MyAppTitleFontTextView tvTypeName;
        vehicleTypeDialog = new Dialog(drawerActivity);
        vehicleTypeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        vehicleTypeDialog.setContentView(R.layout.dialog_vehicle_type_cast);
        tvDialogMessage = vehicleTypeDialog.findViewById(R.id.tvDialogMessage);
        tvDialogMessage.setText(message);
        tvTypeBasePriceValue = vehicleTypeDialog.findViewById(R.id.tvTypeBasePriceValue);
        tvTypeDistancePriceValue = vehicleTypeDialog.findViewById(R.id.tvTypeDistancePriceValue);
        tvTypeTimePriceValue = vehicleTypeDialog.findViewById(R.id.tvTypeTimePriceValue);
        tvTypeName = vehicleTypeDialog.findViewById(R.id.tvTypeName);
        llTypeDetail = vehicleTypeDialog.findViewById(R.id.llTypeDetail);
        if (isVisitor) {
            tvTypeName.setText(typeName);
            tvTypeBasePriceValue.setText(currency + " " + basePrice + Utils.validSuffix(basePriceDistance, unit));
            tvTypeDistancePriceValue.setText(currency + " " + distancePrice + Const.SLASH + unit);
            tvTypeTimePriceValue.setText(currency + " " + timePrice + drawerActivity.getResources().getString(R.string.text_unit_per_min));
            llTypeDetail.setVisibility(View.VISIBLE);
        }


        vehicleTypeDialog.findViewById(R.id.btnCastYes).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (isVisitor) {
                    vehicleTypeDialog.dismiss();
                    updateProviderType();
                } else {
                    vehicleTypeDialog.dismiss();
                    drawerActivity.finishAffinity();
                }
            }
        });
        vehicleTypeDialog.findViewById(R.id.btnCastNo).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                vehicleTypeDialog.dismiss();
            }
        });


        WindowManager.LayoutParams params = vehicleTypeDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        vehicleTypeDialog.getWindow().setAttributes(params);
        vehicleTypeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        vehicleTypeDialog.setCancelable(false);
        if (!drawerActivity.isFinishing()) {
            vehicleTypeDialog.show();
        }
    }

    private void updateProviderType() {
        JSONObject jsonObject = new JSONObject();
        Utils.showCustomProgressDialog(drawerActivity, drawerActivity.getResources().getString(R.string.msg_loading), false, null);

        try {
            jsonObject.put(Const.Params.PROVIDER_ID, drawerActivity.preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TYPE_ID, serviceTypeId);
            jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());
            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).updateType(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {
                    if (ParseContent.getInstance().isSuccessful(response)) {
                        Utils.hideCustomProgressDialog();
                        if (response.body().isSuccess()) {
                            getProviderDetail();
                        } else {
                            Utils.hideCustomProgressDialog();
                            Utils.showErrorToast(response.body().getErrorCode(), drawerActivity);
                        }

                    }

                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    AppLog.handleThrowable(MapFragment.class.getSimpleName(), t);
                    Utils.hideCustomProgressDialog();
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }

    private void animateMarkerToGB(final Marker marker, final LatLng finalPosition, final LatLngInterpolator latLngInterpolator, final float bearing, final boolean moveCamera) {
        if (marker != null) {

            final LatLng startPosition = marker.getPosition();
            final LatLng endPosition = new LatLng(finalPosition.latitude, finalPosition.longitude);

            final float startRotation = marker.getRotation();
            final LatLngInterpolator interpolator = new LatLngInterpolator.LinearFixed();

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(3000); // duration 3 second
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    try {
                        float v = animation.getAnimatedFraction();
                        LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, endPosition);
                        marker.setPosition(newPosition);
                        marker.setAnchor(0.5f, 0.5f);
                        if (isCameraIdeal && getDistanceBetweenTwoLatLng(startPosition, finalPosition) > Const.DISPLACEMENT && moveCamera) {
                            updateCamera(getBearing(startPosition, new LatLng(finalPosition.latitude, finalPosition.longitude)), newPosition);
                        }
                    } catch (Exception ex) {
                        //I don't care atm..
                    }
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
            valueAnimator.start();
        }
    }

    private void initTripStatusReceiver() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(Const.ACTION_NEW_TRIP);
        intentFilter.addAction(Const.ACTION_PROVIDER_OFFLINE);
        tripStatusReceiver = new TripStatusReceiver();
        localBroadcastManager = LocalBroadcastManager.getInstance(drawerActivity);
    }

    private void checkIsProviderApproved(List<VehicleDetail> vehicleDetail) {
        if (Const.ProviderStatus.IS_APPROVED == drawerActivity.preferenceHelper.getIsApproved()) {
            if (drawerActivity.preferenceHelper.getProviderType() == Const.ProviderStatus.PROVIDER_TYPE_PARTNER) {
                if (drawerActivity.preferenceHelper.getIsPartnerApprovedByAdmin() == Const.ProviderStatus.IS_APPROVED) {
                    if (drawerActivity.currentLocation != null) {
                        new GetCityAndCountryTask().execute();
                    }
                    checkProviderAndVehicleDocument(vehicleDetail);
                } else {
                    updateUIForAdminApproved(false, Const.Pending.PENDING_FOR_ADMIN_APPROVAL);
                }

            } else {
                if (drawerActivity.currentLocation != null) {
                    new GetCityAndCountryTask().execute();
                }
                checkProviderAndVehicleDocument(vehicleDetail);
            }

        } else {
            updateUIForAdminApproved(false, Const.Pending.PENDING_FOR_ADMIN_APPROVAL);
        }
    }

    private void getHeatMap() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, drawerActivity.preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());

            Call<HeatMapResponse> call = ApiClient.getClient().create(ApiInterface.class).getProviderHeatMap(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<HeatMapResponse>() {
                @Override
                public void onResponse(Call<HeatMapResponse> call, Response<HeatMapResponse> response) {
                    if (ParseContent.getInstance().isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            for (PickupLocations pickupLocations : response.body().getPickupLocations()) {
                                LatLng latLng = new LatLng(pickupLocations.getSourceLocation().get(0), pickupLocations.getSourceLocation().get(1));
                                heatMapLocationList.add(latLng);
                            }
                            if (heatMapLocationList.size() > 0) {
                                if (isHeatMapLoaded) {
                                    heatmapTileProvider.setData(heatMapLocationList);
                                    tileOverlay.clearTileCache();
                                } else {
                                    addHeatMap();
                                }
                            } else {
                                removeHeatMap();
                            }
                        }

                    }

                }

                @Override
                public void onFailure(Call<HeatMapResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            AppLog.handleException(TAG, e);
        }
    }

    private void addHeatMap() {
        removeHeatMap();
        int[] colors = {Color.rgb(102, 225, 0), // green
                Color.rgb(255, 0, 0)    // red
        };

        float[] startPoints = {0.2f, 1f};
        Gradient gradient = new Gradient(colors, startPoints);

        heatmapTileProvider = new HeatmapTileProvider.Builder().data(heatMapLocationList).gradient(gradient).build();
        tileOverlay = googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(heatmapTileProvider));
        isHeatMapLoaded = true;
    }

    private void removeHeatMap() {
        if (tileOverlay != null) tileOverlay.remove();
    }

    private void initHeatMapHandler() {
        heatMapHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (isAdded()) {
                    getHeatMap();
                }
            }
        };
    }

    /**
     * Start heat map scheduler.
     */
    public void startHeatMapScheduler() {
        if (!isSchedularStart) {
            heatMapScheduleService = Executors.newSingleThreadScheduledExecutor();
            heatMapScheduleService.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    Message message = heatMapHandler.obtainMessage();
                    heatMapHandler.sendMessage(message);
                }
            }, 0, Const.HEAT_MAP_SCHEDULED_SECOND, TimeUnit.SECONDS);
            isSchedularStart = true;
        }
    }

    /**
     * Stop heat map scheduler.
     */
    public void stopHeatMapScheduler() {
        if (isSchedularStart && isAdded()) {
            heatMapScheduleService.shutdown();
            try {
                if (!heatMapScheduleService.awaitTermination(60, TimeUnit.SECONDS)) {
                    heatMapScheduleService.shutdownNow();
                    if (!heatMapScheduleService.awaitTermination(60, TimeUnit.SECONDS))
                        AppLog.Log(TAG, "Pool did not terminate");
                }
            } catch (InterruptedException e) {
                AppLog.handleException(TAG, e);
                heatMapScheduleService.shutdownNow();
                Thread.currentThread().interrupt();
            }
            isSchedularStart = false;
        }
    }

    /**
     * This function is use for set driver pin on map as per set in admin panel
     **/
    private void setDriverCarPin(String pinUrl) {
        GlideApp.with(drawerActivity).asBitmap().load(pinUrl)

                .diskCacheStrategy(DiskCacheStrategy.ALL).override(drawerActivity.getResources().getDimensionPixelSize(R.dimen.vehicle_pin_width), drawerActivity.getResources().getDimensionPixelSize(R.dimen.vehicle_pin_height)).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                if (myLocationMarker != null)
                    myLocationMarker.setIcon(BitmapDescriptorFactory.fromBitmap(Utils.drawableToBitmap(ResourcesCompat.getDrawable(drawerActivity.getResources(), R.drawable.driver_car, null))));
                return true;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                if (myLocationMarker != null)
                    myLocationMarker.setIcon(BitmapDescriptorFactory.fromBitmap(resource));
                return true;
            }
        }).into(ivDriverCar);
    }

    private void openCreateTripDialog() {
        if (createTripDialog != null && createTripDialog.isShowing()) {
            return;
        }
        createTripDialog = new Dialog(drawerActivity);
        createTripDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        createTripDialog.setContentView(R.layout.dialog_create_trip);

        ivCloseTripDialog = createTripDialog.findViewById(R.id.ivCloseTripDialog);
        tvCreateSrcAddress = createTripDialog.findViewById(R.id.tvCreateSrcAddress);
        tvFareEst = createTripDialog.findViewById(R.id.tvFareEst);
        actvCreateDesAddress = createTripDialog.findViewById(R.id.actvCreateDesAddress);
        edtCreateFirstName = createTripDialog.findViewById(R.id.edtCreateFirstName);
        edtCreateLastName = createTripDialog.findViewById(R.id.edtCreateLastName);
        edtCreateEmail = createTripDialog.findViewById(R.id.edtCreateEmail);
        edtCreatePhone = createTripDialog.findViewById(R.id.edtCreatePhone);
        edtCreatePhoneCode = createTripDialog.findViewById(R.id.edtCreatePhoneCode);
        edtCreatePhoneCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCountryCodeDialog();
            }
        });
        llRideNow = createTripDialog.findViewById(R.id.llRideNow);
        ivClearTextDestAddress = createTripDialog.findViewById(R.id.ivClearTextDestAddress);

        tvFareEst.setText(currencyFormat.format(0));
        initDestinationAutocomplete();
        initPhoneCodeEditText();
        ivCloseTripDialog.setOnClickListener(this);
        llRideNow.setOnClickListener(this);
        ivClearTextDestAddress.setOnClickListener(this);
        edtCreatePhoneCode.setHint(drawerActivity.preferenceHelper.getCountryPhoneCode());
        getCountryCodeList(drawerActivity.preferenceHelper.getCountryPhoneCode());
        tvCreateSrcAddress.setText(pickupAddress);

        WindowManager.LayoutParams params = createTripDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        createTripDialog.getWindow().setAttributes(params);
        createTripDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        createTripDialog.setCancelable(false);
        if (!drawerActivity.isFinishing()) {
            createTripDialog.show();
        }

        edtCreatePhone.setLongClickable(false);
        edtCreatePhone.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private void closeCreateTripDialog() {
        if (createTripDialog != null && createTripDialog.isShowing()) {
            InputMethodManager imm = (InputMethodManager) drawerActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = createTripDialog.getCurrentFocus();
            if (view == null) {
                view = new View(drawerActivity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            createTripDialog.dismiss();
            createTripDialog = null;
        }
    }

    private void initDestinationAutocomplete() {
        actvCreateDesAddress.setAdapter(placeAutocompleteAdapter);
        actvCreateDesAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideKeybord();
                setDestinationAddress(actvCreateDesAddress.getText().toString());
                getLocationFromPlaceId(placeAutocompleteAdapter.getPlaceId(position));
            }
        });
        actvCreateDesAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    ivClearTextDestAddress.setVisibility(View.VISIBLE);
                } else {
                    ivClearTextDestAddress.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setDestinationAddress(String destinationAddress) {
        actvCreateDesAddress.setFocusable(false);
        actvCreateDesAddress.setFocusableInTouchMode(false);
        this.destinationAddress = destinationAddress;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            actvCreateDesAddress.setText(destinationAddress, false);
        } else {
            actvCreateDesAddress.setText(destinationAddress);
        }
        actvCreateDesAddress.setFocusable(true);
        actvCreateDesAddress.setFocusableInTouchMode(true);
    }


    /**
     * It is use for get Location from address using placeId through PlaceSDK Google Play
     * Service
     *
     * @param placeId
     */
    private void getLocationFromPlaceId(String placeId) {
        if (!TextUtils.isEmpty(placeId) && placeAutocompleteAdapter != null) {

            placeAutocompleteAdapter.getFetchPlaceRequest(placeId, new OnSuccessListener<FetchPlaceResponse>() {
                @Override
                public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                    Place place = fetchPlaceResponse.getPlace();
                    destinationLatLng = place.getLatLng();
                    if (destinationLatLng != null) {
                        getDistanceMatrix(currentLatLng, destinationLatLng);
                    }
                    CurrentTrip.getInstance().setAutocompleteSessionToken(AutocompleteSessionToken.newInstance());
                }
            });
        } else {
            Utils.showToast("Place not" + " found.", drawerActivity);
        }

    }

    private void hideKeybord() {
        InputMethodManager imm = (InputMethodManager) drawerActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(actvCreateDesAddress.getWindowToken(), 0);
    }

    private void initPhoneCodeEditText() {
        edtCreatePhoneCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                edtCreatePhoneCode.setSelection(s.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean isValidTripData() {
        Validator emailValidation = FieldValidation.isEmailValid(drawerActivity, edtCreateEmail.getText().toString().trim());
        String msg = null;
        if (TextUtils.isEmpty(edtCreateFirstName.getText().toString().trim())) {
            msg = drawerActivity.getResources().getString(R.string.msg_enter_name);
            edtCreateFirstName.setError(msg);
            edtCreateFirstName.requestFocus();
        } else if (!emailValidation.isValid()) {
            msg = emailValidation.getErrorMsg();
            edtCreateEmail.setError(msg);
            edtCreateEmail.requestFocus();
        } else if (TextUtils.isEmpty(edtCreatePhoneCode.getText().toString().trim())) {
            msg = drawerActivity.getResources().getString(R.string.message_enter_country_phonecode);
            edtCreatePhoneCode.setError(msg);
            edtCreatePhoneCode.requestFocus();
        } else if (!Utils.isValidPhoneNumber(edtCreatePhone.getText().toString(), drawerActivity.preferenceHelper.getMinimumPhoneNumberLength(), drawerActivity.preferenceHelper.getMaximumPhoneNumberLength())) {
            msg = getString(R.string.msg_enter_valid_number);
            edtCreatePhone.requestFocus();
            edtCreatePhone.setError(msg);
        }
        return TextUtils.isEmpty(msg);
    }

    private void createTrip() {
        if (cityType != null) {
            Utils.showCustomProgressDialog(drawerActivity, "", false, null);
            JSONObject jsonObject = new JSONObject();
            try {
                if (!TextUtils.isEmpty(actvCreateDesAddress.getText().toString()) && destinationLatLng != null) {
                    jsonObject.put(Const.Params.DEST_LATITUDE, destinationLatLng.latitude);
                    jsonObject.put(Const.Params.DEST_LONGITUDE, destinationLatLng.longitude);
                    jsonObject.put(Const.Params.DESTINATION_ADDRESS, destinationAddress);
                } else {
                    jsonObject.put(Const.Params.DESTINATION_ADDRESS, "");
                    jsonObject.put(Const.Params.DEST_LATITUDE, "");
                    jsonObject.put(Const.Params.DEST_LONGITUDE, "");
                }

                if (!TextUtils.isEmpty(pickupAddress)) {
                    jsonObject.put(Const.Params.PICK_UP_LATITUDE, currentLatLng.latitude);
                    jsonObject.put(Const.Params.PICK_UP_LONGITUDE, currentLatLng.longitude);
                    jsonObject.put(Const.Params.SOURCE_ADDRESS, pickupAddress);
                } else {
                    Utils.showToast(drawerActivity.getResources().getString(R.string.msg_plz_select_valid_source_address), drawerActivity);
                    return;
                }
                jsonObject.put(Const.Params.PROVIDER_ID, drawerActivity.preferenceHelper.getProviderId());
                jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());

                jsonObject.put(Const.Params.FIRST_NAME, edtCreateFirstName.getText().toString());
                jsonObject.put(Const.Params.LAST_NAME, edtCreateLastName.getText().toString());
                jsonObject.put(Const.Params.EMAIL, edtCreateEmail.getText().toString());
                jsonObject.put(Const.Params.COUNTRY_PHONE_CODE, edtCreatePhoneCode.getText().toString());
                jsonObject.put(Const.Params.PHONE, edtCreatePhone.getText().toString());
                jsonObject.put(Const.Params.TIMEZONE, Utils.getTimeZoneName());
                jsonObject.put(Const.Params.PAYMENT_MODE, Const.CASH);
                jsonObject.put(Const.Params.SERVICE_TYPE_ID, serviceTypeId);
                if (cityType != null) {
                    SurgeResult surgeResult = checkSurgeTimeApply(cityType.getSurgeHours(), serverTime, cityTimeZone, false, 0);
                    jsonObject.put(Const.Params.IS_SURGE_HOURS, surgeResult.getIsSurge());
                    jsonObject.put(Const.Params.SURGE_MULTIPLIER, surgeResult.getSurgeMultiplier());
                } else {
                    jsonObject.put(Const.Params.IS_SURGE_HOURS, Const.FALSE);
                }


                Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).providerCreateTrip(ApiClient.makeJSONRequestBody(jsonObject));
                call.enqueue(new Callback<IsSuccessResponse>() {
                    @Override
                    public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {
                        if (ParseContent.getInstance().isSuccessful(response)) {
                            Utils.hideCustomProgressDialog();
                            if (response.body().isSuccess()) {
                                closeCreateTripDialog();
                                getTrips();
                            } else {
                                Utils.showErrorToast(response.body().getErrorCode(), drawerActivity);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                        AppLog.handleThrowable(MapFragment.class.getSimpleName(), t);
                    }
                });

            } catch (JSONException e) {
                AppLog.handleException(TAG, e);
            }
        } else {
            Utils.showToast(drawerActivity.getResources().getString(R.string.text_no_type), drawerActivity);
        }
        isCreateTripPress = false;
    }

    private SurgeResult checkSurgeTimeApply(List<SurgeTime> surgeTime, String serverTime, String timeZoneString, boolean isScheduleBooking, long scheduleBookingTime) {

        SurgeResult surgeResult = new SurgeResult();
        if (surgeTime != null) {
            try {
                Calendar serverTimeCalendar = Calendar.getInstance();
                if (isScheduleBooking) {
                    serverTimeCalendar.setTimeInMillis(scheduleBookingTime);
                } else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Const.DATE_TIME_FORMAT_WEB, Locale.US);
                    Date date = simpleDateFormat.parse(serverTime);
                    TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
                    serverTimeCalendar.setTimeInMillis(date.getTime() + (timeZone.getOffset(date.getTime())));
                }
                boolean isSurge = false;
                int dayOfWeek = serverTimeCalendar.get(Calendar.DAY_OF_WEEK) - 1;
                for (SurgeTime timeItem : surgeTime) {
                    if (timeItem.getDay() == dayOfWeek) {
                        if (timeItem.isSurge()) {
                            if (timeItem.getDayTime().isEmpty()) {
                                isSurge = false;
                            } else {
                                for (DayTime dayTime : timeItem.getDayTime()) {

                                    String[] open = dayTime.getStartTime().split(":");
                                    String[] closed = dayTime.getEndTime().split(":");

                                    Calendar openCalendar = Calendar.getInstance();
                                    openCalendar.setTimeInMillis(serverTimeCalendar.getTimeInMillis());
                                    openCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(open[0]));
                                    openCalendar.set(Calendar.MINUTE, Integer.valueOf(open[1]));
                                    openCalendar.set(Calendar.SECOND, 0);


                                    Calendar closedCalendar = Calendar.getInstance();
                                    closedCalendar.setTimeInMillis(serverTimeCalendar.getTimeInMillis());
                                    closedCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(closed[0]));
                                    closedCalendar.set(Calendar.MINUTE, Integer.valueOf(closed[1]));
                                    closedCalendar.set(Calendar.SECOND, 0);
                                    if (serverTimeCalendar.after(openCalendar) && serverTimeCalendar.before(closedCalendar)) {
                                        surgeResult.setSurgeMultiplier(dayTime.getMultiplier());
                                        isSurge = true;

                                        break;
                                    } else {
                                        isSurge = false;
                                    }

                                }

                            }

                        } else {
                            isSurge = false;
                            break;
                        }
                    }

                }


                if (isSurge) {
                    surgeResult.setSurgeMultiplier(cityType.getRichAreaSurgeMultiplier() > surgeResult.getSurgeMultiplier() ? cityType.getRichAreaSurgeMultiplier() : surgeResult.getSurgeMultiplier());
                    surgeResult.setIsSurge(surgeResult.getSurgeMultiplier() != 1.0 && surgeResult.getSurgeMultiplier() > 0.0 ? Const.TRUE : Const.FALSE);
                } else if (cityType.getRichAreaSurgeMultiplier() != 1.0 && cityType.getRichAreaSurgeMultiplier() > 0.0) {
                    surgeResult.setSurgeMultiplier(cityType.getRichAreaSurgeMultiplier());
                    surgeResult.setIsSurge(Const.TRUE);
                } else {
                    surgeResult.setIsSurge(Const.FALSE);
                }

                if (CurrentTrip.getInstance().getTripType() == Const.TripType.AIRPORT || CurrentTrip.getInstance().getTripType() == Const.TripType.CITY || CurrentTrip.getInstance().getTripType() == Const.TripType.ZONE) {
                    surgeResult.setIsSurge(Const.FALSE);
                }
            } catch (ParseException e) {
                AppLog.handleException(MapFragment.class.getName(), e);
            }

        }
        return surgeResult;
    }


    /**
     * use for get duration and distance between source and destination.
     *
     * @param srcLatLng
     * @param destLatLng
     */
    private void getDistanceMatrix(LatLng srcLatLng, LatLng destLatLng) {
        Utils.showCustomProgressDialog(drawerActivity, "", false, null);
        String origins = srcLatLng.latitude + "," + srcLatLng.longitude;
        String destination = destLatLng.latitude + "," + destLatLng.longitude;
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Const.google.ORIGINS, origins);
        hashMap.put(Const.google.DESTINATIONS, destination);
        hashMap.put(Const.google.KEY, drawerActivity.preferenceHelper.getGoogleServerKey());
        ApiInterface apiInterface = new ApiClient().changeApiBaseUrl(Const.GOOGLE_API_URL).create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getGoogleDistanceMatrix(hashMap);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (ParseContent.getInstance().isSuccessful(response)) {
                    HashMap<String, String> map = null;
                    try {
                        map = drawerActivity.parseContent.parsDistanceMatrix(response.body().string());

                        if (map != null) {
                            String distance = map.get(Const.google.DISTANCE);
                            String timeSecond = map.get(Const.google.DURATION);
                            Double timeMinute = Double.valueOf(timeSecond) / 60;
                            double tripDistance = Double.valueOf(distance);
                            double tripTime = Double.valueOf(timeSecond);
                            getFareEstimate(tripDistance, tripTime, serviceTypeId);
                        } else {
                            Utils.hideCustomProgressDialog();
                        }
                    } catch (Exception e) {
                        Utils.hideCustomProgressDialog();
                        e.printStackTrace();
                    }
                } else {
                    Utils.hideCustomProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideCustomProgressDialog();
                AppLog.handleThrowable(MapFragment.class.getSimpleName(), t);
            }
        });
    }


    private void getFareEstimate(double distance, double time, String serviceType) {

        CurrentTrip.getInstance().setTripType(Const.TripType.NORMAL);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.DISTANCE, distance);
            jsonObject.put(Const.Params.TIME, time);
            jsonObject.put(Const.Params.SERVICE_TYPE_ID, serviceType);
            if (cityType != null) {
                SurgeResult surgeResult = checkSurgeTimeApply(cityType.getSurgeHours(), serverTime, cityTimeZone, false, 0);
                jsonObject.put(Const.Params.IS_SURGE_HOURS, surgeResult.getIsSurge());
            } else {
                jsonObject.put(Const.Params.IS_SURGE_HOURS, Const.FALSE);
            }
            jsonObject.put(Const.Params.PICKUP_LAT, currentLatLng.latitude);
            jsonObject.put(Const.Params.PICKUP_LON, currentLatLng.longitude);
            jsonObject.put(Const.Params.DEST_LAT, destinationLatLng.latitude);
            jsonObject.put(Const.Params.DEST_LON, destinationLatLng.longitude);

            Call<ETAResponse> call = ApiClient.getClient().create(ApiInterface.class).getETAForeTrip(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<ETAResponse>() {
                @Override
                public void onResponse(Call<ETAResponse> call, Response<ETAResponse> response) {
                    if (ParseContent.getInstance().isSuccessful(response)) {
                        ETAResponse etaResponse = response.body();
                        if (etaResponse.isSuccess()) {
                            tvFareEst.setText(currencyFormat.format(response.body().getEstimatedFare()));
                            CurrentTrip.getInstance().setTripType(Integer.valueOf(etaResponse.getTripType()));
                            Utils.hideCustomProgressDialog();
                        } else {
                            Utils.hideCustomProgressDialog();
                            tvFareEst.setText(currencyFormat.format(0));
                        }

                    }

                }

                @Override
                public void onFailure(Call<ETAResponse> call, Throwable t) {
                    AppLog.handleThrowable(MapFragment.class.getSimpleName(), t);
                }
            });
        } catch (Exception e) {
            AppLog.handleException(TAG, e);
        }
    }


    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }


    /**
     * this method update UI or navigation of activity according to vehicle document , driver
     * document and admin approval
     *
     * @param isApproved
     * @param pending
     */
    private boolean updateUIForAdminApproved(boolean isApproved, int pending) {
        //updateUIVehicleDetail(isApproved);
        drawerActivity.drawerAdapter.clearDrawerNotification(drawerActivity.getResources().getString(R.string.text_manage_vehicles));
        drawerActivity.drawerAdapter.clearDrawerNotification(drawerActivity.getResources().getString(R.string.text_Document));
        if (isApproved) {
            btnGoOffline.setVisibility(View.VISIBLE);
            llVehicleDetail.setVisibility(View.VISIBLE);
            llGoOnline.setVisibility(View.GONE);
            llNotApproved.setVisibility(View.GONE);
            btnPendingWorkAction.setVisibility(View.GONE);
            checkProviderOnline();
        } else {
            btnGoOffline.setVisibility(View.GONE);
            llVehicleDetail.setVisibility(View.GONE);
            llGoOnline.setVisibility(View.GONE);
            llNotApproved.setVisibility(View.VISIBLE);
            drawerActivity.hideToolbarButton();
            ivTargetLocation.setVisibility(View.GONE);
            llCreateTrip.setVisibility(View.GONE);
            String msg = "", btnText = "";
            switch (pending) {
                case Const.Pending.PENDING_FOR_ADMIN_APPROVAL:
                    msg = drawerActivity.getResources().getString(R.string.msg_under_review);
                    btnText = drawerActivity.getResources().getString(R.string.text_contact_us);
                    actionIntent = new Intent(drawerActivity, ContactUsActivity.class);
                    btnPendingWorkAction.setVisibility(View.VISIBLE);
                    break;
                case Const.Pending.ADD_VEHICLE:
                    if (drawerActivity.currentTrip.getCurrentVehicle() == null) {
                        msg = drawerActivity.getResources().getString(R.string.message_add_vehicle);
                        btnText = drawerActivity.getResources().getString(R.string.text_add_vehicle);
                        actionIntent = new Intent(drawerActivity, AddVehicleDetailActivity.class);
                        actionIntent.putExtra(Const.IS_ADD_VEHICLE, true);
                        actionIntent.putExtra(Const.VEHICLE_ID, "");
                        if (vehicleSheetBehavior != null) {
                            vehicleSheetBehavior.setPeekHeight(0);
                        }
                    } else {
                        msg = drawerActivity.getResources().getString(R.string.message_edit_vehicle);
                        btnText = drawerActivity.getResources().getString(R.string.text_edit_vehicle);
                        actionIntent = null;
                    }
                    btnPendingWorkAction.setVisibility(View.VISIBLE);
                    break;
                case Const.Pending.DOCUMENT_EXPIRE:
                    if (drawerActivity.currentTrip.getCurrentVehicle() != null) {
                        drawerActivity.drawerAdapter.setDrawerNotification(drawerActivity.getResources().getString(R.string.text_manage_vehicles));
                        if (!drawerActivity.currentTrip.getCurrentVehicle().getIsDocumentUploaded()) {
                            msg = drawerActivity.getResources().getString(R.string.message_vehicle_document_uploded);
                        } else if (drawerActivity.currentTrip.getCurrentVehicle().isIsDocumentsExpired()) {
                            msg = drawerActivity.getResources().getString(R.string.message_vehicle_document_expire);
                        }
                        if (drawerActivity.preferenceHelper.getProviderType() == Const.ProviderStatus.PROVIDER_TYPE_PARTNER) {
                            btnText = drawerActivity.getResources().getString(R.string.text_contact_us);
                            actionIntent = new Intent(drawerActivity, ContactUsActivity.class);
                        } else {
                            btnText = drawerActivity.getResources().getString(R.string.text_update);
                            actionIntent = null;
                        }
                        btnPendingWorkAction.setVisibility(View.VISIBLE);
                    }
                    if (drawerActivity.preferenceHelper.getIsDocumentExpire()) {
                        msg = drawerActivity.getResources().getString(R.string.message_document_expire);
                        actionIntent = new Intent(drawerActivity, DocumentActivity.class);
                        actionIntent.putExtra(Const.IS_CLICK_INSIDE_DRAWER, true);
                        btnPendingWorkAction.setVisibility(View.VISIBLE);
                    }
                    break;
                default:

                    break;
            }
            btnPendingWorkAction.setText(btnText);
            tvAdminMessage.setText(msg);
        }

        return isApproved;

    }

    private void checkProviderAndVehicleDocument(List<VehicleDetail> vehicleDetail) {
        if (drawerActivity.drawerAdapter != null) {
            boolean isDocumentValid;
            if (drawerActivity.preferenceHelper.getIsDocumentExpire()) {
                isDocumentValid = false;
            } else if (drawerActivity.currentTrip.getCurrentVehicle() == null) {
                isDocumentValid = false;
            } else if (!drawerActivity.currentTrip.getCurrentVehicle().getIsDocumentUploaded()) {
                isDocumentValid = false;
            } else {
                isDocumentValid = !drawerActivity.currentTrip.getCurrentVehicle().isIsDocumentsExpired();
            }
            if (updateUIForAdminApproved(isDocumentValid, Const.Pending.DOCUMENT_EXPIRE)) {
                checkProviderOnline();
            } else {
                if (drawerActivity.preferenceHelper.getIsProviderOnline() == Const.ProviderStatus.PROVIDER_STATUS_ONLINE) {
                    setProviderIsOnline(Const.ProviderStatus.PROVIDER_STATUS_OFFLINE);
                }
            }
            drawerActivity.drawerAdapter.notifyDataSetChanged();
        }

    }

    /**
     * this method is used for get formatted address from location using Geocode Api from Google
     *
     * @param latLng
     */
    private void getGeocodeSourcesAddressFromLocation(Location latLng) {
        Utils.showCustomProgressDialog(drawerActivity, "", false, null);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Const.google.LAT_LNG, latLng.getLatitude() + "," + latLng.getLongitude());
        hashMap.put(Const.google.KEY, drawerActivity.preferenceHelper.getGoogleServerKey());
        ApiInterface apiInterface = new ApiClient().changeApiBaseUrl(Const.GOOGLE_API_URL).create(ApiInterface.class);
        Call<ResponseBody> bodyCall = apiInterface.getGoogleGeocode(hashMap);
        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (ParseContent.getInstance().isSuccessful(response)) {
                    Utils.hideCustomProgressDialog();
                    HashMap<String, String> hashMapDest = null;
                    try {
                        hashMapDest = drawerActivity.parseContent.parsGeocode(response.body().string());
                        if (hashMapDest != null) {
                            countryName = hashMapDest.get(Const.google.COUNTRY);
                            String admin = hashMapDest.get(Const.google.ADMINISTRATIVE_AREA_LEVEL_1);
                            String subadmin = hashMapDest.get(Const.google.ADMINISTRATIVE_AREA_LEVEL_2);
                            pickupAddress = hashMapDest.get(Const.google.FORMATTED_ADDRESS);
                            if (tvCreateSrcAddress != null)
                                tvCreateSrcAddress.setText(Utils.trimString(pickupAddress));
                            if (admin != null) {
                                providerCurrentCity = admin;
                            } else if (subadmin != null) {
                                providerCurrentCity = subadmin;
                            } else {
                                providerCurrentCity = countryName;
                            }
                            getVehicleTypeOfCurrentCity(countryName, providerCurrentCity, Double.parseDouble(hashMapDest.get(Const.google.LAT)), Double.parseDouble(hashMapDest.get(Const.google.LNG)), admin, false);
                            setPlaceFilter(hashMapDest.get(Const.google.COUNTRY_CODE));
                        }
                    } catch (IOException e) {
                        AppLog.handleThrowable(MapFragment.class.getSimpleName(), e);
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                AppLog.handleThrowable(MapFragment.class.getSimpleName(), t);
            }
        });
    }


    private void updateUIIsPartnerProvider(boolean isPartnerProvider) {
        if (isAdded()) {
            ViewGroup.LayoutParams layoutParams = ivCarImage.getLayoutParams();
            if (isPartnerProvider) {
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

                ivCarImage.setBackground(AppCompatResources.getDrawable(drawerActivity, R.drawable.round_rect_full));
                btnAddVehicle.setVisibility(View.GONE);

            } else {
                layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.dimen_provider_vehicle_image_size);

                ivCarImage.setBackground(AppCompatResources.getDrawable(drawerActivity, R.drawable.round_rect));
                btnAddVehicle.setVisibility(View.VISIBLE);
            }

            ivCarImage.setLayoutParams(layoutParams);
        }
    }


    /**
     * Sets provider is online.
     *
     * @param setOnline the set online
     */
    public void setProviderIsOnline(int setOnline) {
        JSONObject jsonObject = new JSONObject();
        Utils.showCustomProgressDialog(drawerActivity, getResources().getString(R.string.msg_loading), false, null);
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, drawerActivity.preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.IS_ACTIVE, setOnline);

            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).toggleState(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {
                    if (ParseContent.getInstance().isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            drawerActivity.preferenceHelper.putIsProviderOnline(response.body().getIsActive());
                            Utils.hideCustomProgressDialog();
                            checkProviderOnline();
                            if (response.body().getIsActive() == Const.TRUE) {
                                checkHavePowerManager();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    AppLog.handleThrowable(MainDrawerActivity.class.getSimpleName(), t);
                }
            });


        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }


    public void getTrips() {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, drawerActivity.preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());
            Call<TripsResponse> call = ApiClient.getClient().create(ApiInterface.class).getTrips(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<TripsResponse>() {
                @Override
                public void onResponse(Call<TripsResponse> call, Response<TripsResponse> response) {

                    if (ParseContent.getInstance().isSuccessful(response)) {
                        if (drawerActivity.parseContent.parseTrips(response.body())) {
                            if (isAdded()) {
                                drawerActivity.goToTripFragment();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<TripsResponse> call, Throwable t) {
                    AppLog.handleThrowable(MainDrawerActivity.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }


    private void initVehicleBottomSheet() {

        final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llBlackView.getLayoutParams();
        final int llHeight = layoutParams.height;
        vehicleSheetBehavior = BottomSheetBehavior.from(llCarDetails);
        llVehicleTag.setOnClickListener(this);
        vehicleSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    if (listVehicle.isEmpty()) {
                        getProviderVehicleList();
                    } else {
                        driverRemainInfoShouldUpdate();
                    }
                }
                if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                    ivTargetLocation.animate().scaleX(0).scaleY(0).setDuration(300).start();
                    ivTargetLocation.setClickable(false);
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    ivTargetLocation.setClickable(true);
                    ivTargetLocation.animate().scaleX(1).scaleY(1).setDuration(300).start();

                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                float offset = 1 - slideOffset;
                llVehicleTag.setAlpha(offset);
                llVehicleTag.setScaleX(offset);
                llVehicleTag.setScaleY(offset);
                layoutParams.height = (int) (llHeight * offset);
                llBlackView.setLayoutParams(layoutParams);
            }
        });
    }

    private void getProviderVehicleList() {
        Utils.showCustomProgressDialog(drawerActivity, "", false, null);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(Const.Params.PROVIDER_ID, drawerActivity.preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());

            Call<VehiclesResponse> call = ApiClient.getClient().create(ApiInterface.class).getVehicles(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<VehiclesResponse>() {
                @Override
                public void onResponse(Call<VehiclesResponse> call, Response<VehiclesResponse> response) {
                    if (drawerActivity.parseContent.isSuccessful(response)) {

                        if (response.body().isSuccess()) {
                            listVehicle.clear();
                            listVehicle.addAll(response.body().getVehicleList());
                            vehicleSelectionAdapter.notifyDataSetChanged();
                            driverRemainInfoShouldUpdate();
                        }

                        Utils.hideCustomProgressDialog();
                    }

                }

                @Override
                public void onFailure(Call<VehiclesResponse> call, Throwable t) {
                    AppLog.handleThrowable(MapFragment.class.getSimpleName(), t);
                }
            });


        } catch (JSONException e) {
            AppLog.handleException("GetProviderVehicleList", e);
        }
    }

    private void changeCurrentVehicle(String vehicleId) {
        Utils.showCustomProgressDialog(drawerActivity, "", false, null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, drawerActivity.preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.VEHICLE_ID, vehicleId);


            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).changeCurrentVehicle(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {
                    if (drawerActivity.parseContent.isSuccessful(response)) {
                        Utils.hideCustomProgressDialog();
                        getProviderDetail();

                    }

                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    Utils.hideCustomProgressDialog();
                    AppLog.handleThrowable(MapFragment.class.getSimpleName(), t);
                }
            });

        } catch (JSONException e) {
            AppLog.handleException("change vehicle", e);
        }
    }

    @Override
    public void onNetwork(boolean isConnected) {
        if (isConnected) {
            getTrips();
        } else {
            Utils.hideCustomProgressDialog();
        }


    }

    /**
     * Go to add vehicle detail activity.
     *
     * @param isAddVehicle the is add vehicle
     * @param vehicleId    the vehicle id
     */
    public void goToAddVehicleDetailActivity(boolean isAddVehicle, String vehicleId) {
        Intent intent = new Intent(drawerActivity, AddVehicleDetailActivity.class);
        intent.putExtra(Const.IS_ADD_VEHICLE, isAddVehicle);
        intent.putExtra(Const.VEHICLE_ID, vehicleId);
        startActivityForResult(intent, Const.REQUEST_ADD_VEHICLE);
        drawerActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_ADD_VEHICLE) {
            getProviderVehicleList();
        }

    }

    private void openCountryCodeDialog() {

        CustomCountryDialog customCountryDialog = new CustomCountryDialog(drawerActivity, null, drawerActivity.parseContent.getRawCountryCodeList(), true) {
            @Override
            public void onSelect(int position, ArrayList<Country> filterList) {


            }

            @Override
            public void onSelectItem(int position, ArrayList<CountryList> filterList) {
                if (createTripDialog != null) {
                    setCountry(position);
                    dismiss();
                }

            }
        };
        if (!drawerActivity.isFinishing()) {
            customCountryDialog.show();
        }


    }

    private void setCountry(int position) {
        country = codeArrayList.get(position);
        edtCreatePhoneCode.setText(country.getCountryPhoneCode());
    }

    private void getCountryCodeList(String countryPhCode) {
        int countryListSize = codeArrayList.size();
        for (int i = 0; i < countryListSize; i++) {
            if (TextUtils.equals(codeArrayList.get(i).getCountryName(), drawerActivity.preferenceHelper.getCountryPhoneCode())) {
                if (codeArrayList.get(i).getCountryPhoneCode().toUpperCase().startsWith(countryPhCode.toUpperCase())) {
                    setCountry(i);
                    return;
                }
            }
        }
        setCountry(0);

    }

    /**
     * check driver selected vehicle document is expire or insufficient wallet amount for cash
     * request accepted
     */
    private void driverRemainInfoShouldUpdate() {
        boolean isRequiredUpdate = false;
        for (VehicleDetail vehicleDetail : listVehicle) {
            if (vehicleDetail.isIsSelected()) {
                if (vehicleDetail.isIsDocumentsExpired()) {
                    isRequiredUpdate = true;
                    break;
                }
            }
        }
        if (isRequiredUpdate) {
            updateUiForDriverRemainInfo(isRequiredUpdate);
        } else {
            updateUiForDriverRemainInfo(tvTagWallet.getVisibility() == View.VISIBLE);
        }


    }

    /**
     * alert to driver for required info is remain to update
     *
     * @param isRequiredUpdate is if its required
     */
    private void updateUiForDriverRemainInfo(boolean isRequiredUpdate) {
        if (isRequiredUpdate) {
            tvYourVehicle.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(drawerActivity, R.drawable.info_red_icon), null);
        } else {
            tvYourVehicle.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        }
    }

    private void checkHavePowerManager() {
        if (!drawerActivity.preferenceHelper.getIsAskBackgroundAppRestriction()) {
            boolean isFoundIntent = false;
            for (Intent intent : Const.POWERMANAGER_INTENTS) {
                if (drawerActivity.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                    isFoundIntent = true;
                    CustomDialogBigLabel customDialogBigLabel = new CustomDialogBigLabel(drawerActivity, getString(R.string.text_background_restriction), getString(R.string.msg_background_restriction), getString(R.string.text_ok), getString(R.string.text_cancel)) {
                        final Intent intentFound = intent;

                        @Override
                        public void positiveButton() {
                            drawerActivity.preferenceHelper.putIsAskBackgroundAppRestriction(true);
                            try {
                                startActivity(intentFound);
                            } catch (Exception e) {
                                Utils.showToast(e.getMessage(), drawerActivity);
                            }
                            dismiss();
                        }

                        @Override
                        public void negativeButton() {
                            dismiss();
                        }
                    };
                    customDialogBigLabel.show();
                    customDialogBigLabel.setCancelable(false);
                    break;
                }
            }
            if (!isFoundIntent) {
                drawerActivity.preferenceHelper.putIsAskBackgroundAppRestriction(true);
            }
        }
    }

    @Override
    public void onDestroyView() {
        if (googleMap != null) {
            googleMap.clear();
            myLocationMarker = null;
        }
        if (mapView != null) {
            mapView.onDestroy();
            mapView = null;
        }
        super.onDestroyView();
    }

    /**
     * This Receiver receive new trip
     */
    private class TripStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!drawerActivity.isFinishing() && isAdded()) {
                switch (intent.getAction()) {
                    case Const.ACTION_NEW_TRIP:
                        getTrips();
                        break;
                    case Const.ACTION_PROVIDER_OFFLINE:
                        drawerActivity.preferenceHelper.putIsProviderOnline(Const.ProviderStatus.PROVIDER_STATUS_OFFLINE);
                        checkProviderOnline();
                        break;
                    default:

                        break;
                }
            }
        }
    }

    /**
     * The type Get city and country task.
     */
    protected class GetCityAndCountryTask extends AsyncTask<String, Void, Address> {
        /**
         * The Lat.
         */
        double lat;
        /**
         * The Lng.
         */
        double lng;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (drawerActivity.currentLocation != null) {
                lat = drawerActivity.currentLocation.getLatitude();
                lng = drawerActivity.currentLocation.getLongitude();
            }
        }

        @Override
        protected Address doInBackground(String... params) {

            Geocoder geocoder = new Geocoder(drawerActivity, new Locale("en_US"));
            try {
                List<Address> addressList = geocoder.getFromLocation(lat, lng, 1);
                if (addressList != null && !addressList.isEmpty()) {
                    return addressList.get(0);
                }
            } catch (IOException e) {
                AppLog.handleException(TAG, e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Address address) {
            StringBuilder sb = new StringBuilder();
            if (address != null) {
                String admin = address.getSubAdminArea();
                countryName = address.getCountryName();
                if (address.getMaxAddressLineIndex() > 0) {
                    int addressLineSize = address.getMaxAddressLineIndex();
                    for (int i = 0; i < addressLineSize; i++) {
                        sb.append(address.getAddressLine(i)).append(",").append("\n");
                    }
                    sb.append(address.getCountryName());
                } else {
                    sb.append(address.getAddressLine(0));
                }
                String strAddress = sb.toString();
                strAddress = strAddress.replace(",null", "");
                strAddress = strAddress.replace("null", "");
                strAddress = strAddress.replace("Unnamed", "");

                pickupAddress = strAddress;
                if (tvCreateSrcAddress != null)
                    tvCreateSrcAddress.setText(Utils.trimString(pickupAddress));
                if (address.getLocality() != null) {
                    providerCurrentCity = address.getLocality();
                } else if (address.getSubAdminArea() != null) {
                    providerCurrentCity = address.getSubAdminArea();
                } else {
                    providerCurrentCity = address.getAdminArea();
                }
                getVehicleTypeOfCurrentCity(countryName, providerCurrentCity, address.getLatitude(), address.getLongitude(), admin, false);
                setPlaceFilter(address.getCountryCode());
            } else {
                getGeocodeSourcesAddressFromLocation(drawerActivity.currentLocation);
            }
        }
    }
}
