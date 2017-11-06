package olog.dev.leeto.ui._activity_main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import dev.olog.domain.interactor.GetAllJourneysUseCase;
import olog.dev.leeto.mapper.DisplayableJourneyMapper;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final GetAllJourneysUseCase getAllJourneysUseCase;
    private final DisplayableJourneyMapper journeyMapper;

    @Inject MainActivityViewModelFactory(
            GetAllJourneysUseCase getAllJourneysUseCase,
            DisplayableJourneyMapper journeyMapper) {

        this.getAllJourneysUseCase = getAllJourneysUseCase;
        this.journeyMapper = journeyMapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(
                getAllJourneysUseCase,
                journeyMapper
        );
    }
}
