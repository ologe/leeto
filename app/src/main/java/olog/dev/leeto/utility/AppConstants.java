package olog.dev.leeto.utility;

import android.content.Context;
import android.support.annotation.NonNull;

import lombok.experimental.UtilityClass;
import olog.dev.leeto.R;

@UtilityClass
public class AppConstants {

    public static String NO_DESCRIPTION = "";

    public static void init(@NonNull Context context){
        NO_DESCRIPTION = context.getString(R.string.no_description);
    }

}
