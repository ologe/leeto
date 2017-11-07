package olog.dev.leeto.ui._activity_detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import dev.olog.domain.interactor.GetJourneyByParamUseCase;

public class DetailActivityViewModelFactory implements ViewModelProvider.Factory {

    private final long journeyId;
    private final GetJourneyByParamUseCase getJourneyByParamUseCase;

    @Inject DetailActivityViewModelFactory(
            long journeyId,
            GetJourneyByParamUseCase getJourneyByParamUseCase) {

        this.journeyId = journeyId;
        this.getJourneyByParamUseCase = getJourneyByParamUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailActivityViewModel(journeyId, getJourneyByParamUseCase);
    }
}
