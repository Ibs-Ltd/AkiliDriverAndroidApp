package com.elluminati.eber.driver.roomdata;

import org.json.JSONArray;

/**
 * Created by Ravi Bhalodi on 24,February,2020 in Elluminati
 */
public interface DataLocationsListener {
    void onSuccess(JSONArray locations);
}
