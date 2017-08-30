package olog.dev.leeto.utility.reactive;

import io.reactivex.Scheduler;

public interface BaseSchedulersProvider {

    Scheduler mainThread();

    Scheduler computation();

    Scheduler io();

}
