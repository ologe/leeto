package olog.dev.leeto.app;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import olog.dev.leeto.utility.AppConstants;

public class App extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        initializeMaps();

        AppConstants.init(this);
    }

    private void initializeMaps(){
        Completable.create(e -> {
            MapsInitializer.initialize(this);
            MapView mapView = new MapView(this);
            mapView.onCreate(null);
            mapView.onPause();
            mapView.onDestroy();

        }).subscribeOn(Schedulers.computation())
                .subscribe(() -> {}, throwable -> {});
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

}
