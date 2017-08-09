package olog.dev.leeto.utility;

import android.os.Build;

public class BuildVersion {

    private BuildVersion() {
        throw new AssertionError("not instantiable");
    }

    public static boolean Marshmallow(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
