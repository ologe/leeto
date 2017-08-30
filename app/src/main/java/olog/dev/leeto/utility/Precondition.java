package olog.dev.leeto.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Precondition {

    public static <T> T checkNotNull(T reference){
        if (reference == null){
            throw new NullPointerException();
        }
        return reference;
    }

    public static int checkNonNegative(int value){
        if(value < 0)
            throw new IllegalArgumentException("called with value " + value);
        return value;
    }

    public static long checkNonNegative(long value){
        if(value < 0)
            throw new IllegalArgumentException("called with value " + value);
        return value;
    }

    public static float checkNonNegative(float value){
        if(value < 0)
            throw new IllegalArgumentException("called with value " + value);
        return value;
    }

}
