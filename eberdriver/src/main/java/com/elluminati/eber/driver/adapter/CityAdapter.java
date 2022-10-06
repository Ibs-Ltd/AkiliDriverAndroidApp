package com.elluminati.eber.driver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.datamodels.City;

import java.util.ArrayList;

/**
 * Created by elluminati on 23-09-2016.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> implements Filterable {

    private ArrayList<City> cityList;
    private Filter filter;

    public CityAdapter(ArrayList<City> cityList) {
        this.cityList = cityList;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_name_code, parent, false);
        CityViewHolder countryViewHolder = new CityViewHolder(view);

        return countryViewHolder;
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {

        holder.tvCityName.setText(cityList.get(position).getFullCityName());
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) filter = new AppFilter(cityList);
        return filter;
    }

    public ArrayList<City> getFilterResult() {
        return cityList;
    }

    protected class CityViewHolder extends RecyclerView.ViewHolder {
        MyFontTextView tvCityName;
        View viewDive;

        public CityViewHolder(View itemView) {
            super(itemView);

            tvCityName = itemView.findViewById(R.id.tvItemCountryName);
            viewDive = itemView.findViewById(R.id.viewDiveCity);

        }
    }

    private class AppFilter extends Filter {

        private final ArrayList<City> sourceObjects;

        public AppFilter(ArrayList<City> objects) {

            sourceObjects = new ArrayList<City>();
            synchronized (this) {
                sourceObjects.addAll(objects);
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence chars) {
            String filterSeq = chars.toString();
            FilterResults result = new FilterResults();
            if (filterSeq.length() > 0) {
                ArrayList<City> filter = new ArrayList<City>();
                for (City city : sourceObjects) {
                    // the filtering itself:
                    if (city.getFullCityName().toUpperCase().startsWith(filterSeq.toUpperCase())) filter.add(city);
                }
                result.count = filter.size();
                result.values = filter;
            } else {
                // add all objects
                synchronized (this) {
                    result.values = sourceObjects;
                    result.count = sourceObjects.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count != 0) {
                cityList = (ArrayList<City>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
