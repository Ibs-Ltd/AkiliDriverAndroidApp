package com.elluminati.eber.driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.elluminati.eber.driver.components.MyFontButton;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.responsemodels.IsSuccessResponse;
import com.elluminati.eber.driver.models.singleton.CurrentTrip;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowReferralActivity extends BaseAppCompatActivity {
    private MyFontTextView tvUserReferralCode;
    private MyFontButton btnShareReferralCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_referral);
        initToolBar();
        setTitleOnToolbar(getResources().getString(R.string.text_referral));
        tvUserReferralCode = findViewById(R.id.tvUserReferralCode);
        btnShareReferralCode = findViewById(R.id.btnShareReferralCode);
        btnShareReferralCode.setOnClickListener(this);
        tvUserReferralCode.setText(preferenceHelper.getReferralCode());
        getReferralCredits();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdminApprovedListener(this);
        setConnectivityListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShareReferralCode:
                shareReferralCode();
                break;

            default:

                break;
        }
    }

    private void shareReferralCode() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + "" + " " + getResources().getString(R.string.msg_my_referral_code_is) + " " + preferenceHelper.getReferralCode());
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.text_referral_share_with_)));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
    public void onAdminApproved() {
        goWithAdminApproved();
    }

    @Override
    public void onAdminDeclined() {
        goWithAdminDecline();
    }


    private void getReferralCredits() {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.PROVIDER_ID, preferenceHelper.getProviderId());

            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).getReferralCredit(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            TextView referralCredits = findViewById(R.id.tvUserReferralCradit);
                            referralCredits.setText(String.format("%s %s", response.body().getTotalReferralCredit(), CurrentTrip.getInstance().getWalletCurrencyCode()));
                        } else {
                            Utils.showErrorToast(response.body().getErrorCode(), ShowReferralActivity.this);
                        }
                    }

                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    AppLog.handleThrowable(ShowReferralActivity.class.getSimpleName(), t);

                }
            });

        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }
}
