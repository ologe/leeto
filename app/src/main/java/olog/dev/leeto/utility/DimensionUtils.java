package olog.dev.leeto.utility;

import android.content.Context;
import android.support.annotation.NonNull;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DimensionUtils {


    public static int dip(@NonNull Context context, int value){
        return (int) (value * context.getResources().getDisplayMetrics().density);
    }

}
