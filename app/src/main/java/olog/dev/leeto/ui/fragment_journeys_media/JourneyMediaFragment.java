package olog.dev.leeto.ui.fragment_journeys_media;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import olog.dev.leeto.R;
import olog.dev.leeto.ui._activity_detail.DetailActivityViewModel;

public class JourneyMediaFragment extends DaggerFragment {

    @Inject DetailActivityViewModel activityViewModel;

    private View backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journey_media, container, false);
        backButton = view.findViewById(R.id.back);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        backButton.setOnClickListener(view -> activityViewModel.setCurrentViewPagerPage(1));
    }

    @Override
    public void onPause() {
        super.onPause();
        backButton.setOnClickListener(null);
    }

}
