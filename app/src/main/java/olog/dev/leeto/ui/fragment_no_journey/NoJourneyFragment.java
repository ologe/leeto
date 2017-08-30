package olog.dev.leeto.ui.fragment_no_journey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import olog.dev.leeto.R;

public class NoJourneyFragment extends Fragment {

    public static final String TAG = "NoJourneyFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_no_journey, container, false);
    }


}
