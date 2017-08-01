package olog.dev.leeto.dagger.module;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import olog.dev.leeto.dagger.annotation.PerApplication;
import olog.dev.leeto.utility.rx.BaseSchedulerProvider;
import olog.dev.leeto.utility.rx.SchedulerProvider;

@Module
public class ReactiveModule {

    @Provides
    @PerApplication
    BaseSchedulerProvider provideSchedulerProvider(){
        return new SchedulerProvider();
    }


    @Provides
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }


}
