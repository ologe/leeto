package olog.dev.leeto.ui.fragment_stop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseFragment;
import olog.dev.leeto.data.model.Location;
import olog.dev.leeto.data.model.Stop;


public class StopFragment extends BaseFragment implements StopContract.View{

    private static final String TAG = StopFragment.class.getSimpleName();
    public static final String ARGUMENT_STOP_POSITION = TAG + "argument.stop_position";

    @BindView(R.id.description) TextView description;
    @BindView(R.id.locationName) TextView locationName;
    @BindView(R.id.locationDate) TextView locationDate;

    @Inject StopContract.Presenter presenter;

    public static StopFragment newInstance(int position){
        StopFragment fragment = new StopFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT_STOP_POSITION, position);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void setupUI(@NonNull Stop stop) {
        Location location = stop.getLocation();
        String formattedLocation = location.getFormattedLocation();

        locationDate.setText(stop.getFormattedDate());
        description.setText(location.getShortDescription());
        locationName.setText(formattedLocation);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stop;
    }
}
