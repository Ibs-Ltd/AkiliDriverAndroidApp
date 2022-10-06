package com.elluminati.eber.driver.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.EarningActivity;
import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.adapter.TripAnalyticAdapter;
import com.elluminati.eber.driver.adapter.TripDayEarningAdaptor;
import com.elluminati.eber.driver.adapter.TripEarningAdapter;
import com.elluminati.eber.driver.components.MyAppTitleFontTextView;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.datamodels.Analytic;
import com.elluminati.eber.driver.models.datamodels.EarningData;
import com.elluminati.eber.driver.models.datamodels.TripsEarning;
import com.elluminati.eber.driver.models.responsemodels.EarningResponse;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by elluminati on 27-Jun-17.
 */

public class DayEarningFragment extends Fragment implements View.OnClickListener {


    public MyFontTextView tvTripDate;
    private MyAppTitleFontTextView tvTripTotal;
    private RecyclerView rcvOrderEarning, rcvProviderAnalytic, rcvOrders;
    private ArrayList<ArrayList<EarningData>> arrayListForEarning;
    private ArrayList<Analytic> arrayListProviderAnalytic;
    private List<TripsEarning> tripPaymentsItemList;
    private LinearLayout llData, ivEmpty;
    private Calendar calendar;
    private int day;
    private int month;
    private int year;
    private DatePickerDialog.OnDateSetListener fromDateSet;
    private EarningActivity earningActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragEarView = inflater.inflate(R.layout.fragment_day_earning, container, false);
        tvTripDate = fragEarView.findViewById(R.id.tvTripDate);
        tvTripTotal = fragEarView.findViewById(R.id.tvTripTotal);
        rcvOrderEarning = fragEarView.findViewById(R.id.rcvOrderEarning);
        rcvProviderAnalytic = fragEarView.findViewById(R.id.rcvProviderAnalytic);
        rcvOrders = fragEarView.findViewById(R.id.rcvOrders);
        llData = fragEarView.findViewById(R.id.llData);
        return fragEarView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        earningActivity = (EarningActivity) getActivity();
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        arrayListForEarning = new ArrayList<>();
        arrayListProviderAnalytic = new ArrayList<>();
        tripPaymentsItemList = new ArrayList<>();
        getDailyEarning(new Date());
        setDate(new Date());
        tvTripDate.setOnClickListener(this);
        fromDateSet = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.clear();
                calendar.set(year, monthOfYear, dayOfMonth);
                setDate(calendar.getTime());
                getDailyEarning(calendar.getTime());
            }
        };

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvTripDate:
                openFromDatePicker();
                break;

            default:

                break;
        }
    }


    private void initEarningOrderRcv() {
        rcvOrderEarning.setLayoutManager(new LinearLayoutManager(earningActivity));
        rcvOrderEarning.setAdapter(new TripEarningAdapter(arrayListForEarning));
        rcvOrderEarning.setNestedScrollingEnabled(false);
    }

    private void initAnalyticRcv() {
        rcvProviderAnalytic.setLayoutManager(new GridLayoutManager(earningActivity, 2, LinearLayoutManager.VERTICAL, false));
        rcvProviderAnalytic.addItemDecoration(new DividerItemDecoration(earningActivity, DividerItemDecoration.HORIZONTAL));
        rcvProviderAnalytic.setAdapter(new TripAnalyticAdapter(arrayListProviderAnalytic));
        rcvProviderAnalytic.setNestedScrollingEnabled(false);
    }

    private void initOrdersRcv() {
        rcvOrders.setLayoutManager(new LinearLayoutManager(earningActivity));
        rcvOrders.addItemDecoration(new DividerItemDecoration(earningActivity, DividerItemDecoration.VERTICAL));
        rcvOrders.setAdapter(new TripDayEarningAdaptor(earningActivity, tripPaymentsItemList));
        rcvOrders.setNestedScrollingEnabled(false);
    }

    private void openFromDatePicker() {
        DatePickerDialog fromPiker = new DatePickerDialog(earningActivity, fromDateSet, year, month, day);
        fromPiker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        fromPiker.show();
    }

    public void setDate(Date date) {
        tvTripDate.setText(Utils.getDayOfMonthSuffix(Integer.valueOf(earningActivity.parseContent.day.format(date))) + earningActivity.parseContent.dateFormatMonth.format(date));
    }


    public void getDailyEarning(Date date) {
        Utils.showCustomProgressDialog(earningActivity, "", false, null);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.accumulate(Const.Params.PROVIDER_ID, earningActivity.preferenceHelper.getProviderId());
            jsonObject.accumulate(Const.Params.TOKEN, earningActivity.preferenceHelper.getSessionToken());
            String earningDateEn = earningActivity.parseContent.getDateFormatInEnglish(Const.DATE_FORMAT).format(date);
            jsonObject.accumulate(Const.Params.DATE, earningDateEn);

            Call<EarningResponse> call = ApiClient.getClient().create(ApiInterface.class).getProviderDailyEarningDetail(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<EarningResponse>() {
                @Override
                public void onResponse(Call<EarningResponse> call, Response<EarningResponse> response) {
                    if (response.isSuccessful()) {
                        EarningResponse earningResponse = response.body();
                        if (earningResponse.isSuccess()) {
                            arrayListForEarning.clear();
                            arrayListProviderAnalytic.clear();
                            tripPaymentsItemList.clear();
                            tripPaymentsItemList.addAll(earningResponse.getTrips());
                            NumberFormat numberFormat = earningActivity.currencyHelper.getCurrencyFormat(earningActivity.preferenceHelper.getCurrencyCode());
                            earningActivity.parseContent.parseEarning(earningResponse, arrayListForEarning, arrayListProviderAnalytic, false, earningActivity);
                            initEarningOrderRcv();
                            initAnalyticRcv();
                            initOrdersRcv();

                            tvTripTotal.setText(numberFormat.format(earningResponse.getProviderDayEarning().getTotalProviderServiceFees()));

                            llData.setVisibility(View.VISIBLE);

                        } else {
                            Utils.showErrorToast(earningResponse.getErrorCode(), earningActivity);
                            llData.setVisibility(View.GONE);
                        }
                        Utils.hideCustomProgressDialog();
                    }

                }

                @Override
                public void onFailure(Call<EarningResponse> call, Throwable t) {
                    AppLog.handleThrowable(EarningActivity.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException("DailyEarning", e);
        }
    }
}
