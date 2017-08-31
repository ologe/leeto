package olog.dev.leeto.utility;

import android.os.Handler;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HandlerUtils {

    public static void removeCallback(Handler handler, Runnable runnable){
        if(runnable != null) handler.removeCallbacks(runnable);
    }

}
