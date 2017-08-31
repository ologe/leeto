package olog.dev.leeto.ui._activity_main;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;
import java.util.Map;

import olog.dev.leeto.base.BaseView;
import olog.dev.leeto.data.model.Journey;

public interface MainContract {

    interface View extends BaseView {
        void showJourneysList(@NonNull List<Journey> data);
        void onItemClick(@NonNull Journey journey, int position, android.view.View scrim, android.view.View journeyName);
    }

    interface Presenter {
        void toAddJourney(@NonNull FloatingActionButton fab);
        void toDetailActivity(@NonNull Map<String, android.view.View> views, long journeyId,
                              int currentPosition, @NonNull LinearLayoutManager layoutManager);
    }

}
