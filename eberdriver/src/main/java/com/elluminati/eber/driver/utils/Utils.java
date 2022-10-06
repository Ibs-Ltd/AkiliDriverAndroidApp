package com.elluminati.eber.driver.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.elluminati.eber.driver.MainActivity;
import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.adapter.CircularProgressViewAdapter;
import com.elluminati.eber.driver.components.CustomCircularProgressView;
import com.elluminati.eber.driver.components.MyFontTextView;
import com.elluminati.eber.driver.interfaces.OnProgressCancelListener;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.POWER_SERVICE;


/**
 * Created by elluminati on 31-03-2016.
 */
public class Utils {

    public static final String TAG = "Utils";
    private static Dialog dialog, dialogProgress;
    private static CustomCircularProgressView ivProgressBar;

    @Deprecated
    public static boolean isInternetConnected(Context context) {

        boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return isConnected;

    }

    public static boolean isGpsEnable(Context context) {
        boolean isGpsEnable = false;
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager != null) {
            isGpsEnable = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return isGpsEnable;
    }

    public static String getTimeZoneName() {
        return TimeZone.getDefault().getID();
    }


    public static void showHttpErrorToast(int code, Context context) {
        String msg;
        String errorCode = Const.HTTP_ERROR_CODE_PREFIX + code;
        try {
            msg = context.getResources().getString(context.getResources().getIdentifier(errorCode, Const.STRING, context.getPackageName()));
            showToast(msg, context);
        } catch (Resources.NotFoundException e) {
            msg = errorCode;
            AppLog.handleException(TAG, e);
        }


    }

    public static void showErrorToast(int code, Context context) {
        String msg;
        String errorCode = Const.ERROR_CODE_PREFIX + code;
        try {
            msg = context.getResources().getString(context.getResources().getIdentifier(errorCode, Const.STRING, context.getPackageName()));
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            if (code == Const.ERROR_CODE_INVALID_TOKEN || code == Const.ERROR_PROVIDER_DETAIL_NOT_FOUND) {
                PreferenceHelper.getInstance(context).logout();// clear
                // session token
                Intent sigInIntent = new Intent(context, MainActivity.class);
                sigInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                sigInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sigInIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                sigInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(sigInIntent);
            }
        } catch (Resources.NotFoundException e) {
            msg = errorCode;
            showToast(msg, context);
        }

    }

    public static void showMessageToast(String code, Context context) {
        String msg;
        String messageCode = Const.MESSAGE_CODE_PREFIX + code;
        try {
            msg = context.getResources().getString(context.getResources().getIdentifier(messageCode, Const.STRING, context.getPackageName()));
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Resources.NotFoundException e) {
            msg = context.getResources().getString(R.string.text_error);
        }

    }

    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static boolean hasAnyPrefix(String number, String... prefixes) {
        if (number == null) {
            return false;
        }
        for (String prefix : prefixes) {
            if (number.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    public static Bitmap vectorToBitmap(Context ctx, @DrawableRes int resVector) {
        Drawable drawable = AppCompatResources.getDrawable(ctx, resVector);
        Bitmap b = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        drawable.setBounds(0, 0, c.getWidth(), c.getHeight());
        drawable.draw(c);
        return b;
    }

    /**
     * This method is used for convert bitmap to base64 format.
     */
    public static String convertImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.URL_SAFE);

        return encodedImage;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable != null) {
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if (bitmapDrawable.getBitmap() != null) {
                    return bitmapDrawable.getBitmap();
                }
            }

            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap
                // will be created of 1x1 pixel
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    public static void hideCustomProgressDialog() {
        try {
            if (dialog != null && ivProgressBar != null) {

                dialog.dismiss();


            }
        } catch (Exception e) {
            AppLog.handleException(TAG, e);
        } finally {
            dialog = null;
            ivProgressBar = null;
        }
    }


    public static void showCustomProgressDialog(AppCompatActivity activity, String title, boolean isCancel, OnProgressCancelListener onProgressCancelListener) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        if (!activity.isFinishing()) {
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.circuler_progerss_bar_two);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ivProgressBar = dialog.findViewById(R.id.ivProgressBarTwo);
            ivProgressBar.addListener(new CircularProgressViewAdapter() {
                @Override
                public void onProgressUpdate(float currentProgress) {
                    Log.d("CPV", "onProgressUpdate: " + currentProgress);
                }

                @Override
                public void onProgressUpdateEnd(float currentProgress) {
                    Log.d("CPV", "onProgressUpdateEnd: " + currentProgress);
                }

                @Override
                public void onAnimationReset() {
                    Log.d("CPV", "onAnimationReset");
                }

                @Override
                public void onModeChanged(boolean isIndeterminate) {
                    Log.d("CPV", "onModeChanged: " + (isIndeterminate ? "indeterminate" : "determinate"));
                }
            });
            ivProgressBar.startAnimation();
            dialog.setCancelable(isCancel);
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setAttributes(params);
            dialog.getWindow().setDimAmount(0);
            dialog.show();
        }


    }

    public static String showUnit(Context context, int code) {
        String codeString = Const.UNIT_PREFIX + code;
        return context.getResources().getString(context.getResources().getIdentifier(codeString, Const.STRING, context.getPackageName()));

    }

    public static String validSuffix(double value, String unit) {

        if (value <= 1.0) {
            return Const.SLASH + unit;
        } else {
            return Const.SLASH + value + unit;

        }
    }

    public static String validBasePriceSuffix(double value, String unit) {
        if (value < 1) {
            return "";
        } else if (value == 1.0) {
            return Const.SLASH + unit;
        } else {
            return Const.SLASH + value + unit + "s";
        }
    }

    public static String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return n + "th";
        }
        switch (n % 10) {
            case 1:
                return n + "st";
            case 2:
                return n + "nd";
            case 3:
                return n + "rd";
            default:
                return n + "th";
        }
    }

    public static boolean isNougat() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.M;
    }

    public static String fixValueAfterPoint(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(value);
    }

    public static String trimString(String address) {
        boolean isInteger;
        String original = "";
        if (!TextUtils.isEmpty(address)) {
            String[] strings = address.split(",");
            String s = address.substring(0, 1);
            try {
                Integer.valueOf(s);
                isInteger = true;


            } catch (NumberFormatException e) {
                isInteger = false;
            }
            int stringLength = strings.length;
            if (isInteger) {
                switch (stringLength) {
                    case 1:
                    case 2:
                        original = address;
                        break;
                    default:
                        original += strings[0] + "," + strings[1];
                        break;
                }


            } else {
                switch (stringLength) {
                    case 1:
                        original = address;
                        break;
                    case 2:
                        original = strings[0];
                        break;
                    default:
                        original = strings[0] + "," + strings[1];
                        break;
                }
            }

        }


        return original.trim();
    }

    public static String UrlEncoding(String string) {
        try {
            return URLEncoder.encode(string, Const.UTF_8);
        } catch (UnsupportedEncodingException e) {
            AppLog.handleException("URI ENCODE", e);
        }
        return null;
    }

    public static boolean isScreenOn(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(POWER_SERVICE);
        return powerManager.isInteractive();
    }

    public static String secondsToHoursMinutesSeconds(long seconds) {

        return (seconds / 3600) + " hr" + " " + (seconds % 3600) / 60 + " min";
    }

    public static void hideLocationUpdateDialog() {
        try {
            if (dialogProgress != null && dialogProgress.isShowing()) {

                dialogProgress.dismiss();


            }
        } catch (Exception e) {
            AppLog.handleException(TAG, e);
        }
    }

    public static void showLocationUpdateDialog(Context context) {
        if (dialogProgress != null && dialogProgress.isShowing()) {
            return;
        }

        CustomCircularProgressView ivProgressBar;
        MyFontTextView tvTitleProgress;
        dialogProgress = new Dialog(context);
        dialogProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogProgress.setContentView(R.layout.circuler_progerss_bar);
        tvTitleProgress = dialogProgress.findViewById(R.id.tvTitleProgress);
        ivProgressBar = dialogProgress.findViewById(R.id.ivProgressBar);
        ivProgressBar.addListener(new CircularProgressViewAdapter() {
            @Override
            public void onProgressUpdate(float currentProgress) {
                Log.d("CPV", "onProgressUpdate: " + currentProgress);
            }

            @Override
            public void onProgressUpdateEnd(float currentProgress) {
                Log.d("CPV", "onProgressUpdateEnd: " + currentProgress);
            }

            @Override
            public void onAnimationReset() {
                Log.d("CPV", "onAnimationReset");
            }

            @Override
            public void onModeChanged(boolean isIndeterminate) {
                Log.d("CPV", "onModeChanged: " + (isIndeterminate ? "indeterminate" : "determinate"));
            }
        });
        tvTitleProgress.setText(context.getResources().getString(R.string.msg_for_end_trip_location_update));
        ivProgressBar.startAnimation();
        dialogProgress.setCancelable(false);
        WindowManager.LayoutParams params = dialogProgress.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialogProgress.getWindow().setAttributes(params);
        dialogProgress.getWindow().setDimAmount(0);
        dialogProgress.show();
    }

    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    public static String getDeviceCountryCode(Context context) {
        String countryCode;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null && !TextUtils.isEmpty(tm.getNetworkCountryIso())) {
            countryCode = tm.getNetworkCountryIso();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            countryCode = context.getResources().getConfiguration().getLocales().get(0).getCountry();
        } else {
            countryCode = context.getResources().getConfiguration().locale.getCountry();
        }

        return (TextUtils.isEmpty(countryCode) ? "us" : countryCode).toUpperCase();
    }

    public static String getDeviceCountryPhoneCode(String countryCode) {
        return "+" + PhoneNumberUtil.getInstance().getCountryCodeForRegion(countryCode);
    }
    public static boolean isValidPhoneNumber(String mobileNumber, String countryCodeISO2) {
        if (Patterns.PHONE.matcher(mobileNumber.trim()).matches()) {
            PhoneNumberUtil phoneNumber = PhoneNumberUtil.getInstance();
            try {
                Phonenumber.PhoneNumber parseNumber = phoneNumber.parse(mobileNumber, countryCodeISO2);
                return phoneNumber.isValidNumber(parseNumber);
            } catch (NumberParseException e) {
                return false;
            }

        } else {
            return false;
        }
    }

    public static boolean isValidPhoneNumber(String mobileNumber, int minimumPhoneNumberLength, int maximumPhoneNumberLength) {
        return mobileNumber.trim().length() >= minimumPhoneNumberLength && mobileNumber.trim().length() <= maximumPhoneNumberLength;
    }
}

