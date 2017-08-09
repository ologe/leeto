package olog.dev.leeto.ui.fragment_no_journey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import olog.dev.leeto.R;

public class NoJourneyFragment extends Fragment {

    public static final String TAG = "NoJourneyFragment";

    public static void newInstance(AppCompatActivity activity){
        NoJourneyFragment fragment = new NoJourneyFragment();

        activity.getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.root, fragment, TAG)
                .commitNowAllowingStateLoss();
    }


    public NoJourneyFragment(){
        // required empty constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_no_journey, container, false);
    }


}
