package olog.dev.leeto.ui.activity_detail;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import olog.dev.leeto.data.model.Journey;
import olog.dev.leeto.data.model.Location;
import olog.dev.leeto.data.model.Stop;

public class MapController implements LifecycleObserver, OnMapReadyCallback {

    private static final int ZOOM = 13;

    private Lifecycle lifecycle;
    private MapView mapView;
    private Journey journey;

    private List<Marker> markerList = new ArrayList<>();

    public MapController(Journey journey, Bundle savedInstanceState, Lifecycle lifecycle, MapView mapView) {
        this.lifecycle = lifecycle;
        this.mapView = mapView;
        lifecycle.addObserver(this);

        this.journey = journey;

        this.mapView.onCreate(savedInstanceState);
        this.mapView.getMapAsync(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(){
        mapView.onStart();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        mapView.onResume();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        mapView.onPause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(){
        mapView.onStop();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        lifecycle.removeObserver(this);
        mapView.onDestroy();
        mapView = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //        Calendar calendar = Calendar.getInstance();
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        boolean isNight = hour > 18 || hour < 6;
//        if(isNight){
//            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
//                    binding.getRoot().getContext(), R.raw.night_map));
//        }

        Single.create(e -> {

            List<Stop> stopList = journey.getStopsList();

            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            List<MarkerOptions> markerList = new ArrayList<>();

            for (Stop stop: stopList){
                Location location = stop.getLocation();
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                builder.include(latLng);

                MarkerOptions marker = new MarkerOptions()
                        .position(latLng)
                        .title(stop.getLocation().getAddress());

                markerList.add(marker);
            }


            e.onSuccess(markerList);

        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(obj -> {
                    List<MarkerOptions> markerOptionList = (List<MarkerOptions>) obj;

                    for(MarkerOptions markerOption: markerOptionList){
                        Marker marker = googleMap.addMarker(markerOption);
                        markerList.add(marker);
                    }

                    markerList.get(0).showInfoWindow();

                    Location location = journey.getStopsList().get(0).getLocation();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), ZOOM));

                }, Throwable::printStackTrace);

    }

    public void moveTo(Location location, int position){
        mapView.getMapAsync(googleMap -> {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            markerList.get(position).showInfoWindow();

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM));
        });
    }

    public void addMarker(Stop stop){
        mapView.getMapAsync(googleMap -> {

            Location location = stop.getLocation();

            Marker marker = googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
                            .title(location.getAddress()));
            markerList.add(marker);
        });
    }
}
