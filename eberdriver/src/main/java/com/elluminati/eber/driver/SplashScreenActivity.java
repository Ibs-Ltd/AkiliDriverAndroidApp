package com.elluminati.eber.driver;

import static com.elluminati.eber.driver.utils.Const.REQUEST_UPDATE_APP;

import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.elluminati.eber.driver.components.CustomDialogBigLabel;
import com.elluminati.eber.driver.models.datamodels.AdminSettings;
import com.elluminati.eber.driver.models.responsemodels.SettingsDetailsResponse;
import com.elluminati.eber.driver.models.singleton.CurrentTrip;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.NetworkHelper;
import com.elluminati.eber.driver.utils.ServerConfig;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends BaseAppCompatActivity {

    private int oneTimeCall;
    private AppUpdateManager appUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (preferenceHelper.getGoogleAutoCompleteKey() != null) {
            Places.initialize(getApplicationContext(), preferenceHelper.getGoogleAutoCompleteKey());
        }
        CurrentTrip.getInstance().setAutocompleteSessionToken(AutocompleteSessionToken.newInstance());
        appUpdateManager = AppUpdateManagerFactory.create(SplashScreenActivity.this);
        setContentView(R.layout.activity_splash_screen);
        ServerConfig.setURL(this);
        checkIfGpsOrInternetIsEnable();
        TextView appTitle = findViewById(R.id.appTitle);
        appTitle.setText(Html.fromHtml(getString(R.string.app_title)));

    }

    @Override
    protected void onResume() {
        super.onResume();
        setConnectivityListener(this);
        setAdminApprovedListener(this);
    }

    @Override
    protected boolean isValidate() {
        return false;
    }

    @Override
    public void goWithBackArrow() {
        // do somethings
    }

    @Override
    public void onClick(View v) {
        // do somethings
    }

    private void checkIfGpsOrInternetIsEnable() {
        if (!NetworkHelper.getInstance().isInternetConnected()) {
            openInternetDialog();
        } else {
            closedEnableDialogGps();
            closedEnableDialogInternet();
            getAPIKeys();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            closedEnableDialogInternet();
            getAPIKeys();
        } else {
            openInternetDialog();
        }
    }

    @Override
    public void onGpsConnectionChanged(boolean isConnected) {
        if (isConnected) {
            closedEnableDialogGps();
            getAPIKeys();
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

    private void openUpdateAppDialog(final AppUpdateInfo result, final boolean isForceUpdate) {
        String btnNegative;
        if (isForceUpdate) {
            btnNegative = getResources().getString(R.string.text_exit_caps);
        } else {
            btnNegative = getResources().getString(R.string.text_not_now);
        }

        final CustomDialogBigLabel customDialogBigLabel = new CustomDialogBigLabel(this, getResources().getString(R.string.text_update_app), getResources().getString(R.string.meg_update_app), getResources().getString(R.string.text_update), btnNegative) {
            @Override
            public void positiveButton() {
                if (result != null) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, SplashScreenActivity.this, REQUEST_UPDATE_APP);
                    } catch (IntentSender.SendIntentException e) {
                        AppLog.handleException(SplashScreenActivity.class.getSimpleName(), e);
                    }
                    dismiss();
                } else {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google" + ".com/store/apps/details?id=" + getPackageName())));
                    }
                    dismiss();
                    finishAffinity();
                }
            }

            @Override
            public void negativeButton() {
                dismiss();
                if (isForceUpdate) {
                    finishAffinity();
                } else {
                    if (!TextUtils.isEmpty(preferenceHelper.getSessionToken())) {
                        goToMainDrawerActivity();
                        finish();
                    } else {
                        goToMainActivity();
                    }
                }
            }
        };
        if (!isFinishing()) {
            customDialogBigLabel.show();
        }
    }

    private boolean checkVersionCode(String code) {
        String[] appVersionWeb = code.split("\\.");
        String[] appVersion = getAppVersion().split("\\.");

        int sizeWeb = appVersionWeb.length;
        int sizeApp = appVersion.length;
        if (sizeWeb == sizeApp) {
            for (int i = 0; i < sizeWeb; i++) {
                if (Double.valueOf(appVersionWeb[i]) > Double.valueOf(appVersion[i])) {
                    return true;
                } else if ((Integer.valueOf(appVersionWeb[i]) == Integer.valueOf(appVersion[i]))) {
                } else {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    private void getAPIKeys() {
        if (oneTimeCall == 0) {
            oneTimeCall++;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Const.Params.PROVIDER_ID, !TextUtils.isEmpty(preferenceHelper.getSessionToken()) ? preferenceHelper.getProviderId() : null);
                jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
                jsonObject.put(Const.Params.APP_VERSION, getAppVersion());
                jsonObject.put(Const.Params.DEVICE_TYPE, Const.DEVICE_TYPE_ANDROID);
                Call<SettingsDetailsResponse> call = ApiClient.getClient().create(ApiInterface.class).getProviderSettingDetail(ApiClient.makeJSONRequestBody(jsonObject));
                call.enqueue(new Callback<SettingsDetailsResponse>() {
                    @Override
                    public void onResponse(Call<SettingsDetailsResponse> call, Response<SettingsDetailsResponse> response) {
                        if (parseContent.isSuccessful(response)) {
                            parseContent.parseProviderSettingDetail(response.body());
                            final AdminSettings adminSettings = response.body().getAdminSettings();
                            Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
                            appUpdateInfoTask.addOnSuccessListener(result -> {
                                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                                    openUpdateAppDialog(result, adminSettings.isAndroidProviderAppForceUpdate());
                                } else if (checkVersionCode(adminSettings.getAndroidProviderAppVersionCode())) {
                                    openUpdateAppDialog(null, adminSettings.isAndroidProviderAppForceUpdate());
                                } else if (TextUtils.isEmpty(preferenceHelper.getSessionToken())) {
                                    goToMainActivity();
                                } else {
                                    moveWithUserSpecificPreference();
                                }
                            }).addOnFailureListener(e -> {
                                AppLog.handleThrowable(SplashScreenActivity.class.getSimpleName(), e);
                                if (checkVersionCode(adminSettings.getAndroidProviderAppVersionCode())) {
                                    openUpdateAppDialog(null, adminSettings.isAndroidProviderAppForceUpdate());
                                } else if (TextUtils.isEmpty(preferenceHelper.getSessionToken())) {
                                    goToMainActivity();
                                } else {
                                    moveWithUserSpecificPreference();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<SettingsDetailsResponse> call, Throwable t) {
                        AppLog.handleThrowable(BaseAppCompatActivity.class.getSimpleName(), t);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE_APP) {
            if (resultCode != RESULT_OK) {
                finishAffinity();
            }
        }
    }
}
