package com.tech.rotobash.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 @Module class/module		:	TextFontsRegular
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 5 , 2018
 @Purpose				    :	It supports font regular for textview
 */
public class TextFontsRegular extends android.support.v7.widget.AppCompatTextView{
    public TextFontsRegular(Context context) {
        super(context);
        setFont();
    }
    public TextFontsRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public TextFontsRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "regular.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}
