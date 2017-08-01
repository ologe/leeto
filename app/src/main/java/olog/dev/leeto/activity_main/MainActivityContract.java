package olog.dev.leeto.activity_main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import olog.dev.leeto.custom_view.ParallaxRecyclerView;
import olog.dev.leeto.model.pojo.Journey;

public interface MainActivityContract {

    interface View {
        void updateJourneysList(List<Journey> data);
    }

    interface Presenter {
        void onFabClick(android.view.View view, ParallaxRecyclerView recyclerView);
        void loadJourneysList(Context context);
    }

}
