package olog.dev.leeto.utility;

import android.content.Context;
import android.support.annotation.NonNull;

public class DimensionUtils {

    private DimensionUtils(){
        // helper class
    }

    public static int dip(@NonNull Context context, int value){
        return (int) (value * context.getResources().getDisplayMetrics().density);
    }

}
