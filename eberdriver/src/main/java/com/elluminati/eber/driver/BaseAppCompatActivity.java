package com.elluminati.eber.driver;

import static com.elluminati.eber.driver.utils.Utils.isScreenOn;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.elluminati.eber.driver.components.CustomDialogBigLabel;
import com.elluminati.eber.driver.components.CustomDialogEnable;
import com.elluminati.eber.driver.components.MyAppTitleFontTextView;
import com.elluminati.eber.driver.components.MyFontButton;
import com.elluminati.eber.driver.interfaces.AdminApprovedListener;
import com.elluminati.eber.driver.interfaces.ConnectivityReceiverListener;
import com.elluminati.eber.driver.interfaces.OnSingleClickListener;
import com.elluminati.eber.driver.models.responsemodels.GenerateFirebaseTokenResponse;
import com.elluminati.eber.driver.models.responsemodels.IsSuccessResponse;
import com.elluminati.eber.driver.parse.ApiClient;
import com.elluminati.eber.driver.parse.ApiInterface;
import com.elluminati.eber.driver.parse.ParseContent;
import com.elluminati.eber.driver.utils.AppLog;
import com.elluminati.eber.driver.utils.Const;
import com.elluminati.eber.driver.utils.CurrencyHelper;
import com.elluminati.eber.driver.utils.LanguageHelper;
import com.elluminati.eber.driver.utils.NetworkHelper;
import com.elluminati.eber.driver.utils.PreferenceHelper;
import com.elluminati.eber.driver.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by elluminati on 10-05-2016.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiverListener, AdminApprovedListener {
    private final AppReceiver appReceiver = new AppReceiver();
    private final LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
    public TextView tvTitle;
    public MyAppTitleFontTextView tvTimeRemain;
    public PreferenceHelper preferenceHelper;
    public ParseContent parseContent;
    public String TAG = this.getClass().getSimpleName();
    public CurrencyHelper currencyHelper;
    protected Toolbar toolbar;
    protected ActionBar actionBar;
    private MyFontButton btnToolBar;
    private ImageView ivToolbarIcon;
    private CustomDialogEnable customDialogEnableGps, customDialogEnableInternet;
    private CustomDialogBigLabel customDialogExit, customDialogUnderReview;
    private ConnectivityReceiverListener connectivityReceiverListener;
    private AdminApprovedListener adminApprovedListener;
    private NetworkHelper networkHelper;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.color_app_status_bar_green, null));
        preferenceHelper = PreferenceHelper.getInstance(this);
        parseContent = ParseContent.getInstance();
        currencyHelper = CurrencyHelper.getInstance(this);
        parseContent.getContext(getApplicationContext());
        parseContent.initDateTimeFormat();
        adjustFontScale(getResources().getConfiguration());
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(Const.GPS_ACTION);
        intentFilter.addAction(Const.ACTION_DECLINE_PROVIDER);
        intentFilter.addAction(Const.ACTION_APPROVED_PROVIDER);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            networkHelper = NetworkHelper.getInstance();
            networkHelper.initConnectivityManager(this);
        } else {
            intentFilter.addAction(Const.NETWORK_ACTION);
        }
        registerReceiver(appReceiver, intentFilter);
        localBroadcastManager.registerReceiver(appReceiver, new IntentFilter(Const.ACTION_NEW_TRIP));
        mAuth = FirebaseAuth.getInstance();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    protected void initToolBar() {
        toolbar = findViewById(R.id.myToolbar);
        tvTitle = toolbar.findViewById(R.id.tvTitle);
        ivToolbarIcon = toolbar.findViewById(R.id.ivToolbarIcon);
        tvTimeRemain = toolbar.findViewById(R.id.tvTimeRemain);
        btnToolBar = toolbar.findViewById(R.id.btnToolBar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goWithBackArrow();
            }
        });
    }

    public void setTitleOnToolbar(String title) {
        tvTitle.setText(title);
        tvTitle.setVisibility(View.VISIBLE);
        tvTimeRemain.setVisibility(View.GONE);
        ivToolbarIcon.setVisibility(View.GONE);
        btnToolBar.setVisibility(View.GONE);
    }

    public void setToolbarIcon(Drawable drawable, View.OnClickListener onClickListener) {
        ivToolbarIcon.setImageDrawable(drawable);
        ivToolbarIcon.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onClickListener.onClick(v);
            }
        });
        ivToolbarIcon.setVisibility(View.VISIBLE);
    }

    public void setToolbarRightSideButton(String title, View.OnClickListener onClickListener) {
        ivToolbarIcon.setVisibility(View.GONE);
        tvTimeRemain.setVisibility(View.GONE);
        btnToolBar.setVisibility(View.VISIBLE);
        btnToolBar.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onClickListener.onClick(v);
            }
        });
        btnToolBar.setText(title);
    }

    public void setToolbarBackgroundAndElevation(boolean isDrawable, int resId, int elevationId) {
        if (toolbar != null) {
            if (isDrawable) {
                toolbar.setBackground(AppCompatResources.getDrawable(this, resId));
            } else {
                toolbar.setBackgroundColor(ResourcesCompat.getColor(getResources(), resId, null));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (elevationId <= 0) {
                    toolbar.setElevation(0);
                } else {
                    toolbar.setElevation(getResources().getDimensionPixelOffset(elevationId));
                }


            }
        }
    }

    public void hideToolbarButton() {
        btnToolBar.setVisibility(View.GONE);
    }

    protected void openExitDialog(final Activity activity) {

        customDialogExit = new CustomDialogBigLabel(this, getString(R.string.text_exit_caps), getString(R.string.msg_are_you_sure), getString(R.string.text_yes), getString(R.string.text_no)) {
            @Override
            public void positiveButton() {
                if (!activity.isFinishing() && customDialogExit.isShowing()) {
                    customDialogExit.dismiss();
                }
                activity.finishAffinity();
            }

            @Override
            public void negativeButton() {
                customDialogExit.dismiss();
            }
        };
        if (!activity.isFinishing()) {
            customDialogExit.show();
        }
    }

    protected void openGpsDialog() {
        if (customDialogEnableGps != null && customDialogEnableGps.isShowing()) {
            return;
        }
        customDialogEnableGps = new CustomDialogEnable(this, getString(R.string.msg_gps_enable), getString(R.string.text_no), getString(R.string.text_yes)) {
            @Override
            public void doWithEnable() {
                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), Const.ACTION_SETTINGS);
            }

            @Override
            public void doWithDisable() {
                closedEnableDialogGps();
            }
        };
        if (!this.isFinishing()) {

            customDialogEnableGps.show();
        }
    }


    protected void openInternetDialog() {
        if (customDialogEnableInternet != null && customDialogEnableInternet.isShowing()) {
            return;
        }

        customDialogEnableInternet = new CustomDialogEnable(this, getString(R.string.msg_internet_enable), getString(R.string.text_no), getString(R.string.text_yes)) {
            @Override
            public void doWithEnable() {
                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), Const.ACTION_SETTINGS);
            }

            @Override
            public void doWithDisable() {
                closedEnableDialogInternet();
                finishAffinity();
            }
        };

        if (!this.isFinishing()) customDialogEnableInternet.show();

    }

    protected void closedEnableDialogGps() {
        if (customDialogEnableGps != null && customDialogEnableGps.isShowing()) {
            customDialogEnableGps.dismiss();
            customDialogEnableGps = null;

        }
    }

    protected void closedEnableDialogInternet() {
        try {
            if (customDialogEnableInternet != null && customDialogEnableInternet.isShowing()) {
                customDialogEnableInternet.dismiss();
                customDialogEnableInternet = null;
            }
        } catch (Exception e) {
            AppLog.handleException(TAG, e);
        }
    }


    protected void goToDocumentActivity(boolean isClickInsideDrawer) {
        Intent docIntent = new Intent(this, DocumentActivity.class);
        docIntent.putExtra(Const.IS_CLICK_INSIDE_DRAWER, isClickInsideDrawer);
        startActivity(docIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    protected void goToReferralActivity() {
        Intent docIntent = new Intent(this, RefferralApplyActivity.class);
        startActivity(docIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    protected void goToShowReferralActivity() {
        Intent docIntent = new Intent(this, ShowReferralActivity.class);
        startActivity(docIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    protected void goToMainDrawerActivity() {
        Intent mainDrawerIntent = new Intent(this, MainDrawerActivity.class);
        mainDrawerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainDrawerIntent);
        overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
    }

    protected void goToMainActivity() {
        Intent sigInIntent = new Intent(this, MainActivity.class);
        sigInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(sigInIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public void goToSplashActivity() {
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finishAffinity();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    protected void goToRegisterActivity() {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    protected void goToSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void goToAddVehicleDetailActivity(boolean isAddVehicle, String vehicleId) {
        Intent intent = new Intent(this, AddVehicleDetailActivity.class);
        intent.putExtra(Const.IS_ADD_VEHICLE, isAddVehicle);
        intent.putExtra(Const.VEHICLE_ID, vehicleId);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    protected abstract boolean isValidate();


    /**
     * @param email
     * @param vehicleId
     * @deprecated
     */
    public void openUnderReviewDialog(final String email, final String vehicleId) {

        if (customDialogUnderReview != null && customDialogUnderReview.isShowing()) {
            return;
        }

        final String positiveButtonTitle;
        final String message;

        if (TextUtils.equals(email, Const.ADD_VEHICLE)) {
            if (TextUtils.isEmpty(vehicleId))
                positiveButtonTitle = getString(R.string.text_add_vehicle);
            else positiveButtonTitle = getString(R.string.text_edit_vehicle);

            message = getString(R.string.message_add_vehicle);
        } else {
            positiveButtonTitle = getString(R.string.text_email);
            message = getString(R.string.msg_under_review);
        }

        customDialogUnderReview = new CustomDialogBigLabel(this, getString(R.string.text_admin_alert), message, positiveButtonTitle, getString(R.string.text_logout)) {
            @Override
            public void positiveButton() {
                if (TextUtils.equals(positiveButtonTitle, getString(R.string.text_add_vehicle)) || TextUtils.equals(positiveButtonTitle, getString(R.string.text_edit_vehicle))) {
                    closedUnderReviewDialog();
                    if (TextUtils.isEmpty(vehicleId)) {
                        goToAddVehicleDetailActivity(true, null);
                    } else {
                        goToAddVehicleDetailActivity(false, vehicleId);
                    }
                } else {
                    contactUsWithEmail(email);
                }
            }

            @Override
            public void negativeButton() {
                closedUnderReviewDialog();
                logOut(false);

            }
        };
        customDialogUnderReview.show();
    }

    public void closedUnderReviewDialog() {
        if (customDialogUnderReview != null && customDialogUnderReview.isShowing()) {
            customDialogUnderReview.dismiss();
            customDialogUnderReview = null;

        }
    }


    public void logOut(boolean isForServer) {
        Utils.showCustomProgressDialog(this, getResources().getString(R.string.msg_waiting_for_log_out), false, null);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.PROVIDER_ID, preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());

            Call<IsSuccessResponse> call = ApiClient.getClient().create(ApiInterface.class).logout(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<IsSuccessResponse>() {
                @Override
                public void onResponse(Call<IsSuccessResponse> call, Response<IsSuccessResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        if (response.body().isSuccess()) {
                            Utils.hideCustomProgressDialog();
                            Utils.showMessageToast(response.body().getMessage(), BaseAppCompatActivity
                                    .this);
                            preferenceHelper.logout();// clear session token
                            if (isForServer) {
                                goToSplashActivity();
                            } else {
                                goToMainActivity();
                            }
                        } else {
                            Utils.hideCustomProgressDialog();
                            Utils.showErrorToast(response.body().getErrorCode(), BaseAppCompatActivity.this);
                        }
                    }


                }

                @Override
                public void onFailure(Call<IsSuccessResponse> call, Throwable t) {
                    AppLog.handleThrowable(BaseAppCompatActivity.class.getSimpleName(), t);
                }
            });

        } catch (JSONException e) {
            AppLog.handleException(TAG, e);
        }
    }

    protected void contactUsWithEmail(String email) {
        Uri gmmIntentUri = Uri.parse("mailto:" + email + "?subject=" + getString(R.string.text_request_to_admin) + "&body=" + getString(R.string.text_hello_sir));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.gm");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Utils.showToast(getResources().getString(R.string.msg_google_mail_app_not_installed), this);
        }
    }


    public abstract void goWithBackArrow();


    public void setConnectivityListener(ConnectivityReceiverListener listener) {
        connectivityReceiverListener = listener;
        if (networkHelper != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkHelper.setNetworkAvailableListener(connectivityReceiverListener);
        }
    }

    public void setAdminApprovedListener(AdminApprovedListener listener) {
        adminApprovedListener = listener;
    }

    public void goWithAdminApproved() {
        preferenceHelper.putIsApproved(Const.ProviderStatus.IS_APPROVED);
        goToMainDrawerActivity();
    }

    public void goWithAdminDecline() {
        preferenceHelper.putIsProviderOnline(Const.ProviderStatus.PROVIDER_STATUS_OFFLINE);
        preferenceHelper.putIsApproved(Const.ProviderStatus.IS_DECLINED);
        goToMainDrawerActivity();
    }

    public String getAppVersion() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            AppLog.handleException(BaseAppCompatActivity.class.getName(), e);
        }
        return null;
    }

    protected Intent getIntentForPermission() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * Remove  comment when you live eber in palyStore
         */
//        if (!TextUtils.equals(Locale.getDefault().getLanguage(), Const.EN)) {
//            openLanguageDialog();
//        }
    }

    private void openLanguageDialog() {

        final CustomDialogBigLabel customDialogBigLabel = new CustomDialogBigLabel(this, getResources().getString(R.string.text_attention), getResources().getString(R.string.meg_language_not_an_english), getResources().getString(R.string.text_settings), getResources().getString(R.string.text_exit_caps)) {
            @Override
            public void positiveButton() {
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                dismiss();
            }

            @Override
            public void negativeButton() {
                dismiss();
                finishAffinity();

            }
        };
        customDialogBigLabel.show();
    }

    public void adjustFontScale(Configuration configuration) {
        if (configuration.fontScale > Const.DEFAULT_FONT_SCALE) {
            configuration.fontScale = Const.DEFAULT_FONT_SCALE;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            getBaseContext().getResources().updateConfiguration(configuration, metrics);
        }
    }

    public void hideKeyBord() {
        InputMethodManager inm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void restartApp() {
        startActivity(new Intent(this, SplashScreenActivity.class));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageHelper.wrapper(newBase, PreferenceHelper.getInstance(newBase).getLanguageCode()));
    }

    @Override
    protected void onDestroy() {
        if (ivToolbarIcon != null) {
            ivToolbarIcon.setOnClickListener(null);
        }
        closedEnableDialogInternet();
        unregisterReceiver(appReceiver);
        localBroadcastManager.unregisterReceiver(appReceiver);
        Utils.hideCustomProgressDialog();
        super.onDestroy();
    }

    /**
     * this method used to make decision after login or register for screen transaction with
     * specific preference
     */
    public void moveWithUserSpecificPreference() {
        if (preferenceHelper.getIsHaveReferral() && preferenceHelper.getIsApplyReferral() == Const.FALSE) {
            goToReferralActivity();
        } else if (preferenceHelper.getAllDocUpload() == Const.FALSE) {
            goToDocumentActivity(false);
        } else {
            goToMainDrawerActivity();
        }
    }


    @Override
    public void onBackPressed() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        super.onBackPressed();
    }

    public class AppReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (intent != null && intent.getAction() != null) {
                switch (intent.getAction()) {
                    case Const.NETWORK_ACTION:
                        if (connectivityReceiverListener != null) {
                            connectivityReceiverListener.onNetworkConnectionChanged(networkHelper.isInternetConnected());
                        }
                        break;
                    case Const.GPS_ACTION:
                        if (connectivityReceiverListener != null) {
                            connectivityReceiverListener.onGpsConnectionChanged(Utils.isGpsEnable(context));
                        }
                        break;
                    case Const.ACTION_APPROVED_PROVIDER:
                        if (adminApprovedListener != null) {
                            adminApprovedListener.onAdminApproved();
                        }
                        break;
                    case Const.ACTION_DECLINE_PROVIDER:
                        if (adminApprovedListener != null) {
                            adminApprovedListener.onAdminDeclined();
                        }
                        break;
                    case Const.ACTION_NEW_TRIP:
                        if (preferenceHelper.getIsMainScreenVisible()) {
                            return;
                        } else {
                            preferenceHelper.putIsScreenLock(!isScreenOn(context));
                            Intent mapIntent = new Intent(context, MainDrawerActivity.class);
                            mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mapIntent.putExtra(Const.Params.IS_FROM_NOTIFICATION, true);
                            startActivity(mapIntent);
                        }
                        break;
                    default:

                        break;
                }
            }
        }


    }

    public void generateFirebaseAccessToken() {
        Utils.showCustomProgressDialog(this, "", false, null);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Const.Params.USER_ID, preferenceHelper.getProviderId());
            jsonObject.put(Const.Params.TOKEN, preferenceHelper.getSessionToken());
            jsonObject.put(Const.Params.TYPE, Const.PROVIDER_UNIQUE_NUMBER);

            Call<GenerateFirebaseTokenResponse> call = ApiClient.getClient().create(ApiInterface.class).generateFirebaseAccessToken(ApiClient.makeJSONRequestBody(jsonObject));
            call.enqueue(new Callback<GenerateFirebaseTokenResponse>() {
                @Override
                public void onResponse(Call<GenerateFirebaseTokenResponse> call, Response<GenerateFirebaseTokenResponse> response) {
                    if (parseContent.isSuccessful(response)) {
                        if (response.body().getSuccess()) {
                            Log.i("AAA", "generateFirebaseAccessToken");
                            Utils.hideCustomProgressDialog();
                            preferenceHelper.putFirebaseUserToken(response.body().getFirebaseToken());
                            signInAnonymously();
                        } else {
                            Utils.hideCustomProgressDialog();
                            Utils.showErrorToast(response.body().getErrorCode(), BaseAppCompatActivity.this);
                        }
                    }


                }

                @Override
                public void onFailure(Call<GenerateFirebaseTokenResponse> call, Throwable t) {
                    AppLog.handleThrowable(BaseAppCompatActivity.class.getSimpleName(), t);
                }
            });

        } catch (JSONException e) {
            AppLog.handleException(BaseAppCompatActivity.class.getName(), e);
        }
    }

    private void signInAnonymously() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            if (!TextUtils.isEmpty(preferenceHelper.getFirebaseUserToken())) {
                mAuth.signInWithCustomToken(preferenceHelper.getFirebaseUserToken()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Utils.showToast("Authentication failed.", BaseAppCompatActivity.this);
                        }
                    }
                });
            }

        }
    }
}
