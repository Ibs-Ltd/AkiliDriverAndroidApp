package com.elluminati.eber.driver.components;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elluminati.eber.driver.R;
import com.elluminati.eber.driver.adapter.LanguageAdaptor;
import com.elluminati.eber.driver.interfaces.ClickListener;
import com.elluminati.eber.driver.interfaces.RecyclerTouchListener;


/**
 * Created by elluminati on 04-08-2016.
 */
public abstract class CustomLanguageDialog extends Dialog {

    private final RecyclerView rcvCountryCode;
    private final TextView tvCountryDialogTitle;
    private final LanguageAdaptor languageAdaptor;
    private final Context context;
    private final TypedArray langCode;
    private final TypedArray langName;

    public CustomLanguageDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_language);
        langCode = context.getResources().obtainTypedArray(R.array.language_code);
        langName = context.getResources().obtainTypedArray(R.array.language_name);
        rcvCountryCode = findViewById(R.id.rcvCountryCode);
        tvCountryDialogTitle = findViewById(R.id.tvCountryDialogTitle);
        tvCountryDialogTitle.setText(context.getResources().getString(R.string.text_change_language));
        rcvCountryCode.setLayoutManager(new LinearLayoutManager(context));
        languageAdaptor = new LanguageAdaptor(context);
        rcvCountryCode.setAdapter(languageAdaptor);
        rcvCountryCode.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        this.context = context;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rcvCountryCode.addOnItemTouchListener(new RecyclerTouchListener(context, rcvCountryCode, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                onSelect(langName.getString(position), langCode.getString(position));

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public abstract void onSelect(String languageName, String languageCode);


}
