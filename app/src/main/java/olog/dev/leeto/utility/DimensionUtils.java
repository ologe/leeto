package olog.dev.leeto.utility;

import android.content.Context;
import android.support.annotation.NonNull;

public class DimensionUtils {

    private DimensionUtils() {
        //no instance
    }

    public static int dip(@NonNull Context context, int value){
        return (int) (value * context.getResources().getDisplayMetrics().density);
    }

}
