package olog.dev.leeto.ui.custom_view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

public class RoundedConstraintLayout extends ConstraintLayout implements RoundifyView {

    public RoundedConstraintLayout(Context context) {
        this(context, null);
    }

    public RoundedConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        roundify(this, context, attrs);
    }


}
