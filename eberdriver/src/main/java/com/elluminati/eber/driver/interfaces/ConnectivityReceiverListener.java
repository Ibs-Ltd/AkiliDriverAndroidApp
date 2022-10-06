package com.elluminati.eber.driver.interfaces;

public interface ConnectivityReceiverListener {
    void onNetworkConnectionChanged(boolean isConnected);

    void onGpsConnectionChanged(boolean isConnected);
}

