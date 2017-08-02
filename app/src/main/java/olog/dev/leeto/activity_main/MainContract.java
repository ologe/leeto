package olog.dev.leeto.activity_main;

import android.support.design.widget.FloatingActionButton;

import java.util.List;

import olog.dev.leeto.base.BasePresenter;
import olog.dev.leeto.custom_view.ParallaxRecyclerView;
import olog.dev.leeto.model.pojo.Journey;

public interface MainContract {

    interface View {
        void updateJourneysList(List<Journey> data);
    }

    interface Presenter extends BasePresenter {
        void onFabClick(FloatingActionButton view, ParallaxRecyclerView recyclerView);
    }

}
