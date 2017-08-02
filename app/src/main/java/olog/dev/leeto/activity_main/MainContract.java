package olog.dev.leeto.activity_main;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;

import java.util.List;

import olog.dev.leeto.activity_main.view.ParallaxRecyclerView;
import olog.dev.leeto.base.BasePresenter;
import olog.dev.leeto.model.pojo.Journey;

public interface MainContract {

    interface View {
        void showJourneysList(@NonNull List<Journey> data);
        void showNoJourneysFragment();
        void showDeleteSnackBar(@StringRes int id, @NonNull Journey itemToDelete);
        void onItemClick(@NonNull Journey journey, int position, android.view.View scrim, android.view.View journeyName);
    }

    interface Presenter extends BasePresenter {
        void onFabClick(@NonNull FloatingActionButton view,@NonNull  ParallaxRecyclerView recyclerView);
        void deleteJourney(@NonNull Journey journey);
    }

}
