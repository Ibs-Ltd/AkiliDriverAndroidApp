package com.elluminati.eber.driver.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * Created by elluminati on 20-May-17.
 */

public class Slider extends SeekBar {

    private Drawable thumb;
    private SlideView.OnSlideCompleteListener listener;
    private SlideView slideView;

    public Slider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (thumb.getBounds().contains((int) event.getX(), (int) event.getY())) {
                super.onTouchEvent(event);
            } else {
                return false;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getProgress() > 85) {
                if (listener != null) listener.onSlideComplete(slideView);
            } else {
                setProgress(0);
            }
        } else super.onTouchEvent(event);

        return true;
    }

    void setOnSlideCompleteListenerInternal(SlideView.OnSlideCompleteListener listener, SlideView slideView) {
        this.listener = listener;
        this.slideView = slideView;
    }

    @Override
    public Drawable getThumb() {
        return super.getThumb();
    }

    @Override
    public void setThumb(Drawable thumb) {
        this.thumb = thumb;
        super.setThumb(thumb);
    }

}