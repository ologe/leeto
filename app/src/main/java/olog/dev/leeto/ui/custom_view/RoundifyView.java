package olog.dev.leeto.ui.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import olog.dev.leeto.R;

import static android.content.res.ColorStateList.valueOf;

public interface RoundifyView {

    default void roundify(View view, Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundedView);
        try {
            float cornerRadius = a.getDimension(R.styleable.RoundedView_corner_radius, 0);

            Drawable background = new RoundRectDrawable(valueOf(Color.WHITE), cornerRadius);
            view.setBackground(background);

            view.setClipToOutline(true);

        } finally {
            a.recycle();
        }
    }

}
