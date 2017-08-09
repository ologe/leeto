package olog.dev.leeto.ui.activity_add_stop;

import android.app.DatePickerDialog;
import android.content.Context;

import olog.dev.leeto.model.pojo.Location;

public interface AddStopContract {

    interface View {
        void updateLocation(Location location);
    }

    interface Presenter {
        void showDatePicker(DatePickerDialog dialog);
        void onLocationRequestClick(Context context, Location location);
    }

}
