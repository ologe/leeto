package olog.dev.leeto.ui._activity_main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import dev.olog.domain.interactor.GetAllJourneysUseCase;
import olog.dev.leeto.mapper.DisplayableJourneyMapper;
import olog.dev.leeto.model.DisplayableItem;
import olog.dev.leeto.model.DisplayableJourney;

public class MainActivityViewModel extends ViewModel {

    private LiveData<List<DisplayableItem<DisplayableJourney>>> journeyListLiveData;

    private final GetAllJourneysUseCase getAllJourneysUseCase;
    private final DisplayableJourneyMapper journeyMapper;

    public MainActivityViewModel(
            GetAllJourneysUseCase getAllJourneysUseCase,
            DisplayableJourneyMapper journeyMapper) {

        this.getAllJourneysUseCase = getAllJourneysUseCase;
        this.journeyMapper = journeyMapper;
    }

    public LiveData<List<DisplayableItem<DisplayableJourney>>> observeJourneys(){
        if (journeyListLiveData == null){
            journeyListLiveData = LiveDataReactiveStreams.fromPublisher(
                    getAllJourneysUseCase.execute(null)
                            .flatMapSingle(journeyMapper)
            );
        }
        return journeyListLiveData;
    }

    public void share(Context context, String message){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(shareIntent, "Share journey to.."));
    }

}
