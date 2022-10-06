package com.elluminati.eber.driver;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.elluminati.eber.driver.components.MyFontButton;
import com.elluminati.eber.driver.components.MyFontEdittextView;
import com.elluminati.eber.driver.models.responsemodels.IsSuccessResponse;
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

public class RefferralApplyActivity extends BaseAppCompatActivity {

    private LinearLayout llInvalidReferral;
    private MyFontEdittextView etReferralCode;
    private MyFontButton btnReferralSubmit, btnReferralSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reffral_apply);
        llInvalidReferral = findViewById(R.id.llInvalidReferral);
        etReferralCode = findViewById(R.id.etReferralCode);
        btnReferralSkip = findViewById(R.id.btnReferralSkip);
        btnReferralSubmit = findViewById(R.id.btnReferralSubmit);
        btnReferralSkip.setOnClickListener(this);
        btnReferralSubmit.setOnClickListener(this);
        updateUi(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdminApprovedListener(this);
        setConnectivityListener(this);
    }

    @Override
    protected boolean isValidate() {

        if (!TextUtils.isEmpty(etReferralCode.getText().toString())) {
            return true;
        } else {
            etReferralCode.requestFocus();
            etReferralCode.setError(getString(R.string.error_referral));
        }

        return false;
    }

    @Override
    public void goWithBackArrow() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReferralSubmit:
                if (isValidate()) {
                    applyReferral(Const.ProviderStatus.IS_REFERRAL_APPLY);
                }

                break;
            case R.id.btnReferralSkip:
                applyReferral(Const.ProviderStatus.IS_REFERRAL_SKIP);
                break;
            default:
                break;
        }
    }

    private void applyReferral(int status) {
        Utils.showCustomProgressDialog(this, "", false, null);


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
            if (status == Const.ProviderStatus.IS_REFERRAL_SKIP) {
                jsonObject.put(Const.Params.REFERRAL_CODE, preferenceHelper.getReferralCode());
            } else {
                jsonObject.put(Const.Params.REFERRAL_CODE, etReferralCode.getText().toString());
            }
            jsonObject.put(Const.Params.IS_SKIP, status);

            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).applyReferralCode(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {

                    if (parseContent.isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            preferenceHelper.putIsApplyReferral(Const.TRUE);
                            Utils.hideCustomProgressDialog();
                            Utils.showMessageToast(response.body().getMessage(), RefferralApplyActivity.this);
                            moveWithUserSpecificPreference();
                        } else {
                            Utils.hideCustomProgressDialog();
                            updateUi(true);
                        }

                    }

                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    AppLog.handleThrowable(RefferralApplyActivity.class.getSimpleName(), t);
                }
            });
        } catch (JSONException e) {
            AppLog.handleException(RefferralApplyActivity.class.getSimpleName(), e);
        }
    }

    private void updateUi(boolean isUpdate) {
        if (isUpdate) {
            llInvalidReferral.setVisibility(View.VISIBLE);
        } else {
            llInvalidReferral.setVisibility(View.GONE);
        }


    }

    @Override
    public void onBackPressed() {
        openExitDialog(this);
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
}
