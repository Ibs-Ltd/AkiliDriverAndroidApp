package com.elluminati.eber.driver.components;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.elluminati.eber.driver.R;

/**
 * Created by Ravi Bhalodi on 04,January,2021 in Elluminati
 */
public abstract class LocationProminentDisclosureDialog extends Dialog {

    public LocationProminentDisclosureDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.location_prominet_discloser_dialog);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        findViewById(R.id.btnNext).setOnClickListener(view -> next());
        TextView message = findViewById(R.id.tvMessage);
        message.setText(context.getResources().getString(R.string.msg_location_prominent_disclosure, context.getResources().getString(R.string.app_name)));
        setCancelable(false);
    }


    public abstract void next();
}
