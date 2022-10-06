package com.elluminati.eber.driver.utils;

import android.content.Context;

import com.elluminati.eber.driver.BuildConfig;
import com.elluminati.eber.driver.parse.ApiClient;

public class ServerConfig {

    public static String BASE_URL = BuildConfig.BASE_URL;

    public static void setURL(Context context) {
        BASE_URL = PreferenceHelper.getInstance(context).getBaseUrl();
        new ApiClient().changeAllApiBaseUrl(BASE_URL);
    }
}