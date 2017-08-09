package olog.dev.leeto.ui.custom_view;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.gms.maps.MapView;

import olog.dev.leeto.R;

public class RoundedMapView extends MapView {


    public RoundedMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init(){
        if(!isInEditMode()){
            setClipToOutline(true);
            setBackgroundResource(R.drawable.rounded_shape);

        }
    }
}
