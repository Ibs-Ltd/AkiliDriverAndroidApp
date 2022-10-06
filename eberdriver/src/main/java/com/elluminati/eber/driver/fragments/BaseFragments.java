package com.elluminati.eber.driver.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.elluminati.eber.driver.MainDrawerActivity;


/**
 * Created by elluminati on 14-06-2016.
 */
public abstract class BaseFragments extends Fragment implements View.OnClickListener {

    public String TAG = this.getClass().getSimpleName();
    protected MainDrawerActivity drawerActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerActivity = (MainDrawerActivity) getActivity();
    }
}
