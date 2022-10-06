package com.elluminati.eber.driver.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.utils.AppLog;


/**
 * @author Elluminati elluminati.in
 */
public class MyFontTextView extends AppCompatTextView {
    public static final String TAG = "MyFontTextView";
    private Typeface typeface;

    public MyFontTextView(Context context) {
        super(context);
    }

    public MyFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
        initAttrs(context, attrs);

    }

    public MyFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
        initAttrs(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.app);
        String customFont = a.getString(R.styleable.app_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    private boolean setCustomFont(Context ctx, String asset) {
        try {
            if (typeface == null) {
                typeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto_Regular_0.ttf");
            }

        } catch (Exception e) {
            AppLog.handleException(TAG, e);
            return false;
        }

        setTypeface(typeface);
        return true;
    }

    private void checkFontSize(Context context) {
        float scale = 0;
        try {
            scale = Settings.System.getFloat(context.getContentResolver(), Settings.System.FONT_SCALE);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(getClass().getSimpleName(), "Error: ", e);
        }
        if (scale > 1.0) {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 8.96f);
        }
    }

    void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView);

            Drawable drawableLeft = null;
            Drawable drawableRight = null;
            Drawable drawableBottom = null;
            Drawable drawableTop = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawableLeft = attributeArray.getDrawable(R.styleable.CustomView_drawableLeftCompat);
                drawableRight = attributeArray.getDrawable(R.styleable.CustomView_drawableRightCompat);
                drawableBottom = attributeArray.getDrawable(R.styleable.CustomView_drawableBottomCompat);
                drawableTop = attributeArray.getDrawable(R.styleable.CustomView_drawableTopCompat);
            } else {
                final int drawableLeftId = attributeArray.getResourceId(R.styleable.CustomView_drawableLeftCompat, -1);
                final int drawableRightId = attributeArray.getResourceId(R.styleable.CustomView_drawableRightCompat, -1);
                final int drawableBottomId = attributeArray.getResourceId(R.styleable.CustomView_drawableBottomCompat, -1);
                final int drawableTopId = attributeArray.getResourceId(R.styleable.CustomView_drawableTopCompat, -1);

                if (drawableLeftId != -1) drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId);
                if (drawableRightId != -1) drawableRight = AppCompatResources.getDrawable(context, drawableRightId);
                if (drawableBottomId != -1) drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId);
                if (drawableTopId != -1) drawableTop = AppCompatResources.getDrawable(context, drawableTopId);
            }
            setCompoundDrawablesRelativeWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
            attributeArray.recycle();
        }
    }

}