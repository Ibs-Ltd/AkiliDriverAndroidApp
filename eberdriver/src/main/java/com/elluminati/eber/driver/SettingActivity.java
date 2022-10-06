package com.elluminati.eber.driver;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.adapter.SpeakingLanguageAdaptor;
import com.elluminati.eber.driver.components.CustomLanguageDialog;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.models.datamodels.Language;
import com.elluminati.eber.driver.models.responsemodels.IsSuccessResponse;
import com.elluminati.eber.driver.models.responsemodels.LanguageResponse;
import com.elluminati.eber.driver.models.singleton.CurrentTrip;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.parse.ParseContent;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.PreferenceHelper;
import com.elluminati.eber.driver.utils.ServerConfig;
import com.elluminati.eber.driver.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends BaseAppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private final ArrayList<Language> languages = new ArrayList<>();
    SwitchCompat switchRequestAlert, switchPickUpAlert, switchPushNotificationSound, switchHeatMap;
    private MyFontTextView tvVersion, tvLanguage;
    private CustomLanguageDialog customLanguageDialog;
    private RecyclerView rcvSpeakingLanguage;
    private CheckBox cbMale, cbFemale;
    private LinearLayout llNotification;
    private LinearLayout llLanguage;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initToolBar();
        setTitleOnToolbar(getResources().getString(R.string.text_settings));
        tvVersion = findViewById(R.id.tvVersion);
        switchRequestAlert = findViewById(R.id.switchRequestSound);
        switchPickUpAlert = findViewById(R.id.switchPickUpSound);
        switchPushNotificationSound = findViewById(R.id.switchPushNotificationSound);
        tvLanguage = findViewById(R.id.tvLanguage);
        switchHeatMap = findViewById(R.id.switchHeatMap);
        rcvSpeakingLanguage = findViewById(R.id.rcvSpeakingLanguage);
        llNotification = findViewById(R.id.llNotification);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            llNotification.setVisibility(View.GONE);
        } else {
            llNotification.setVisibility(View.VISIBLE);
        }
        switchPushNotificationSound.setOnCheckedChangeListener(this);
        switchRequestAlert.setOnCheckedChangeListener(this);
        switchPickUpAlert.setOnCheckedChangeListener(this);
        switchHeatMap.setOnCheckedChangeListener(this);
        tvLanguage.setOnClickListener(this);
        switchPushNotificationSound.setChecked(preferenceHelper.getIsPushNotificationSoundOn());
        switchRequestAlert.setChecked(preferenceHelper.getIsSoundOn());
        switchPickUpAlert.setChecked(preferenceHelper.getIsPickUpSoundOn());
        switchHeatMap.setChecked(preferenceHelper.getIsHeatMapOn());
        tvVersion.setText(getAppVersion());
        cbMale = findViewById(R.id.cbMale);
        cbFemale = findViewById(R.id.cbFemale);
        setLanguageName();
        getSpeakingLanguages();
        llLanguage = findViewById(R.id.llLanguage);
        llLanguage.setOnClickListener(this);
        //  setToolbarRightSideButton(getResources().getString(R.string.text_save), this);
        setToolbarIcon(AppCompatResources.getDrawable(this, R.drawable.ic_done_black_24dp), this);

        if (BuildConfig.APPLICATION_ID.equalsIgnoreCase("com.elluminatiinc.taxi.driver")) {
            findViewById(R.id.llAppVersion).setOnTouchListener(new View.OnTouchListener() {

                final Handler handler = new Handler();
                int numberOfTaps = 0;
                long lastTapTimeMs = 0;
                long touchDownMs = 0;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touchDownMs = System.currentTimeMillis();
                            break;
                        case MotionEvent.ACTION_UP:
                            handler.removeCallbacksAndMessages(null);
                            if (System.currentTimeMillis() - touchDownMs > ViewConfiguration.getTapTimeout()) {
                                numberOfTaps = 0;
                                lastTapTimeMs = 0;
                            }
                            if (numberOfTaps > 0
                                    && System.currentTimeMillis() - lastTapTimeMs < ViewConfiguration.getDoubleTapTimeout()
                            ) {
                                numberOfTaps += 1;
                            } else {
                                numberOfTaps = 1;
                            }
                            lastTapTimeMs = System.currentTimeMillis();

                            if (numberOfTaps == 3) {
                                showServerDialog();
                            }
                    }
                    return true;
                }
            });
        }
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
        onBackPressed();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llLanguage:
                openLanguageDialog();
                break;
            case R.id.ivToolbarIcon:
                upDateSettings();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switchRequestSound:
                preferenceHelper.putIsSoundOn(isChecked);
                break;
            case R.id.switchPickUpSound:
                preferenceHelper.putIsPickUpSoundOn(isChecked);
                break;
            case R.id.switchPushNotificationSound:
                preferenceHelper.putIsPushNotificationSoundOn(isChecked);
                break;
            case R.id.switchHeatMap:
                preferenceHelper.putIsHeatMapOn(isChecked);
                break;
            default:

                break;
        }

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

    private void showServerDialog() {
        final Dialog serverDialog = new Dialog(this);
        serverDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        serverDialog.setContentView(R.layout.dialog_server);

        RadioGroup dialogRadioGroup = serverDialog.findViewById(R.id.dialogRadioGroup);
        RadioButton rbServer1 = serverDialog.findViewById(R.id.rbServer1);
        RadioButton rbServer2 = serverDialog.findViewById(R.id.rbServer2);
        RadioButton rbServer3 = serverDialog.findViewById(R.id.rbServer3);

        switch (ServerConfig.BASE_URL) {
            case "https://eber.appemporio.net/":
                rbServer1.setChecked(true);
                break;
            case "https://staging.appemporio.net/":
                rbServer2.setChecked(true);
                break;
            case "https://eberdeveloper.appemporio.net/":
                rbServer3.setChecked(true);
                break;
        }

        serverDialog.findViewById(R.id.btnCancel).setOnClickListener(v -> serverDialog.dismiss());
        serverDialog.findViewById(R.id.btnOk).setOnClickListener(v -> {

            int id = dialogRadioGroup.getCheckedRadioButtonId();
            if (id == R.id.rbServer1) {
                ServerConfig.BASE_URL = "https://eber.appemporio.net/";
            } else if (id == R.id.rbServer2) {
                ServerConfig.BASE_URL = "https://staging.appemporio.net/";
            } else if (id == R.id.rbServer3) {
                ServerConfig.BASE_URL = "https://eberdeveloper.appemporio.net/";
            }

            serverDialog.dismiss();

            if (!ServerConfig.BASE_URL.equals(PreferenceHelper.getInstance(this).getBaseUrl())) {
                PreferenceHelper.getInstance(this).putBaseUrl(ServerConfig.BASE_URL);
                logOut(true);
            }
        });
        WindowManager.LayoutParams params = serverDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        serverDialog.getWindow().setAttributes(params);
        serverDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        serverDialog.setCancelable(false);
        serverDialog.show();
    }

    private void openLanguageDialog() {
        if (customLanguageDialog != null && customLanguageDialog.isShowing()) {
            return;
        }
        customLanguageDialog = new CustomLanguageDialog(this) {
            @Override
            public void onSelect(String languageName, String languageCode) {
                tvLanguage.setText(languageName);
                if (!TextUtils.equals(preferenceHelper.getLanguageCode(), languageCode)) {
                    preferenceHelper.putLanguageCode(languageCode);
                    finishAffinity();
                    restartApp();
                }
                dismiss();
            }
        };
        customLanguageDialog.show();
    }

    private void setLanguageName() {
        TypedArray array = getResources().obtainTypedArray(R.array.language_code);
        TypedArray array2 = getResources().obtainTypedArray(R.array.language_name);
        int size = array.length();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(preferenceHelper.getLanguageCode(), array.getString(i))) {
                tvLanguage.setText(array2.getString(i));
                break;
            }
        }

    }


    private void getSpeakingLanguages() {
        JSONObject jsonObject = new JSONObject();
        Call<LanguageResponse> call = ApiClient.getClient().create(ApiInterface.class).getLanguageForTrip(ApiClient.makeJSONRequestBody(jsonObject));
        call.enqueue(new Callback<LanguageResponse>() {
            @Override
            public void onResponse(Call<LanguageResponse> call, Response<LanguageResponse> response) {
                if (ParseContent.getInstance().isSuccessful(response)) {
                    if (response.body().isSuccess()) {
                        for (int i = 0; i < CurrentTrip.getInstance().getSpeakingLanguages().size(); i++) {
                            List<Language> languages = response.body().getLanguages();
                            for (int j = 0; j < languages.size(); j++) {
                                if (TextUtils.equals(CurrentTrip.getInstance().getSpeakingLanguages().get(i), languages.get(j).getId())) {
                                    languages.get(j).setSelected(true);
                                }
                            }
                        }
                        languages.addAll(response.body().getLanguages());
                        rcvSpeakingLanguage.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
                        rcvSpeakingLanguage.setAdapter(new SpeakingLanguageAdaptor(SettingActivity.this, languages));
                        rcvSpeakingLanguage.setNestedScrollingEnabled(false);
                    } else {
                        Utils.showErrorToast(response.body().getErrorCode(), SettingActivity.this);
                    }
                }


            }

            @Override
            public void onFailure(Call<LanguageResponse> call, Throwable t) {
                AppLog.handleThrowable(SettingActivity.class.getSimpleName(), t);
            }
        });
    }


    private void upDateSettings() {

        JSONObject jsonObject = new JSONObject();
        JSONArray lanJsonArray = new JSONArray();
        //  JSONArray genderJsonArray = new JSONArray();
        try {

            for (Language language : languages) {
                if (language.isSelected()) {
                    lanJsonArray.put(language.getId());
                }

            }
            if (lanJsonArray.length() == 0) {
                Utils.showToast(getResources().getString(R.string.msg_plz_select_at_one_language), this);
                return;
            }

            jsonObject.put(Const.Params.PROVIDER_ID, preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.LANGUAGES, lanJsonArray);
            Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_waiting_for_update_profile), false, null);
            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).updateProviderSetting(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {
                    Utils.hideCustomProgressDialog();
                    if (response.body().isSuccess()) {
                        onBackPressed();
                    } else {
                        Utils.showErrorToast(response.body().getErrorCode(), SettingActivity.this);
                    }
                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    AppLog.handleThrowable(SettingActivity.class.getSimpleName(), t);
                }
            });

        } catch (Exception e) {
            AppLog.handleException(TAG, e);
        }
    }

    private void setGenderWiseRequests() {
        for (String gender : CurrentTrip.getInstance().getGenderWiseRequests()) {
            if (TextUtils.equals(Const.Gender.MALE, gender)) {
                cbMale.setChecked(true);
            }
            if (TextUtils.equals(Const.Gender.FEMALE, gender)) {
                cbFemale.setChecked(true);
            }
        }
    }

}
