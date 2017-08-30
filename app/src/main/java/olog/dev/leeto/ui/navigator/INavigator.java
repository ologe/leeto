package olog.dev.leeto.ui.navigator;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.Map;

public interface INavigator {

    void toDetailActivity(@NonNull Map<String, View> views,
                          long journeyId,
                          int currentPosition,
                          @NonNull LinearLayoutManager layoutManager);

    void toNoJourneyFragment();

}
