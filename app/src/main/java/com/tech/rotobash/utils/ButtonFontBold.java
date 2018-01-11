package com.tech.rotobash.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by rohitpuri on 4/1/18.
 */

public class ButtonFontBold extends android.support.v7.widget.AppCompatButton {
    public ButtonFontBold(Context context) {
        super(context);
        setFont();
    }
    public ButtonFontBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public ButtonFontBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "bold.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}
