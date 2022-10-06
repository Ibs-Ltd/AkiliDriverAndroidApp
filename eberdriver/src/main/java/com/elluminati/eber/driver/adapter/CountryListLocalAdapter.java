package com.elluminati.eber.driver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.models.datamodels.Country;
import com.elluminati.eber.driver.models.datamodels.CountryList;

import java.util.ArrayList;

/**
 * Created by elluminati on 05-08-2016.
 */
public class CountryListLocalAdapter extends RecyclerView.Adapter<CountryListLocalAdapter.CountryListViewHolder> implements Filterable {

    private ArrayList<CountryList> countryList;
    private Filter filter;


    public CountryListLocalAdapter(ArrayList<CountryList> countryList) {
        this.countryList = countryList;


    }

    @Override
    public CountryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country_code, parent, false);
        CountryListViewHolder countryListViewHolder = new CountryListViewHolder(view);

        return countryListViewHolder;
    }

    @Override
    public void onBindViewHolder(CountryListViewHolder holder, int position) {
        holder.tvCountryCodeDigit.setText(countryList.get(position).getCountryPhoneCode());
        holder.tvCountryName.setText(countryList.get(position).getCountryName());

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) filter = new AppFilter(countryList);
        return filter;
    }

    public ArrayList<CountryList> getFilterResult() {
        return countryList;
    }

    protected class CountryListViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountryCodeDigit, tvCountryName;
        View viewDive;

        public CountryListViewHolder(View itemView) {
            super(itemView);

            tvCountryCodeDigit = itemView.findViewById(R.id.tvCountryCodeDigit);
            tvCountryName = itemView.findViewById(R.id.tvCountryName);
            viewDive = itemView.findViewById(R.id.viewDiveCountry);

        }
    }

    private class AppFilter extends Filter {

        private final ArrayList<CountryList> sourceObjects;

        public AppFilter(ArrayList<CountryList> objects) {

            sourceObjects = new ArrayList<CountryList>();
            synchronized (this) {
                sourceObjects.addAll(objects);
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence chars) {
            String filterSeq = chars.toString();
            FilterResults result = new FilterResults();
            if (filterSeq != null && filterSeq.length() > 0) {
                ArrayList<CountryList> filter = new ArrayList<CountryList>();
                for (CountryList countryCode : sourceObjects) {
                    // the filtering itself:
                    if (countryCode.getCountryName().toUpperCase().startsWith(filterSeq.toUpperCase())) filter.add(countryCode);
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
                countryList = (ArrayList<CountryList>) results.values;
                notifyDataSetChanged();
            }
        }

    }
}
