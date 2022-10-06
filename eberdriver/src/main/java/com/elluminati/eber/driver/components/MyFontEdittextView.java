package com.elluminati.eber.driver.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.appcompat.content.res.AppCompatResources;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.utils.AppLog;
import com.google.android.material.textfield.TextInputEditText;


/**
 * @author Elluminati elluminati.in
 */
public class MyFontEdittextView extends TextInputEditText {

    public static final String TAG = "MyFontEdittextView";
    private Typeface typeface;

    public MyFontEdittextView(Context context) {
        super(context);
    }

    public MyFontEdittextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
        initAttrs(context, attrs);
    }

    public MyFontEdittextView(Context context, AttributeSet attrs, int defStyle) {
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