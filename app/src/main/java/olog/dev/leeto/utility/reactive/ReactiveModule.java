package olog.dev.leeto.utility.reactive;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import olog.dev.leeto.utility.dagger.annotations.scope.PerApplication;

@Module
public class ReactiveModule {

    @Provides
    @PerApplication
    BaseSchedulersProvider provideSchedulerProvider(){
        return new SchedulerProvider();
    }


    @Provides
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }


}
