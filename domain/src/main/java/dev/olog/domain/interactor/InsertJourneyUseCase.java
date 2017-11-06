package dev.olog.domain.interactor;

import javax.inject.Inject;

import dev.olog.domain.CompletableUseCase;
import dev.olog.domain.IoSchedulers;
import dev.olog.domain.model.Journey;
import dev.olog.domain.repository.JourneyDataStore;
import io.reactivex.Completable;

public class InsertJourneyUseCase extends CompletableUseCase<Journey> {

    private final JourneyDataStore dataStore;

    @Inject InsertJourneyUseCase(
            IoSchedulers schedulers,
            JourneyDataStore dataStore) {

        super(schedulers);
        this.dataStore = dataStore;
    }

    @Override
    protected Completable buildUseCaseObservable(Journey journey) {
        return dataStore.insert(journey);
    }
}
