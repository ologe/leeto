package dev.olog.domain;

import io.reactivex.Scheduler;

interface Schedulers {

    Scheduler ui();
    Scheduler worker();

}
