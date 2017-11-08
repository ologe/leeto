package olog.dev.leeto.ui.fragment_journeys_media;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import olog.dev.leeto.R;
import olog.dev.leeto.ui._activity_detail.DetailActivityViewModel;

public class JourneyMediaFragment extends DaggerFragment {

    @Inject DetailActivityViewModel activityViewModel;

    private RecyclerView list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journey_media, container, false);
        list = view.findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(getContext(), 2));
        list.setAdapter(new JourneyMediaAdapter(getLifecycle()));
        return view;
    }

}
