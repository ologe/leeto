package olog.dev.leeto.utility.rx;

import io.reactivex.Scheduler;

public interface BaseSchedulerProvider {

    Scheduler mainThread();

    Scheduler computation();

    Scheduler io();

}
