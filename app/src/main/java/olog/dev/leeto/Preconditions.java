package olog.dev.leeto;


public class Preconditions {

    private Preconditions() {
        //no instance
    }

    public static void assertRange(double value, double lower, double upper){
        if (value < lower){
            throw new AssertionError(
                    value + " is below lower bound " + lower
            );
        }

        if (value > upper){
            throw new AssertionError(
                    value + " is above upper bound " + upper
            );
        }
    }

}
