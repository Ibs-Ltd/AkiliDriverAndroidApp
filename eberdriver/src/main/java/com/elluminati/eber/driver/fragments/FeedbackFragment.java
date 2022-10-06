package com.elluminati.eber.driver.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.components.MyFontButton;
import com.elluminati.eber.driver.components.MyFontEdittextView;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.responsemodels.IsSuccessResponse;
import com.elluminati.eber.driver.models.singleton.CurrentTrip;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.parse.ParseContent;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.GlideApp;
import com.elluminati.eber.driver.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by elluminati on 04-07-2016.
 */
public class FeedbackFragment extends BaseFragments {


    private MyFontTextView tvTripDistance, tvTripTime, tvDriverFullName;
    private MyFontEdittextView etComment;
    private ImageView ivDriverImage;
    private RatingBar ratingBar;
    private MyFontButton btnFeedBackDone, btnFeedBackCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View feedFrag = inflater.inflate(R.layout.fragment_feedback, container, false);
        drawerActivity.setTitleOnToolbar(drawerActivity.getResources().getString(R.string.text_feed_back));
        tvTripDistance = feedFrag.findViewById(R.id.tvTripDistance);
        tvTripTime = feedFrag.findViewById(R.id.tvTripTime);
        ivDriverImage = feedFrag.findViewById(R.id.ivDriverImage);
        etComment = feedFrag.findViewById(R.id.etComment);
        ratingBar = feedFrag.findViewById(R.id.ratingBar);
        tvDriverFullName = feedFrag.findViewById(R.id.tvDriverFullName);
        btnFeedBackDone = feedFrag.findViewById(R.id.btnFeedBackDone);
        btnFeedBackCancel = feedFrag.findViewById(R.id.btnFeedBackCancel);

        return feedFrag;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        drawerActivity.setToolbarBackgroundAndElevation(true, R.drawable.toolbar_bg_rounded_bottom, R.dimen.toolbar_elevation);
        drawerActivity.setTitleOnToolbar(drawerActivity.getResources().getString(R.string.text_feed_back));
        tvTripTime.setText(drawerActivity.parseContent.twoDigitDecimalFormat.format(CurrentTrip.getInstance().getTime()) + " " + drawerActivity.getResources().getString(R.string.text_unit_mins));
        tvTripDistance.setText(drawerActivity.parseContent.twoDigitDecimalFormat.format(CurrentTrip.getInstance().getDistance()) + " " + Utils.showUnit(drawerActivity, CurrentTrip.getInstance().getUnit()));

        GlideApp.with(drawerActivity).load(drawerActivity.currentTrip.getUserProfileImage()).fallback(R.drawable.ellipse).placeholder(R.drawable.ellipse).override(200, 200).dontAnimate().into(ivDriverImage);

        tvDriverFullName.setText(drawerActivity.currentTrip.getUserFirstName() + " " + drawerActivity.currentTrip.getUserLastName());

        btnFeedBackDone.setOnClickListener(this);
        btnFeedBackCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFeedBackDone:
                if (ratingBar.getRating() != 0) {
                    giveFeedBack();
                } else {
                    Utils.showToast(drawerActivity.getResources().getString(R.string.msg_give_ratting), drawerActivity);
                }
                break;

            case R.id.btnFeedBackCancel:
                drawerActivity.currentTrip.clearData();
                if (isAdded()) {
                    drawerActivity.goToMapFragment(false);
                }
                break;
            default:

                break;
        }
    }

    public void giveFeedBack() {

        JSONObject jsonObject = new JSONObject();
        Utils.showCustomProgressDialog(drawerActivity, drawerActivity.getResources().getString(R.string.msg_waiting_for_ratting), false, null);

        try {
            jsonObject.accumulate(Const.Params.PROVIDER_ID, drawerActivity.preferenceHelper.getProviderId());
            jsonObject.accumulate(Const.Params.TRIP_ID, drawerActivity.preferenceHelper.getTripId());
            jsonObject.accumulate(Const.Params.REVIEW, etComment.getText().toString());
            jsonObject.accumulate(Const.Params.TOKEN, drawerActivity.preferenceHelper.getSessionToken());
            jsonObject.accumulate(Const.Params.RATING, ratingBar.getRating());


            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).giveRating(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {
                    if (ParseContent.getInstance().isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            drawerActivity.currentTrip.clearData();
                            if (isAdded()) {
                                drawerActivity.goToMapFragment(false);
                            }
                            Utils.hideCustomProgressDialog();
                        } else {
                            Utils.showErrorToast(response.body().getErrorCode(), drawerActivity);
                            Utils.hideCustomProgressDialog();
                        }
                    }

                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    AppLog.handleThrowable(FeedbackFragment.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }

    }

}
