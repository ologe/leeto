package olog.dev.leeto.ui.activity_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Locale;

import olog.dev.leeto.databinding.FragmentStopBinding;
import olog.dev.leeto.model.pojo.Stop;

public class StopFragment extends Fragment {

    private static final String TAG = StopFragment.class.getSimpleName();
    public static final String BUNDLE_STOP = TAG + "bundle.stop";

    private FragmentStopBinding binding;

    private Stop stop;

    public static StopFragment newInstance(Stop stop){
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_STOP, stop);

        StopFragment fragment = new StopFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStopBinding.inflate(inflater, container, false);

        stop = getArguments().getParcelable(BUNDLE_STOP);

        binding.locationDate.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(stop.getDate()));

        String location = stop.getLocation().getName() + " \u00B7 " + stop.getLocation().getAddress();

        binding.locationName.setText(location);

        binding.setLocation(stop.getLocation());

        return binding.getRoot();
    }


}
