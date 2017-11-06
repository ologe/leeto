package dev.olog.domain.interactor;

import java.util.List;

import javax.inject.Inject;

import dev.olog.domain.FlowableUseCase;
import dev.olog.domain.IoSchedulers;
import dev.olog.domain.model.Journey;
import dev.olog.domain.repository.JourneyDataStore;
import io.reactivex.Flowable;

public class GetAllJourneysUseCase extends FlowableUseCase<List<Journey>, Void> {

    private final JourneyDataStore dataStore;

    @Inject GetAllJourneysUseCase(IoSchedulers schedulers, JourneyDataStore dataStore) {
        super(schedulers);
        this.dataStore = dataStore;
    }

    @Override
    protected Flowable<List<Journey>> buildUseCaseObservable(Void unused) {
        return dataStore.observeAll()
                .distinctUntilChanged();
    }
}
