package olog.dev.leeto.ui.fragment_journeys_stops;

import android.arch.lifecycle.LiveDataReactiveStreams;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private View back;
    private RecyclerView list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journey_stops, container, false);
        journeyName = view.findViewById(R.id.journeyName);
        back = view.findViewById(R.id.back);
        list = view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new JourneyStopsAdapter(getLifecycle()));
    }

    @Override
    public void onResume() {
        super.onResume();
        back.setOnClickListener(view -> getActivity().finishAfterTransition());
    }

    @Override
    public void onPause() {
        super.onPause();
        back.setOnClickListener(null);
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
