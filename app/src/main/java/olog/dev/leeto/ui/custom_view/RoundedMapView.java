package olog.dev.leeto.ui.custom_view;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.gms.maps.MapView;

public class RoundedMapView extends MapView implements RoundifyView {

    public RoundedMapView(Context context) {
        this(context, null);
    }

    public RoundedMapView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundedMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        roundify(this, context, attributeSet);
    }

}
