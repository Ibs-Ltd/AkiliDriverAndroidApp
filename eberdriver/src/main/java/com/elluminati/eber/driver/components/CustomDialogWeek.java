package com.elluminati.eber.driver.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.adapter.WeekAdapter;
import com.elluminati.eber.driver.models.datamodels.WeekData;
import com.elluminati.eber.driver.parse.ParseContent;
import com.elluminati.eber.driver.utils.CalenderHelper;
import com.elluminati.eber.driver.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Akash on 21-Aug-17.
 */

public abstract class CustomDialogWeek extends Dialog implements View.OnClickListener {
    private static final int MAX_YEAR_DIFFERENCE = 1;
    private final ParseContent parseContent;
    private final CalenderHelper calenderHelper;
    private final Context context;
    private final int currentYear;
    private ArrayList<WeekData> lisWeekData;
    private MyFontTextView tvDialogYear;
    private MyFontButton btnWeekOk;
    private MyFontButton btnWeekCancel;
    private ImageView ivPreviousYear, ivNextYear;
    private RecyclerView rcvDialogWeeks;
    private WeekAdapter weekAdapter;
    private WeekData selectedWeek;
    private int selectedYear;

    public CustomDialogWeek(@NonNull Context context, int year) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_select_week);
        this.context = context;
        parseContent = ParseContent.getInstance();
        lisWeekData = new ArrayList<>();
        calenderHelper = new CalenderHelper();
        lisWeekData = calenderHelper.getWeeksOfYear(year);
        initComponents();
        currentYear = year;
        selectedYear = currentYear;
        tvDialogYear.setText("" + year);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCancelable(false);
    }


    private void initComponents() {
        tvDialogYear = findViewById(R.id.tvDialogYear);
        btnWeekOk = findViewById(R.id.btnWeekOk);
        btnWeekCancel = findViewById(R.id.btnWeekCancel);
        ivPreviousYear = findViewById(R.id.ivPreviousYear);
        ivNextYear = findViewById(R.id.ivNextYear);
        rcvDialogWeeks = findViewById(R.id.rcvDialogWeeks);
        intiWeekList();
        ivPreviousYear.setOnClickListener(this);
        ivNextYear.setOnClickListener(this);
        btnWeekOk.setOnClickListener(this);
        btnWeekCancel.setOnClickListener(this);
    }


    private void intiWeekList() {
        rcvDialogWeeks.setLayoutManager(new LinearLayoutManager(context));
        weekAdapter = new WeekAdapter(lisWeekData, context) {
            @Override
            public void onWeekSelected(WeekData weekData) {
                selectedWeek = weekData;
            }
        };
        rcvDialogWeeks.setAdapter(weekAdapter);
        weekAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ivPreviousYear:
                setPreviousYearWeeks();
                break;

            case R.id.ivNextYear:
                setNextYearWeeks();
                break;

            case R.id.btnWeekOk:
                if (selectedWeek != null) {
                    onSelectedWeek(selectedWeek);
                } else {
                    Utils.showToast("Please select week", context);
                }
                break;

            case R.id.btnWeekCancel:
                onCancel();
                break;

            default:
                //Default case here..
                break;
        }
    }

    /**
     * Use for set week data of previous year.
     */
    private void setPreviousYearWeeks() {
        if (selectedYear == (currentYear - MAX_YEAR_DIFFERENCE)) {
            return;
        } else {
            selectedYear = selectedYear - 1;
            changeYear(selectedYear);
        }
    }


    /**
     * Use for set week data of next year from current year.
     */
    private void setNextYearWeeks() {
        if (selectedYear == currentYear) {
            return;
        } else {
            selectedYear = selectedYear + 1;
            changeYear(selectedYear);
        }
    }

    /**
     * It is use to get given year weeks data and set it to list.
     *
     * @param year
     */
    private void changeYear(int year) {
        lisWeekData.clear();
        selectedWeek = null;
        tvDialogYear.setText("" + year);
        lisWeekData = calenderHelper.getWeeksOfYear(year);
        weekAdapter.notifyDataSetChanged();
    }


    public abstract void onSelectedWeek(WeekData weekData);

    public abstract void onCancel();
}
