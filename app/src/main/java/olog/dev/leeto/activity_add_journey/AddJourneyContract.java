package olog.dev.leeto.activity_add_journey;

import android.app.DatePickerDialog;
import android.content.Context;

import olog.dev.leeto.model.pojo.Location;

public interface AddJourneyContract {

    interface View {
        void updateLocation(Location location);
    }

    interface Presenter {
        void showDatePicker(DatePickerDialog dialog);
        void onLocationRequestClick(Context context, Location location);
    }

}
