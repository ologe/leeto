package olog.dev.leeto.utility;

import android.os.Handler;

public class HandlerUtils {

    private HandlerUtils() {
        //no instance
    }

    public static void removeCallback(Handler handler, Runnable runnable){
        if(runnable != null) handler.removeCallbacks(runnable);
    }

}
