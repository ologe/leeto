package olog.dev.leeto.utility;

import android.os.Build;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BuildVersion {


    public static boolean Marshmallow(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
