package olog.dev.leeto.ui._activity_detail;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

public class DetailMap extends MapView implements OnMapReadyCallback {

    private static final int ZOOM = 13;

//    private Journey journey;


    public DetailMap(Context context) {
        this(context, null);
    }

    public DetailMap(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DetailMap(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

//    public void init(Journey journey){
//        this.journey = journey;
//        super.onCreate(null);
//        getMapAsync(this);
//        subscriptions = new CompositeDisposable();
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

//        Disposable markerDisposable = Single.just(journey.getStopList())
//                .flatMapObservable(Observable::fromIterable)
//                .mapToDomain(Stop::getLocation)
//                .mapToDomain(location -> Pair.create(location, new LatLng(location.getLatitude(), location.getLongitude())))
//                .mapToDomain(pair -> new MarkerOptions().position(pair.second).title(pair.first.getAddress()))
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(googleMap::addMarker, Throwable::printStackTrace);
//
//        Disposable moveCameraDisposable = Single.just(journey.getStopList())
//                .mapToDomain(stopList -> stopList.get(0))
//                .mapToDomain(Stop::getLocation)
//                .mapToDomain(location -> new LatLng(location.getLatitude(), location.getLongitude()))
//                .mapToDomain(latLng -> CameraUpdateFactory.newLatLngZoom(latLng, ZOOM))
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(googleMap::moveCamera, Throwable::printStackTrace);
//
//        subscriptions.addAll(markerDisposable, moveCameraDisposable);
    }


//        builder.include(latLng);
//        markerList.add(marker);
//        markerList.get(0).showInfoWindow();

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
//    public void addMarker(Stop stop){
//        getMapAsync(googleMap -> {
//
//            Location location = stop.getLocation();
//
//            Marker marker = googleMap.addMarker(
//                    new MarkerOptions()
//                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
//                            .title(location.getAddress()));
//            markerList.add(marker);
//        });
//    }
}
