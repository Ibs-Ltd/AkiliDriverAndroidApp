package com.elluminati.eber.driver.components;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.DatePicker;

import com.elluminati.eber.driver.R;


public class ExpirationDatePickerDialog extends DatePickerDialog implements DatePicker.OnDateChangedListener {

    public ExpirationDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, Build.VERSION.SDK_INT >= 21 ? R.style.MyDialogTheme : 0, callBack, year, monthOfYear, dayOfMonth);

        init(context);
    }

    private void init(Context context) {
        getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        getDatePicker().setCalendarViewShown(false);
        MyAppTitleFontTextView myFontTextView = new MyAppTitleFontTextView(context);
        myFontTextView.setText(context.getString(R.string.text_card_expire_date));
        myFontTextView.setTextColor(Color.BLACK);
        myFontTextView.setTextSize(context.getResources().getDimensionPixelOffset(R.dimen.size_big_text));
        myFontTextView.setPaddingRelative(context.getResources().getDimensionPixelOffset(R.dimen.dimen_horizontal_margin), context.getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin), context.getResources().getDimensionPixelOffset(R.dimen.dimen_horizontal_margin), context.getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin));
        setCustomTitle(myFontTextView);
        int day = context.getResources().getIdentifier("android:id/day", null, null);
        if (day != 0) {
            View dayPicker = getDatePicker().findViewById(day);
            if (dayPicker != null) {
                dayPicker.setVisibility(View.GONE);
            }
        }
    }

    public void onDateChanged(DatePicker view, int year, int month, int day) {

    }


}