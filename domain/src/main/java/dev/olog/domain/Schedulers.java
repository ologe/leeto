package dev.olog.domain;

import io.reactivex.Scheduler;

public interface Schedulers {

    Scheduler ui();
    Scheduler worker();

}
