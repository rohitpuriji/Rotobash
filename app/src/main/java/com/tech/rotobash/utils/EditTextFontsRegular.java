package com.tech.rotobash.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 @Module class/module		:	EditTextFontsRegular
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 6 , 2018
 @Purpose				    :	It supports font regular for edittext view
 */
public class EditTextFontsRegular extends android.support.v7.widget.AppCompatEditText {
    public EditTextFontsRegular(Context context) {
        super(context);
        setFont();
    }
    public EditTextFontsRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public EditTextFontsRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "regular.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}
