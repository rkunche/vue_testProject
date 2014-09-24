package com.test.vue.vuetest.presenters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomFontText extends TextView {

    public CustomFontText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomFontText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomFontText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Regular.ttf");
            setTypeface(tf);
        }
    }
}
