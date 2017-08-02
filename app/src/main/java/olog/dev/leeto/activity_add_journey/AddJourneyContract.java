package olog.dev.leeto.activity_add_journey;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;

import olog.dev.leeto.base.BasePresenter;
import olog.dev.leeto.model.pojo.Journey;
import olog.dev.leeto.model.pojo.Location;

public interface AddJourneyContract {

    interface View {
        void updateLocation(Location location);
    }

    interface Presenter extends BasePresenter {
        void showDatePicker(DatePickerDialog dialog);
        void onLocationRequestClick(Context context);
        void addJourneyToRepository(@NonNull Journey journey);
    }

}
