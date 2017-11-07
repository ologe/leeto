package olog.dev.leeto.app;

import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import olog.dev.leeto.utility.AppConstants;

public class App extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            LeakCanary.install(this);
//        }


        AppConstants.init(this);
        RxPaparazzo.register(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

}
