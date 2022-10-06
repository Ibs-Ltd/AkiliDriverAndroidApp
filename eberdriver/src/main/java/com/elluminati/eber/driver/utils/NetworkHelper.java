package com.elluminati.eber.driver.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.elluminati.eber.driver.interfaces.ConnectivityReceiverListener;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkHelper {

    private static final NetworkHelper networkHelper = new NetworkHelper();
    private final NetworkRequest networkRequest;
    private final ConnectivityManager.NetworkCallback networkCallback;
    private ConnectivityManager connectivityManager;
    private boolean isInternetConnected;

    private ConnectivityReceiverListener networkAvailableListener;

    private NetworkHelper() {
        networkRequest = new NetworkRequest.Builder().build();
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                isInternetConnected = true;
                if (networkAvailableListener != null) {
                    networkAvailableListener.onNetworkConnectionChanged(true);
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
                if (networkAvailableListener != null) {
                    networkAvailableListener.onNetworkConnectionChanged(false);
                }
            }
        };
    }

    public static NetworkHelper getInstance() {
        return networkHelper;
    }

    public void initConnectivityManager(Context context) {
        if (connectivityManager == null) {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    connectivityManager.registerDefaultNetworkCallback(networkCallback);
                } else {
                    connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
                }
            }
        }


    }

    public void setNetworkAvailableListener(ConnectivityReceiverListener networkAvailableListener) {
        this.networkAvailableListener = networkAvailableListener;
    }

    public boolean isInternetConnected() {
        return isInternetConnected;
    }
}
