package com.elluminati.eber.driver.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.elluminati.eber.driver.adapter.TripEarningAdapter;
import com.elluminati.eber.driver.components.CustomDialogWeek;
import com.elluminati.eber.driver.components.MyAppTitleFontTextView;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.datamodels.Analytic;
import com.elluminati.eber.driver.models.datamodels.EarningData;
import com.elluminati.eber.driver.models.datamodels.WeekData;
import com.elluminati.eber.driver.models.responsemodels.EarningResponse;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.CalenderHelper;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by elluminati on 27-Jun-17.
 */

public class WeekEarningFragment extends Fragment implements View.OnClickListener {


    private final ArrayList<String> dateList = new ArrayList<>();
    private MyFontTextView tvTripDate;
    private MyAppTitleFontTextView tvTripTotal;
    private RecyclerView rcvOrderEarning, rcvProviderAnalytic;
    private ArrayList<ArrayList<EarningData>> arrayListForEarning;
    private ArrayList<Analytic> arrayListProviderAnalytic;
    private LinearLayout llData;
    private CalenderHelper calenderHelper;
    private ImageView ivPaid;
    private EarningActivity earningActivity;
    private CustomDialogWeek weekDialog;
    private Calendar calendar;
    private int currentYear;
    private BarChart barChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragEarView = inflater.inflate(R.layout.fragment_week_earning, container, false);
        tvTripDate = fragEarView.findViewById(R.id.tvTripDate);
        tvTripTotal = fragEarView.findViewById(R.id.tvTripTotal);
        rcvOrderEarning = fragEarView.findViewById(R.id.rcvOrderEarning);
        rcvProviderAnalytic = fragEarView.findViewById(R.id.rcvProviderAnalytic);
        llData = fragEarView.findViewById(R.id.llData);
        ivPaid = fragEarView.findViewById(R.id.ivPaid);
        barChart = fragEarView.findViewById(R.id.barChartState);
        return fragEarView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initChart();
        earningActivity = (EarningActivity) getActivity();
        calenderHelper = new CalenderHelper();
        arrayListForEarning = new ArrayList<>();
        arrayListProviderAnalytic = new ArrayList<>();
        tvTripDate.setOnClickListener(this);
        tvTripDate.setText(earningActivity.parseContent.dateFormat.format(new Date()));
        calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        ArrayList<Date> dateArrayList = calenderHelper.getCurrentWeekDates();
        getWeeklyEarning(dateArrayList.get(0), dateArrayList.get(1));

    }


    private void getWeeklyEarning(Date start, Date end) {
        Utils.showCustomProgressDialog(earningActivity, "", false, null);

        String weekStartDate = earningActivity.parseContent.dailyEarningDateFormat.format(start);
        String weekEndDate = earningActivity.parseContent.dailyEarningDateFormat.format(end);
        tvTripDate.setText(weekStartDate + " - " + weekEndDate);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate(Const.Params.PROVIDER_ID, earningActivity.preferenceHelper.getProviderId());
            jsonObject.accumulate(Const.Params.TOKEN, earningActivity.preferenceHelper.getSessionToken());
            String weekStartDateEn = earningActivity.parseContent.getDateFormatInEnglish(Const.DATE_FORMAT_EARNING).format(start);
            jsonObject.accumulate(Const.Params.DATE, weekStartDateEn);
            Call<EarningResponse> call = ApiClient.getClient().create(ApiInterface.class).getProviderWeeklyEarningDetail(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<EarningResponse>() {
                @Override
                public void onResponse(Call<EarningResponse> call, Response<EarningResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().isSuccess()) {
                            EarningResponse earningResponse = response.body();
                            NumberFormat numberFormat = earningActivity.currencyHelper.getCurrencyFormat(earningActivity.preferenceHelper.getCurrencyCode());
                            arrayListForEarning.clear();
                            arrayListProviderAnalytic.clear();
                            earningActivity.parseContent.parseEarning(earningResponse, arrayListForEarning, arrayListProviderAnalytic, true, earningActivity);
                            initEarningOrderRcv();
                            initAnalyticRcv();

                            tvTripTotal.setText(numberFormat.format(earningResponse.getProviderWeekEarning().getTotalProviderServiceFees()));
                            llData.setVisibility(View.VISIBLE);

                            setBarChartData(earningResponse);

                        } else {
                            Utils.showErrorToast(response.body().getErrorCode(), earningActivity);
                            llData.setVisibility(View.GONE);
                        }

                        Utils.hideCustomProgressDialog();
                    }
                }

                @Override
                public void onFailure(Call<EarningResponse> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            AppLog.handleException("WeeklyEarning", e);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvTripDate:
                openWeekDialog();
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


    private void openWeekDialog() {

        if (weekDialog != null && weekDialog.isShowing()) {
            return;
        }

        weekDialog = new CustomDialogWeek(earningActivity, currentYear) {
            @Override
            public void onSelectedWeek(WeekData weekData) {
                tvTripDate.setText(earningActivity.parseContent.dailyEarningDateFormat.format(weekData.getListWeekDate().get(0)));
                getWeeklyEarning(weekData.getListWeekDate().get(0), weekData.getListWeekDate().get(1));
                closeWeekDialog();
            }

            @Override
            public void onCancel() {
                closeWeekDialog();
            }
        };
        weekDialog.show();

    }

    private void closeWeekDialog() {
        if (weekDialog != null && weekDialog.isShowing()) {
            weekDialog.dismiss();
            weekDialog = null;
        }
    }


    private void initChart() {

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {


                try {
                    Date date = earningActivity.parseContent.webFormat.parse(dateList.get(Math.round(e.getX())));
                    if (date.getTime() <= System.currentTimeMillis()) {
                        earningActivity.dailyEarningFragment.setDate(date);
                        earningActivity.getDayEarning(date);
                        earningActivity.earningPager.setCurrentItem(0, true);
                    }
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new CustomAxisValueFormatter(barChart));


        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);


        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);


        Legend l = barChart.getLegend();
        l.setEnabled(false);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);


    }

    private void setChartWithNegativeValue(boolean isHaveNegativeValue) {
        if (isHaveNegativeValue) {
            barChart.getAxisRight().resetAxisMinimum();
            barChart.getAxisLeft().resetAxisMinimum();
        } else {
            barChart.getAxisRight().setAxisMinimum(0f);
            barChart.getAxisLeft().setAxisMinimum(0f);
        }
    }

    private void setBarChartData(EarningResponse earningResponse) {
        WeekData dayOfWeekOrderTotal, dayOfWeekDate;
        dayOfWeekOrderTotal = earningResponse.getDayOfTripTotal();
        dayOfWeekDate = earningResponse.getDayOfWeekDate();
        dateList.clear();
        dateList.add(dayOfWeekDate.getDate1());
        dateList.add(dayOfWeekDate.getDate2());
        dateList.add(dayOfWeekDate.getDate3());
        dateList.add(dayOfWeekDate.getDate4());
        dateList.add(dayOfWeekDate.getDate5());
        dateList.add(dayOfWeekDate.getDate6());
        dateList.add(dayOfWeekDate.getDate7());
        String[] barData = {dayOfWeekOrderTotal.getDate1(), dayOfWeekOrderTotal.getDate2(), dayOfWeekOrderTotal.getDate3(), dayOfWeekOrderTotal.getDate4(), dayOfWeekOrderTotal.getDate5(), dayOfWeekOrderTotal.getDate6(), dayOfWeekOrderTotal.getDate7()};

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        boolean isHaveNegativeValue = false;
        for (int i = 0; i < barData.length; i++) {
            float value = Float.parseFloat(barData[i]);
            if (!isHaveNegativeValue && value < 0) {
                isHaveNegativeValue = true;
            }
            yVals1.add(new BarEntry(i, value));
        }

        BarDataSet set1;
        setChartWithNegativeValue(isHaveNegativeValue);

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");

            set1.setValueFormatter(new MyValueFormatter());
            set1.setDrawIcons(false);
            set1.setHighLightAlpha(0);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            barChart.setData(data);

        }
        barChart.invalidate();
        barChart.animateY(2000);
    }

    private String getDate(String date) {
        SimpleDateFormat webFormat = new SimpleDateFormat(Const.DATE_TIME_FORMAT_WEB, Locale.US);
        webFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat dateFormat = new SimpleDateFormat(" d MMM", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date1 = webFormat.parse(date);
            return dateFormat.format(date1);
        } catch (ParseException e) {
            AppLog.handleException(WeekEarningFragment.class.getSimpleName(), e);
        }
        return "";
    }


    private class CustomAxisValueFormatter extends ValueFormatter {

        private final BarLineChartBase<?> chart;

        public CustomAxisValueFormatter(BarLineChartBase<?> chart) {
            this.chart = chart;
        }


        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if (Math.round(value) > dateList.size() - 1) {
                return getDate(dateList.get(Math.round(value) - ((Math.round(value) - dateList.size() - 1))));
            } else {
                return getDate(dateList.get(Math.round(value)));
            }
        }

    }

    public class MyValueFormatter extends ValueFormatter {

        private final DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("0.00"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value) + ""; // e.g. append a dollar-sign
        }
    }

}
