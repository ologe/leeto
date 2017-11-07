package olog.dev.leeto.ui.fragment_journeys_map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import dev.olog.domain.model.Journey;
import dev.olog.domain.model.Location;
import dev.olog.domain.model.Stop;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import olog.dev.leeto.R;
import olog.dev.leeto.ui._activity_detail.DetailActivityViewModel;
import olog.dev.leeto.utility.RxUtils;

public class JourneyMapFragment extends DaggerFragment implements OnMapReadyCallback {

    @Inject DetailActivityViewModel activityViewModel;

    private MapView map;
    private View backButton;

    private Disposable markerDisposable;
    private Disposable moveCameraDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journey_map, container, false);
        map = view.findViewById(R.id.map);
        backButton = view.findViewById(R.id.back);
        map.onCreate(savedInstanceState);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadMarkersOnMap();
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
    public void onStop() {
        super.onStop();
        RxUtils.unsubscribe(markerDisposable);
        RxUtils.unsubscribe(moveCameraDisposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    private void loadMarkersOnMap(){
        map.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear(); // todo vedere bene la documentazione

        RxUtils.unsubscribe(markerDisposable);
        RxUtils.unsubscribe(moveCameraDisposable);

        markerDisposable = activityViewModel.observeJourney()
                .map(Journey::getStopList)
                .flatMap(stops -> Flowable.fromIterable(stops)
                        .map(Stop::getLocation)
                        .map(JourneyMapFragment::newMarker)
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(googleMap::addMarker, Throwable::printStackTrace);

        moveCameraDisposable = activityViewModel.observeJourney()
                .map(Journey::getStopList)
                .map(stops -> stops.get(0))
                .map(Stop::getLocation)
                .map(location -> new LatLng(location.getLatitude(), location.getLongitude()))
                .map(CameraUpdateFactory::newLatLng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(googleMap::moveCamera, Throwable::printStackTrace);
    }

    public static MarkerOptions newMarker(Location location){
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        return new MarkerOptions()
                .position(latLng)
                .title(location.getAddress());
    }
}
