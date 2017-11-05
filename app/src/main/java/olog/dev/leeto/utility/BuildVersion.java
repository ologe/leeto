package olog.dev.leeto.utility;

import android.os.Build;

public class BuildVersion {

    private BuildVersion() {
        //no instance
    }

    public static boolean Marshmallow(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean Nougat(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

}
