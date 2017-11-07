package olog.dev.leeto.ui.fragment_journeys_map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import olog.dev.leeto.R;
import olog.dev.leeto.ui._activity_detail.DetailActivityViewModel;

public class JourneyMapFragment extends DaggerFragment implements OnMapReadyCallback {

    @Inject DetailActivityViewModel activityViewModel;

    private MapView map;
    private View backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journey_map, container, false);
        map = view.findViewById(R.id.map);
        backButton = view.findViewById(R.id.back);
        map.onCreate(savedInstanceState);
        map.getMapAsync(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
        backButton.setOnClickListener(view -> activityViewModel.setCurrentViewPagerPage(1));
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
        backButton.setOnClickListener(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
