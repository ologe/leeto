package olog.dev.leeto.ui._activity_main;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;
import java.util.Map;

import olog.dev.leeto.base.BaseView;
import olog.dev.leeto.data.model.Journey;
import olog.dev.leeto.ui._activity_main.list.ParallaxRecyclerView;

public interface MainContract {

    interface View extends BaseView {
        void showJourneysList(@NonNull List<Journey> data);
        void onItemClick(@NonNull Journey journey, int position, android.view.View scrim, android.view.View journeyName);

        void scrollToPosition(int position);
    }

    interface Presenter {
        void onFabClick(@NonNull FloatingActionButton view,@NonNull ParallaxRecyclerView recyclerView);
        void toDetailActivity(@NonNull Map<String, android.view.View> views, long journeyId,
                              int currentPosition, @NonNull LinearLayoutManager layoutManager);
    }

}
