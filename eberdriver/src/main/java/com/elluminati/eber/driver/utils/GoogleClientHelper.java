package com.elluminati.eber.driver.utils;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class GoogleClientHelper {
    private final GoogleApiClient.Builder builder;
    private GoogleApiClient googleApiClient;

    public GoogleClientHelper(Context context) {

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        builder = new GoogleApiClient.Builder(context).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).addApi(LocationServices.API);

    }

    public GoogleClientHelper(FragmentActivity fragmentActivity, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        builder = new GoogleApiClient.Builder(fragmentActivity).enableAutoManage(fragmentActivity, onConnectionFailedListener).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).addApi(LocationServices.API);

    }

    public GoogleApiClient build() {
        googleApiClient = builder.build();
        return googleApiClient;
    }

    public void connect() {
        if (googleApiClient != null && !googleApiClient.isConnected()) {
            googleApiClient.connect(GoogleApiClient.SIGN_IN_MODE_OPTIONAL);
        }
    }

    public void disconnect() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }
}
