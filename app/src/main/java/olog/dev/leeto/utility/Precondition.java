package olog.dev.leeto.utility;

public class Precondition {

    private Precondition(){
        throw new AssertionError("not instantiable");
    }

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

    public static long checkNonNegativeOrValue(long value, long permittedValue){
        if(value == permittedValue) return value;

        if(value < 0) throw new IllegalArgumentException("called with value " + value);
        return value;
    }

    public static int checkNonNegativeOrValue(int value, int permittedValue){
        if(value == permittedValue) return value;

        if(value < 0) throw new IllegalArgumentException("called with value " + value);
        return value;
    }

    public static int checkInValues(int value, int... values){
        boolean inValues = false;
        for (int i : values) {
            if (value == i){
                inValues = true;
                break;
            }
        }

        if(!inValues) throw new IllegalArgumentException();

        return value;
    }

}
