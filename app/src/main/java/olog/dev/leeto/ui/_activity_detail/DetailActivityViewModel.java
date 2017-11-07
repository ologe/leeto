package olog.dev.leeto.ui._activity_detail;

import android.arch.lifecycle.ViewModel;

import dev.olog.domain.interactor.GetJourneyByParamUseCase;
import dev.olog.domain.model.Journey;
import io.reactivex.Flowable;

public class DetailActivityViewModel extends ViewModel {

    private final long journeyId;
    private final GetJourneyByParamUseCase getJourneyByParamUseCase;

    public DetailActivityViewModel(
            long journeyId,
            GetJourneyByParamUseCase getJourneyByParamUseCase) {

        this.journeyId = journeyId;
        this.getJourneyByParamUseCase = getJourneyByParamUseCase;
    }

    public Flowable<Journey> observeJourney(){
        return getJourneyByParamUseCase.execute(journeyId);
    }

}
