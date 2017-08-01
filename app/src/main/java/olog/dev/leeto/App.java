package olog.dev.leeto;

import android.app.Application;
import android.os.StrictMode;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class App extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        Completable.create(e -> {
            MapsInitializer.initialize(this);
            MapView mapView = new MapView(this);
            mapView.onCreate(null);
            mapView.onPause();
            mapView.onDestroy();

        }).subscribeOn(Schedulers.computation())
                .subscribe(() -> {}, throwable -> {});

    }
}
