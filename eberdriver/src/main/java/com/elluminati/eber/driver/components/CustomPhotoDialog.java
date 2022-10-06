package com.elluminati.eber.driver.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.elluminati.eber.driver.R;


/**
 * Created by elluminati on 08-08-2016.
 */
public abstract class CustomPhotoDialog extends Dialog implements View.OnClickListener {

    private final ImageView ivCamera;
    private final ImageView ivGallery;

    public CustomPhotoDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choose_picture);
        ivCamera = findViewById(R.id.ivCamera);
        ivGallery = findViewById(R.id.ivGallery);
        ivGallery.setOnClickListener(this);
        ivCamera.setOnClickListener(this);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setDimAmount(0);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCamera:
                clickedOnCamera();
                break;
            case R.id.ivGallery:
                clickedOnGallery();
                break;

            default:
                break;
        }
    }

    public abstract void clickedOnCamera();

    public abstract void clickedOnGallery();
}
