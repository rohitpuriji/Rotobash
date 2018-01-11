package com.tech.rotobash.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by rohitpuri on 4/1/18.
 */

public class CheckboxRegular extends android.support.v7.widget.AppCompatCheckBox {
    public CheckboxRegular(Context context) {
        super(context);
        setFont();
    }
    public CheckboxRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public CheckboxRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "regular.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}
