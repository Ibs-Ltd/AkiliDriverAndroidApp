package com.elluminati.eber.driver.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.singleton.CurrentTrip;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.PreferenceHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaceAutocompleteAdapter extends BaseAdapter implements Filterable {
    private final CharacterStyle styleBold = new StyleSpan(Typeface.BOLD);
    private final Context context;
    private final LayoutInflater inflater;
    private final List<Place.Field> placeFields;
    /**
     * Current results returned by this adapter.
     */
    private final ArrayList<AutocompletePrediction> mResultList;
    PlacesClient placesClient;
    private ViewHolder holder;
    private RectangularBounds bounds;
    private String countryCode;


    /**
     * Initializes with a resource for text rows and autocomplete query bounds.
     *
     * @see ArrayAdapter#ArrayAdapter(Context, int)
     */
    public PlaceAutocompleteAdapter(Context context) {
        this.context = context;
        if (!Places.isInitialized()) {
            Places.initialize(context, PreferenceHelper.getInstance(context).getGoogleAutoCompleteKey());
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        placesClient = Places.createClient(context);
        mResultList = new ArrayList<>();
        placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
    }

    public void setPlaceFilter(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Sets the bounds for all subsequent queries.
     */
    public void setBounds(RectangularBounds bounds) {
        this.bounds = bounds;
    }

    /**
     * Returns the number of results received in the last autocomplete query.
     */
    @Override
    public int getCount() {
        return mResultList.size();
    }

    /**
     * Returns an item from the last autocomplete query.
     */
    @Override
    public AutocompletePrediction getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_autocomplete_list, parent, false);
            holder = new ViewHolder();
            holder.tvPlaceName = convertView.findViewById(R.id.tvPlaceName);
            holder.tvPlaceAddress = convertView.findViewById(R.id.tvPlaceAddress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AutocompletePrediction item = getItem(position);
        holder.tvPlaceName.setText(item.getPrimaryText(styleBold));
        holder.tvPlaceAddress.setText(item.getSecondaryText(styleBold));

        return convertView;
    }

    /**
     * Returns the filter for the current set of autocomplete results.
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                // Skip the autocomplete query if no constraints are given.
                if (constraint != null) {
                    // Query the autocomplete API for the (constraint) search string.
                    getFindAutocompletePredictionsRequest(constraint);
                    if (mResultList != null) {
                        // The API successfully returned results.
                        results.values = mResultList;
                        results.count = mResultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                // Override this method to display a readable result in the AutocompleteTextView
                // when clicked.
                if (resultValue instanceof AutocompletePrediction) {
                    return ((AutocompletePrediction) resultValue).getFullText(null);
                } else {
                    return super.convertResultToString(resultValue);
                }
            }
        };
    }


    public String getPlaceId(int position) {
        if (mResultList != null && !mResultList.isEmpty()) {
            return mResultList.get(position).getPlaceId();
        } else {
            return "";
        }

    }

    private void getFindAutocompletePredictionsRequest(CharSequence constraint) {

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                // Call either setLocationBias() OR setLocationRestriction().
                .setLocationBias(bounds)
                //.setLocationRestriction(bounds)
                .setCountry(countryCode)
                //.setTypeFilter(TypeFilter.GEOCODE)
                .setSessionToken(CurrentTrip.getInstance().getAutocompleteSessionToken()).setQuery(constraint.toString()).build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener(new OnSuccessListener<FindAutocompletePredictionsResponse>() {
            @Override
            public void onSuccess(FindAutocompletePredictionsResponse response) {
                mResultList.clear();
                mResultList.addAll(response.getAutocompletePredictions());
                notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AppLog.handleException("AutoComplete", e);
            }
        });
    }

    public void getFetchPlaceRequest(String placeId, OnSuccessListener<FetchPlaceResponse> responseOnSuccessListener) {
        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).setSessionToken(CurrentTrip.getInstance().getAutocompleteSessionToken()).build();
        placesClient.fetchPlace(request).addOnSuccessListener(responseOnSuccessListener);
    }

    private class ViewHolder {
        MyFontTextView tvPlaceName, tvPlaceAddress;

    }

}
