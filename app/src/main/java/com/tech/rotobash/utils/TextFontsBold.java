package com.tech.rotobash.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 @Module class/module		:	TextFontsBold
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 5 , 2018
 @Purpose				    :	It supports font bold for textview
 */
public class TextFontsBold extends android.support.v7.widget.AppCompatTextView {
    public TextFontsBold(Context context) {
        super(context);
        setFont();
    }
    public TextFontsBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public TextFontsBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "bold.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}
