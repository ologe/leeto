package olog.dev.leeto.utility;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import lombok.experimental.UtilityClass;
import olog.dev.leeto.R;

@UtilityClass
public class AppConstants {

    public static String NO_DESCRIPTION = "";
    private static List<String> ratioList;

    private static final String RATIO_16_9 = "1.77";
    private static final String RATIO_2_1 = "2";
    private static final String RATIO_SQUARE = "1";
    private static final String RATIO_ONE_HALF = "1.5";

    public static void init(@NonNull Context context){
        NO_DESCRIPTION = context.getString(R.string.no_description);
        ratioList = new ArrayList<>();
        ratioList.add(RATIO_16_9);
        ratioList.add(RATIO_2_1);
        ratioList.add(RATIO_SQUARE);
        ratioList.add(RATIO_ONE_HALF);
    }

    public String getRatio(int position){
        return ratioList.get(position % ratioList.size());
    }

}
