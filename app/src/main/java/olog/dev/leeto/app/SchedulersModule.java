package olog.dev.leeto.app;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dev.olog.domain.ComputationSchedulers;
import dev.olog.domain.IoSchedulers;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class SchedulersModule {

    @Provides
    @Singleton
    IoSchedulers provideIoSchedulers(){
        return new IoSchedulers() {
            @Override
            public Scheduler ui() {
                return AndroidSchedulers.mainThread();
            }

            @Override
            public Scheduler worker() {
                return Schedulers.io();
            }
        };
    }

    @Provides
    @Singleton
    ComputationSchedulers provideComputationSchedulers(){
        return new ComputationSchedulers() {
            @Override
            public Scheduler ui() {
                return AndroidSchedulers.mainThread();
            }

            @Override
            public Scheduler worker() {
                return Schedulers.computation();
            }
        };
    }

}
