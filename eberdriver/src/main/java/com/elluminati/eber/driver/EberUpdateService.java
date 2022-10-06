package com.elluminati.eber.driver;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.elluminati.eber.driver.models.responsemodels.ProviderLocationResponse;
import com.elluminati.eber.driver.models.singleton.CurrentTrip;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.roomdata.DataLocationsListener;
import com.elluminati.eber.driver.roomdata.DataModificationListener;
import com.elluminati.eber.driver.roomdata.DatabaseClient;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.PreferenceHelper;
import com.elluminati.eber.driver.utils.SocketHelper;
import com.elluminati.eber.driver.utils.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by elluminati on 24-06-2016.
 */
public class EberUpdateService extends Service {
    private static final String CHANNEL_ID = "eberlocation2019";
    private static final Long INTERVAL = 5000L; // millisecond
    private static final Long FASTEST_INTERVAL = 4000L; // millisecond
    private static final Float DISPLACEMENT = 5f; // millisecond
    private final LocationRequest locationRequest = LocationRequest.create().setInterval(INTERVAL).setFastestInterval(FASTEST_INTERVAL).setSmallestDisplacement(DISPLACEMENT).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    public String TAG = EberUpdateService.class.getSimpleName();
    private PreferenceHelper preferenceHelper;
    private Location currentLocation;
    private Location lastLocation;
    private ServiceReceiver serviceReceiver;
    private SocketHelper socketHelper;
    private boolean isWaitForLocationUpdate;
    private NetworkRequest networkRequest;
    private ConnectivityManager.NetworkCallback networkCallback;
    private ConnectivityManager connectivityManager;
    private boolean isInternetConnected;
    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult != null) {
                currentLocation = locationResult.getLastLocation();
                if (!SocketHelper.getInstance().isConnected() || (lastLocation != null && currentLocation != null && (currentLocation.getTime() - lastLocation.getTime()) > 10 * 1000)) {
                    isWaitForLocationUpdate = false;
                }
                if (isInternetConnected) {
                    if (!socketHelper.isConnected()) {
                        SocketHelper.getInstance().socketConnect();
                    }
                }
                if (!isWaitForLocationUpdate) {
                    updateLocation();
                }
            }
        }
    };
    private final Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            updateLocation();
        }
    };
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(EberUpdateService.this);
        startForeground(Const.FOREGROUND_NOTIFICATION_ID, getNotification(getResources().getString(R.string.app_name)));
        preferenceHelper = PreferenceHelper.getInstance(this);
        serviceReceiver = new ServiceReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Const.ACTION_ACCEPT_NOTIFICATION);
        intentFilter.addAction(Const.ACTION_CANCEL_NOTIFICATION);
        registerReceiver(serviceReceiver, intentFilter);
        initNetworkManager();
        socketHelper = SocketHelper.getInstance();
        socketHelper.getSocket().on(Socket.EVENT_CONNECT, onConnect);
        socketHelper.socketConnect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(Const.Action.START_FOREGROUND_ACTION)) {
                checkPermission();
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            currentLocation = location;
                        }
                    }
                });
            }
        }
        return START_STICKY;
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            addLocationListener();
        }
    }

    @SuppressLint("MissingPermission")
    private void addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void removeLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(serviceReceiver);
        removeLocationListener();
        connectivityManager.unregisterNetworkCallback(networkCallback);
        SocketHelper socketHelper = SocketHelper.getInstance();
        if (socketHelper != null) {
            if (!preferenceHelper.getIsHaveTrip()) {
                socketHelper.getSocket().off();
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void providerLocationUpdateWhenTrip(String tripId) {
        if (currentLocation != null) {
            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Const.Params.PROVIDER_ID, preferenceHelper.getProviderId());
                jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
                jsonObject.put(Const.Params.LATITUDE, String.valueOf(currentLocation.getLatitude()));
                jsonObject.put(Const.Params.LONGITUDE, String.valueOf(currentLocation.getLongitude()));
                if (lastLocation != null) {
                    jsonObject.put(Const.Params.BEARING, currentLocation.bearingTo(lastLocation));
                } else {
                    jsonObject.put(Const.Params.BEARING, 0);
                }
                jsonObject.put(Const.Params.TRIP_ID, tripId);
                jsonObject.put(Const.Params.LOCATION_UNIQUE_ID, preferenceHelper.getIsHaveTrip() ? preferenceHelper.getLocationUniqueId() : 0);
                if (isInternetConnected && socketHelper.isConnected()) {
                    setLastLocation(currentLocation);
                    DatabaseClient.getInstance(this).insertLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), preferenceHelper.getLocationUniqueId(), new DataModificationListener() {
                        @Override
                        public void onSuccess() {
                            DatabaseClient.getInstance(EberUpdateService.this).getAllLocation(new DataLocationsListener() {
                                @Override
                                public void onSuccess(JSONArray locations) {
                                    try {
                                        jsonObject.put(Const.google.LOCATION, locations);
                                        updateLocationUsingSocket(jsonObject);
                                    } catch (JSONException e) {
                                        AppLog.handleException(TAG, e);
                                    }
                                }
                            });
                        }
                    });


                } else {
                    if (!TextUtils.isEmpty(tripId) && !locationMatch(lastLocation, currentLocation)) {
                        DatabaseClient.getInstance(this).insertLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), preferenceHelper.getLocationUniqueId(), new DataModificationListener() {
                            @Override
                            public void onSuccess() {
                                setLastLocation(currentLocation);
                            }
                        });
                    }
                }
            } catch (JSONException e) {
                AppLog.handleException(TAG, e);
            }
        }

    }

    private void providerLocationUpdateNoTrip() {
        if (currentLocation != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Const.Params.PROVIDER_ID, preferenceHelper.getProviderId());
                jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
                jsonObject.put(Const.Params.LATITUDE, String.valueOf(currentLocation.getLatitude()));
                jsonObject.put(Const.Params.LONGITUDE, String.valueOf(currentLocation.getLongitude()));
                if (lastLocation != null) {
                    jsonObject.put(Const.Params.BEARING, currentLocation.bearingTo(lastLocation));
                }
                jsonObject.put(Const.Params.TRIP_ID, "");
                jsonObject.put(Const.Params.LOCATION_UNIQUE_ID, 0);

                JSONArray location = new JSONArray();
                location.put(currentLocation.getLatitude());
                location.put(currentLocation.getLongitude());
                location.put(System.currentTimeMillis());
                JSONArray locationJSONArray = new JSONArray();
                jsonObject.put(Const.google.LOCATION, locationJSONArray.put(location));
                setLastLocation(currentLocation);
                updateLocationUsingSocket(jsonObject);
            } catch (JSONException e) {
                AppLog.handleException(TAG, e);
            }
        }

    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_stat_eber : R.mipmap.ic_launcher;
    }

    private void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(Const.SERVICE_NOTIFICATION_ID);
    }

    private void getProviderLocationResponse(ProviderLocationResponse response) {
        if (response.isSuccess()) {
            if (preferenceHelper.getIsHaveTrip()) {
                DatabaseClient.getInstance(this).deleteLocation(String.valueOf(response.getLocationUniqueId()), new DataModificationListener() {
                    @Override
                    public void onSuccess() {
                        int uniqueIdForLocation = preferenceHelper.getLocationUniqueId();
                        uniqueIdForLocation++;
                        preferenceHelper.putLocationUniqueId(uniqueIdForLocation);
                    }
                });
            }
            CurrentTrip currentTrip = CurrentTrip.getInstance();
            currentTrip.setTotalDistance(response.getTotalDistance());
            currentTrip.setTotalTime(response.getTotalTime());
            Utils.hideLocationUpdateDialog();
        }
    }

    /**
     * this method get Notification object which help to notify user as foreground service
     *
     * @param notificationDetails
     * @return
     */
    private Notification getNotification(String notificationDetails) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "Location Service", NotificationManager.IMPORTANCE_DEFAULT);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }
        Intent intent = new Intent(getApplicationContext(), MainDrawerActivity.class);
        PendingIntent notifyIntent = PendingIntent.getActivity(EberUpdateService.this, 0, intent, Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_MUTABLE : PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(getNotificationIcon()).setColor(ResourcesCompat.getColor(getResources(), R.color.color_app_theme_dark, null)).setContentTitle(notificationDetails).setContentText(getResources().getString(R.string.msg_service)).setContentIntent(notifyIntent).setAutoCancel(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID); // Channel ID
        }
        return builder.build();
    }

    private void setLastLocation(Location location) {
        if (lastLocation == null) {
            lastLocation = new Location("lastLocation");
        }
        lastLocation.set(location);
    }

    private boolean locationMatch(Location lastLocation, Location currentLocation) {
        if (lastLocation != null && currentLocation != null) {
            return lastLocation.getLongitude() == currentLocation.getLongitude() && lastLocation.getLatitude() == currentLocation.getLatitude();
        } else {
            return false;
        }
    }

    /**
     * emit provider location using socket
     *
     * @param jsonObject
     */
    private void updateLocationUsingSocket(JSONObject jsonObject) {
        if (socketHelper != null && socketHelper.isConnected()) {
            isWaitForLocationUpdate = true;
            try {
                socketHelper.getSocket().emit(SocketHelper.UPDATE_LOCATION, jsonObject, new Ack() {
                    @Override
                    public void call(Object... args) {
                        if (args != null) {
                            JSONObject jsonObject1 = (JSONObject) args[0];
                            ProviderLocationResponse providerLocationResponse = ApiClient.getGsonInstance().fromJson(jsonObject1.toString(), ProviderLocationResponse.class);
                            getProviderLocationResponse(providerLocationResponse);
                        }
                        isWaitForLocationUpdate = false;
                    }
                });
            } catch (Exception e) {
                isWaitForLocationUpdate = false;
                e.printStackTrace();
            }
        }
    }

    private void updateLocation() {
        if (preferenceHelper.getSessionToken() != null) {
            if (currentLocation != null) {
                if (preferenceHelper.getIsHaveTrip()) {
                    providerLocationUpdateWhenTrip(preferenceHelper.getTripId());
                } else {
                    if (preferenceHelper.getIsProviderOnline() == Const.ProviderStatus.PROVIDER_STATUS_ONLINE) {
                        providerLocationUpdateNoTrip();
                    }
                }
            }
        }
    }

    private void initNetworkManager() {
        networkRequest = new NetworkRequest.Builder().build();
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                isInternetConnected = true;
                if (socketHelper != null) {
                    socketHelper.socketConnect();
                }
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
                isInternetConnected = false;

            }

            @Override
            public void onLost(Network network) {
                super.onLost(network);
                isInternetConnected = false;
            }
        };
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
        }
    }

    private class ServiceReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                switch (intent.getAction()) {
                    case Const.ACTION_CANCEL_NOTIFICATION:
                        clearNotification();
                        break;
                }
            }
        }
    }

}
