package com.elluminati.eber.driver.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;

import com.elluminati.eber.driver.R;

import java.util.Locale;

public class LanguageHelper extends ContextWrapper {

    public LanguageHelper(Context base) {
        super(base);
    }

    public static ContextWrapper wrapper(Context context, String language) {
        Resources res = context.getResources();
        Configuration config = res.getConfiguration();
        Locale sysLocale = config.locale;
        if (!sysLocale.getLanguage().equals(language)) {
            if (TextUtils.isEmpty(language)) {
                language = "en";
                TypedArray typedArray = context.getResources().obtainTypedArray(R.array.language_code);
                for (int i = 0; i < typedArray.length(); i++) {
                    if (TextUtils.equals(typedArray.getString(i), sysLocale.getLanguage())) {
                        language = sysLocale.getLanguage();
                        PreferenceHelper.getInstance(context).putLanguageCode(language);
                    }
                }
                typedArray.recycle();
            }
        }

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        config.locale = locale;
        config.setLayoutDirection(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());

        return new LanguageHelper(context);
    }
}