package olog.dev.leeto.ui._activity_main;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;

import java.util.List;

import olog.dev.leeto.base.BaseView;
import olog.dev.leeto.data.model.Journey;
import olog.dev.leeto.ui._activity_main.view.ParallaxRecyclerView;

public interface MainContract {

    interface View extends BaseView {
        void showJourneysList(@NonNull List<Journey> data);
        void showNoJourneysFragment();
        void showDeleteSnackBar(@StringRes int id, @NonNull Journey itemToDelete);
        void onItemClick(@NonNull Journey journey, int position, android.view.View scrim, android.view.View journeyName);
    }

    interface Presenter {
        void onFabClick(@NonNull FloatingActionButton view,@NonNull  ParallaxRecyclerView recyclerView);
        void deleteJourney(@NonNull Journey journey);
    }

}
