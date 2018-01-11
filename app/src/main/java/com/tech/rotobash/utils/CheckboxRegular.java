package com.tech.rotobash.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 @Module class/module		:	CheckboxRegular
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 5 , 2018
 @Purpose				    :	It supports font regular for checkbox view
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
