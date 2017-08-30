package olog.dev.leeto.app;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import olog.dev.leeto.BuildConfig;
import olog.dev.leeto.utility.AppConstants;
import olog.dev.leeto.utility.timber.DebugTree;
import olog.dev.leeto.utility.timber.ReleaseTree;
import timber.log.Timber;

public class App extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

//        initializeMaps();
        initLogger();

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

    private void initLogger(){
        if(BuildConfig.DEBUG){
            Timber.plant(new DebugTree());
        } else {
            // release mode
            // can start crashlytics for example
            Timber.plant(new ReleaseTree());
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

}
