package dev.olog.domain.interactor;

import javax.inject.Inject;

import dev.olog.domain.FlowableUseCase;
import dev.olog.domain.IoSchedulers;
import dev.olog.domain.model.Journey;
import dev.olog.domain.repository.JourneyDataStore;
import io.reactivex.Flowable;

public class GetJourneyByParamUseCase extends FlowableUseCase<Journey, Long> {

    private final JourneyDataStore dataStore;

    @Inject GetJourneyByParamUseCase(
            IoSchedulers schedulers,
            JourneyDataStore dataStore) {

        super(schedulers);
        this.dataStore = dataStore;
    }

    @Override
    protected Flowable<Journey> buildUseCaseObservable(Long journeyId) {
        return dataStore.observeByParam(journeyId);
    }
}
