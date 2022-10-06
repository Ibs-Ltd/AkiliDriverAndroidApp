package com.elluminati.eber.driver.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by elluminati on 20-06-2016.
 */
public class LocationHelper {

    private static final long INTERVAL = 5000;// millisecond
    private static final long FASTEST_INTERVAL = 4000;// millisecond
    private final Context context;
    private final FusedLocationProviderClient fusedLocationProviderClient;
    public LocationRequest locationRequest;
    private OnLocationReceived locationReceived;
    private LocationSettingsRequest locationSettingsRequest;
    private SettingsClient client;
    private LocationCallback locationCallback;

    public LocationHelper(Context context) {
        this.context = context;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        getLocationRequest();
        setLocationCallback();
    }

    private void setLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location currentLocation = locationResult.getLastLocation();
                if (currentLocation != null) {
                    locationReceived.onLocationChanged(currentLocation);
                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }
        };
    }

    public void setLocationReceivedLister(OnLocationReceived locationReceived) {
        this.locationReceived = locationReceived;
    }

    private void getLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();
        client = LocationServices.getSettingsClient(context);
    }

    public void stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdate() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }


    @SuppressLint("MissingPermission")
    public void getLastLocation(OnSuccessListener<Location> onSuccessListener) {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(onSuccessListener);
    }

    public void onStart() {
        startLocationUpdate();
    }

    public void onStop() {
        stopLocationUpdate();
    }

    public void setLocationSettingRequest(final AppCompatActivity activity, final int requestCode, OnSuccessListener<LocationSettingsResponse> onSuccessListener, final NoGPSDeviceFoundListener noGPSDeviceFoundListener) {
        Task<LocationSettingsResponse> task = client.checkLocationSettings(locationSettingsRequest);
        task.addOnFailureListener(activity, e -> {
            int statusCode = ((ApiException) e).getStatusCode();
            switch (statusCode) {
                case CommonStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(activity, requestCode);
                    } catch (IntentSender.SendIntentException sendEx) {
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    if (noGPSDeviceFoundListener != null) {
                        noGPSDeviceFoundListener.noFound();
                    }
                    break;
            }
        });
        task.addOnSuccessListener(activity, onSuccessListener);
    }

    public interface OnLocationReceived {

        void onLocationChanged(Location location);

    }

    public interface NoGPSDeviceFoundListener {
        void noFound();
    }
}
