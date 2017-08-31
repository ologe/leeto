package olog.dev.leeto.ui.activity_add_journey.di;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyActivity;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyContract;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyPresenter;
import olog.dev.leeto.utility.dagger.annotations.context.ActivityContext;
import olog.dev.leeto.utility.dagger.annotations.scope.PerActivity;

@Module
public class AddJourneyModule {

    private List<String> mockLocations;
    private AddJourneyActivity activity;

    public AddJourneyModule(AddJourneyActivity activity) {
        this.activity = activity;
        // TODO remove
        mockLocations = new ArrayList<>();
        mockLocations.add("Parma");
        mockLocations.add("New York");
        mockLocations.add("Paris");
        mockLocations.add("Moscow");
        mockLocations.add("Barcellona");
    }

    @Provides
    @PerActivity
    @ActivityContext
    Context provideContext(){
        return activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity(){
        return activity;
    }

    @Provides
    @PerActivity
    AddJourneyContract.View provideView(){
        return activity;
    }

    @Provides
    @PerActivity
    AddJourneyContract.Presenter providePresenter(AddJourneyPresenter presenter){
        return presenter;
    }

    @Provides
    String provideMockData(){
        return mockLocations.get(new Random().nextInt(mockLocations.size()));
    }

}
