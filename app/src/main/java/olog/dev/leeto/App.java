package olog.dev.leeto;

import android.app.Application;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import olog.dev.leeto.dagger.component.AppComponent;
import olog.dev.leeto.dagger.component.DaggerAppComponent;
import olog.dev.leeto.dagger.module.AppContextModule;
import olog.dev.leeto.model.repository.IRepository;
import olog.dev.leeto.utility.timber.DebugTree;
import olog.dev.leeto.utility.timber.ReleaseTree;
import timber.log.Timber;

public class App extends Application {

    @Inject
    IRepository repository;

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appContextModule(new AppContextModule(this))
                .build();
        appComponent.inject(this);

        initializeMaps();
        initLogger();
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

    public AppComponent getAppComponent(){
        return appComponent;
    }

}
