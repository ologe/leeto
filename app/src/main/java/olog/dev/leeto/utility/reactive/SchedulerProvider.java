package olog.dev.leeto.utility.reactive;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import olog.dev.leeto.utility.dagger.annotations.scope.PerApplication;

@PerApplication
public class SchedulerProvider implements BaseSchedulersProvider {

    @Inject
    SchedulerProvider() {}

    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

}
