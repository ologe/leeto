package dev.olog.domain;

import io.reactivex.Completable;

public abstract class CompletableUseCase<Param> {

    private final Schedulers schedulers;

    public CompletableUseCase(Schedulers schedulers) {
        this.schedulers = schedulers;
    }

    protected abstract Completable buildUseCaseObservable(Param param);

    public Completable execute(Param param){
        return Completable.defer(() -> this.buildUseCaseObservable(param)
                .subscribeOn(schedulers.worker())
                .observeOn(schedulers.ui())
        );
    }

}
