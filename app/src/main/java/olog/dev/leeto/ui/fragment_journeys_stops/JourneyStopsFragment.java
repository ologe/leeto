package olog.dev.leeto.ui.fragment_journeys_stops;

import android.arch.lifecycle.LiveDataReactiveStreams;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import olog.dev.leeto.R;
import olog.dev.leeto.ui._activity_detail.DetailActivity;
import olog.dev.leeto.ui._activity_detail.DetailActivityViewModel;


public class JourneyStopsFragment extends DaggerFragment {

    @Inject
    DetailActivityViewModel activityViewModel;

    private TextView journeyName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journey_stops, container, false);
        journeyName = view.findViewById(R.id.journeyName);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LiveDataReactiveStreams.fromPublisher(activityViewModel.observeJourney()
                .delay(20, TimeUnit.MILLISECONDS))
                .observe(this, journey -> {
                    if (journey != null){
                        long journeyId = journey.getId();
                        journeyName.setTransitionName(DetailActivity.SHARED_JOURNEY_NAME + journeyId);
                        journeyName.setText(journey.getName());
                    }
                    getActivity().startPostponedEnterTransition();
                });
    }

}
