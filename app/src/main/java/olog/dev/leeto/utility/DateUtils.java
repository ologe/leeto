package olog.dev.leeto.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String toString(Date date){
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date);
    }

}
