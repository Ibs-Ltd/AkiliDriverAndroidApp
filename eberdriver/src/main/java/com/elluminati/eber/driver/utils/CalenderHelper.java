package com.elluminati.eber.driver.utils;

import com.elluminati.eber.driver.models.datamodels.WeekData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by elluminati on 19-Aug-17.
 */

public class CalenderHelper {

    private final ArrayList<WeekData> listWeekData;
    private int totalWeekOfYear;

    public CalenderHelper() {
        listWeekData = new ArrayList<>();
    }


    public ArrayList<WeekData> getWeeksOfYear(int year) {

        listWeekData.clear();
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);

        int ordinalDay = calendar.get(Calendar.DAY_OF_YEAR);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
        int numberOfWeeks = (ordinalDay - weekDay + 10) / 7;


        for (int i = 1; i <= numberOfWeeks; i++) {
            calendar.set(Calendar.WEEK_OF_YEAR, i);
            if (calendar.getTimeInMillis() > calendar1.getTimeInMillis() - 86400000) {
                break;
            }
            calendar.set(Calendar.DAY_OF_WEEK, 0);
            ArrayList<Date> integersDate = new ArrayList<>();
            for (int j = 1; j <= 7; j++) {
                calendar.add(Calendar.DAY_OF_WEEK, 1);
                if (j == 1) {
                    integersDate.add(calendar.getTime());
                }
                if (j == 7) {
                    integersDate.add(calendar.getTime());
                }

            }
            listWeekData.add(loadData(integersDate));
        }

        Collections.reverse(listWeekData);

        return listWeekData;
    }


    private WeekData loadData(ArrayList<Date> listDate) {
        WeekData weekData = new WeekData();
        weekData.setListWeekDate(listDate);
        return weekData;
    }

    public ArrayList<Date> getCurrentWeekDates() {
        ArrayList<Date> dateArrayList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_WEEK, -dayOfWeek);
        for (int j = 1; j <= 7; j++) {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            if (j == 1) {
                dateArrayList.add(calendar.getTime());
            }
            if (j == 7) {
                dateArrayList.add(calendar.getTime());
            }

        }
        return dateArrayList;
    }

}
