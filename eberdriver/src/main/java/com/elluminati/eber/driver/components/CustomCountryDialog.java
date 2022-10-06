package com.elluminati.eber.driver.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.adapter.CountryAdapter;
import com.elluminati.eber.driver.adapter.CountryListLocalAdapter;
import com.elluminati.eber.driver.interfaces.ClickListener;
import com.elluminati.eber.driver.interfaces.RecyclerTouchListener;
import com.elluminati.eber.driver.models.datamodels.Country;
import com.elluminati.eber.driver.models.datamodels.CountryList;

import java.util.ArrayList;

/**
 * Created by elluminati on 04-08-2016.
 */
public abstract class CustomCountryDialog extends Dialog {

    private final RecyclerView rcvCountryCode;
    private final MyFontEdittextView etCountrySearch;
    private final CountryAdapter countryAdapter;
    private final CountryListLocalAdapter countryListLocalAdapter;
    private final TextView tvDialogTitle;
    private final ArrayList<Country> countries;
    private final ArrayList<CountryList> countryLists;
    private final Context context;
    private SearchView searchView;
    private Boolean isFromMapScreen;

    public CustomCountryDialog(Context context, ArrayList<Country> country, ArrayList<CountryList> countryList, boolean isFromMap) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_country_code);
        countries = country;
        isFromMapScreen = isFromMap;
        countryLists = countryList;
        rcvCountryCode = findViewById(R.id.rcvCountryCode);
        etCountrySearch = findViewById(R.id.etCountrySearch);
        tvDialogTitle = findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setText(context.getResources().getString(R.string.text_country_codes));
        rcvCountryCode.setLayoutManager(new LinearLayoutManager(context));
        countryListLocalAdapter = new CountryListLocalAdapter(countryLists);
        countryAdapter = new CountryAdapter(countries);
        if (isFromMap) {
            rcvCountryCode.setAdapter(countryListLocalAdapter);
        } else {
            rcvCountryCode.setAdapter(countryAdapter);
        }
        this.context = context;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        etCountrySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isFromMapScreen) {
                    if (countryListLocalAdapter != null) {
                        countryListLocalAdapter.getFilter().filter(s);
                    }
                } else {
                    if (countryAdapter != null) {
                        countryAdapter.getFilter().filter(s);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rcvCountryCode.addOnItemTouchListener(new RecyclerTouchListener(context, rcvCountryCode, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (isFromMapScreen) {
                    onSelectItem(position, countryListLocalAdapter.getFilterResult());
                } else {
                    onSelect(position, countryAdapter.getFilterResult());
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public abstract void onSelect(int position, ArrayList<Country> filterList);

    public abstract void onSelectItem(int position, ArrayList<CountryList> filterList);
}
