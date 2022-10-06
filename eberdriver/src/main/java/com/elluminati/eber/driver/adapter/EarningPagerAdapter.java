package com.elluminati.eber.driver.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elluminati on 18-Aug-17.
 */

public class EarningPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public EarningPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        this.fragmentList.add(fragment);
        this.fragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
}
