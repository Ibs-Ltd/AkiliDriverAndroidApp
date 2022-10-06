package com.elluminati.eber.driver;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.elluminati.eber.driver.adapter.EarningPagerAdapter;
import com.elluminati.eber.driver.fragments.DayEarningFragment;
import com.elluminati.eber.driver.fragments.WeekEarningFragment;
import com.elluminati.eber.driver.utils.AppLog;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;
import java.util.Date;

public class EarningActivity extends BaseAppCompatActivity {

    private final String TAG = "EarningActivity";
    public ViewPager earningPager;
    public DayEarningFragment dailyEarningFragment;
    private TabLayout earningTabs;
    private WeekEarningFragment weeklyEarningFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning);
        setDefaultFont(this, "MONOSPACE", "fonts/Roboto_Regular_0.ttf");
        initToolBar();
        setTitleOnToolbar(getResources().getString(R.string.text_earning));
        dailyEarningFragment = new DayEarningFragment();
        weeklyEarningFragment = new WeekEarningFragment();
        earningPager = findViewById(R.id.earningPager);
        setUpViewPager(earningPager);
        earningTabs = findViewById(R.id.earningTabs);
        earningTabs.setupWithViewPager(earningPager);

    }

    private void setDefaultFont(Context context, String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(), fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }


    private void replaceFont(String staticTypefaceFieldName, final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            AppLog.handleException(TAG, e);
        } catch (IllegalAccessException e) {
            AppLog.handleException(TAG, e);
        }
    }


    private void setUpViewPager(ViewPager viewPager) {
        EarningPagerAdapter earningPagerAdapter = new EarningPagerAdapter(getSupportFragmentManager());

        earningPagerAdapter.addFragment(dailyEarningFragment, getString(R.string.text_day));
        earningPagerAdapter.addFragment(weeklyEarningFragment, getString(R.string.text_week));

        viewPager.setAdapter(earningPagerAdapter);
    }

    @Override
    protected boolean isValidate() {
        return false;
    }

    @Override
    public void goWithBackArrow() {
        onBackPressed();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onAdminApproved() {
        goWithAdminApproved();
    }

    @Override
    public void onAdminDeclined() {
        goWithAdminDecline();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            closedEnableDialogInternet();
        } else {
            openInternetDialog();
        }
    }

    @Override
    public void onGpsConnectionChanged(boolean isConnected) {
        if (isConnected) {
            closedEnableDialogGps();
        } else {
            openGpsDialog();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void getDayEarning(Date date) {
        if (dailyEarningFragment != null) {
            dailyEarningFragment.getDailyEarning(date);
        }

    }
}
