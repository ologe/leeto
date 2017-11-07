package olog.dev.leeto.ui.fragment_journeys_stops;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.android.support.DaggerFragment;
import olog.dev.leeto.R;


public class JourneyStopsFragment extends DaggerFragment {

    private static final String TAG = JourneyStopsFragment.class.getSimpleName();
    public static final String ARGUMENT_STOP_POSITION = TAG + "argument.stop_position";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stop, container, false);
    }

    //    @Override
//    public void setupUI(@NonNull Stop stop) {
//        Location location = stop.getLocation();
//        String formattedLocation = location.getFormattedLocation();
//
//        locationDate.setText(stop.getFormattedDate());
//        description.setText(location.getDescription());
//        locationName.setText(formattedLocation);
//    }

}
