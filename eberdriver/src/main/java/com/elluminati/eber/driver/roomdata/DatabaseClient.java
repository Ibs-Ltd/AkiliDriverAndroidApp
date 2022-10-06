package com.elluminati.eber.driver.roomdata;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.elluminati.eber.driver.utils.AppLog;

import org.json.JSONArray;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Ravi Bhalodi on 24,February,2020 in Elluminati
 */
public class DatabaseClient {
    private static DatabaseClient databaseClient;
    private final Context context;
    //our app database object
    private final AppDatabase appDatabase;


    private DatabaseClient(Context context) {
        this.context = context;

        //creating the app database with Room database builder
        //DhaweeyeDriverLocation is the name of the database
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "DhaweeyeDriverLocation").build();
    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static synchronized DatabaseClient getInstance(Context context) {
        if (databaseClient == null) {
            databaseClient = new DatabaseClient(context);
        }
        return databaseClient;
    }


    /**
     * Insert location.
     *
     * @param latitude                 the latitude
     * @param longitude                the longitude
     * @param uniqueId                 the unique id
     * @param dataModificationListener the data modification listener
     */
    public void insertLocation(final double latitude, final double longitude, final int uniqueId, @NonNull final DataModificationListener dataModificationListener) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                Location location = new Location();
                location.setLatitude(String.valueOf(latitude));
                location.setLongitude(String.valueOf(longitude));
                location.setLocationUniqueId(String.valueOf(uniqueId));
                location.setTime(String.valueOf(System.currentTimeMillis()));
                appDatabase.locationDao().insert(location);
                return null;
            }

            @Override
            protected void onPostExecute(Void aBoolean) {
                super.onPostExecute(aBoolean);
                dataModificationListener.onSuccess();
            }
        }.execute();
    }

    /**
     * Clear location.
     *
     * @param dataModificationListener the data modification listener
     */
    public void clearLocation(@NonNull final DataModificationListener dataModificationListener) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.locationDao().deleteAll();
                return null;
            }

            @Override
            protected void onPostExecute(Void aBoolean) {
                super.onPostExecute(aBoolean);
                dataModificationListener.onSuccess();
            }
        }.execute();
    }

    /**
     * Gets all location.
     *
     * @param dataLocationsListener the data locations listener
     */
    public void getAllLocation(@NonNull final DataLocationsListener dataLocationsListener) {

        new AsyncTask<Void, Void, List<Location>>() {

            @Override
            protected List<Location> doInBackground(Void... voids) {
                return appDatabase.locationDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Location> locations) {
                super.onPostExecute(locations);
                JSONArray locationJSONArray = new JSONArray();
                if (locations != null) {
                    Iterator<Location> locationIterator = locations.iterator();
                    do {
                        try {
                            Location location = locationIterator.next();
                            JSONArray locationArray = new JSONArray();
                            locationArray.put(location.getLatitude());
                            locationArray.put(location.getLongitude());
                            locationArray.put(location.getTime());
                            locationJSONArray.put(locationArray);
                        } catch (NoSuchElementException e) {
                            AppLog.handleException("DatabaseClient", e);
                        }
                    } while (locationIterator.hasNext());
                    dataLocationsListener.onSuccess(locationJSONArray);
                } else {
                    dataLocationsListener.onSuccess(locationJSONArray);
                }


            }
        }.execute();
    }

    /**
     * Gets count.
     *
     * @param dataCountListener the data count listener
     */
    public void getCount(@NonNull final DataCountListener dataCountListener) {

        new AsyncTask<Void, Void, Long>() {

            @Override
            protected Long doInBackground(Void... voids) {
                return appDatabase.locationDao().count();
            }

            @Override
            protected void onPostExecute(Long count) {
                super.onPostExecute(count);
                dataCountListener.onSuccess(count);
            }
        }.execute();
    }


    /**
     * Delete location.
     *
     * @param uniqueId                 the unique id
     * @param dataModificationListener the data modification listener
     */
    public void deleteLocation(final String uniqueId, @NonNull final DataModificationListener dataModificationListener) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    appDatabase.locationDao().deleteLocation(uniqueId);
                } catch (NoSuchElementException e) {

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void count) {
                super.onPostExecute(count);
                dataModificationListener.onSuccess();
            }
        }.execute();
    }
}
