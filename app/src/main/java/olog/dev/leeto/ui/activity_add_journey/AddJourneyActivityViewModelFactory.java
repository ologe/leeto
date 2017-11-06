package olog.dev.leeto.ui.activity_add_journey;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import dev.olog.domain.interactor.InsertJourneyUseCase;

public class AddJourneyActivityViewModelFactory implements ViewModelProvider.Factory {

    private final InsertJourneyUseCase insertJourneyUseCase;

    @Inject AddJourneyActivityViewModelFactory(
            InsertJourneyUseCase insertJourneyUseCase) {

        this.insertJourneyUseCase = insertJourneyUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddJourneyActivityViewModel(insertJourneyUseCase);
    }
}
