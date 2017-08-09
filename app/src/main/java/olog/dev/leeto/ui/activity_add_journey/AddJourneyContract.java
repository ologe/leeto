package olog.dev.leeto.ui.activity_add_journey;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import olog.dev.leeto.base.BasePresenter;
import olog.dev.leeto.model.pojo.Journey;
import olog.dev.leeto.model.pojo.Location;

public interface AddJourneyContract {

    interface View {
        void updateLocation(@Nullable Location location);
    }

    interface Presenter extends BasePresenter {
        void onLocationRequestClick();
        void addJourneyToRepository(@NonNull Journey journey);
    }

}
