package olog.dev.leeto.app;

import com.google.android.gms.maps.MapsInitializer;
import com.squareup.leakcanary.LeakCanary;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import olog.dev.leeto.utility.AppConstants;

public class App extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }


        initializeMaps();

        AppConstants.init(this);
    }

    private void initializeMaps(){
        Single.fromCallable(() -> MapsInitializer.initialize(this))
                .subscribeOn(Schedulers.io())
                .subscribe(integer -> {}, throwable -> {});
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

}
