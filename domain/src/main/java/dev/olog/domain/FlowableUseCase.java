package dev.olog.domain;

import io.reactivex.Flowable;

public abstract class FlowableUseCase<T, Param> {

    private final Schedulers schedulers;

    public FlowableUseCase(Schedulers schedulers) {
        this.schedulers = schedulers;
    }

    protected abstract Flowable<T> buildUseCaseObservable(Param param);

    public Flowable<T> execute(Param param){
        return Flowable.defer(() -> this.buildUseCaseObservable(param)
                .subscribeOn(schedulers.worker())
                .observeOn(schedulers.ui())
        );
    }

}
