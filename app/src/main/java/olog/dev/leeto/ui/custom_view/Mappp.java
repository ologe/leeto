package olog.dev.leeto.ui.custom_view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.util.AttributeSet;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import olog.dev.leeto.data.model.Journey;
import olog.dev.leeto.data.model.Location;
import olog.dev.leeto.data.model.Stop;

public class Mappp extends RoundedMapView implements OnMapReadyCallback, LifecycleObserver {

    private static final int ZOOM = 13;

    private Journey journey;

    private final List<Marker> markerList;

    public Mappp(Context context) {
        this(context, null);
    }

    public Mappp(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Mappp(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        markerList = new ArrayList<>();
    }

    public void init(Journey journey){
        this.journey = journey;
        super.onCreate(null);
        getMapAsync(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start(){
        super.onStart();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume(){
        super.onResume();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pause(){
        super.onPause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop(){
        super.onStop();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy(){
        super.onDestroy();
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
//
//        Single.create(e -> {
//
//            List<Stop> stopList = journey.getStopsList();
//
//            LatLngBounds.Builder builder = new LatLngBounds.Builder();
//
//            List<MarkerOptions> markerList = new ArrayList<>();
//
//            for (Stop stop: stopList){
//                Location location = stop.getLocation();
//                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//                builder.include(latLng);
//
//                MarkerOptions marker = new MarkerOptions()
//                        .position(latLng)
//                        .title(stop.getLocation().getAddress());
//
//                markerList.add(marker);
//            }
//
//
//            e.onSuccess(markerList);
//
//        }).subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(obj -> {
//                    List<MarkerOptions> markerOptionList = (List<MarkerOptions>) obj;
//
//                    for(MarkerOptions markerOption: markerOptionList){
//                        Marker marker = googleMap.addMarker(markerOption);
//                        markerList.add(marker);
//                    }
//
//                    markerList.get(0).showInfoWindow();
//
//                    Location location = journey.getStopsList().get(0).getLocation();
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), ZOOM));
//
//                }, Throwable::printStackTrace);
    }

//    public void moveTo(Location location, int position){
//        getMapAsync(googleMap -> {
//            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//            markerList.get(position).showInfoWindow();
//
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM));
//        });
//    }
//
    public void addMarker(Stop stop){
        getMapAsync(googleMap -> {

            Location location = stop.getLocation();

            Marker marker = googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
                            .title(location.getAddress()));
            markerList.add(marker);
        });
    }
}
