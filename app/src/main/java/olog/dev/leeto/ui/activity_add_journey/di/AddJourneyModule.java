package olog.dev.leeto.ui.activity_add_journey.di;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.dagger.ActivityContext;
import olog.dev.leeto.location.LocationModel;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyActivity;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyActivityViewModel;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyActivityViewModelFactory;

@Module
public class AddJourneyModule {

    private final List<String> mockLocations;
    private final AddJourneyActivity activity;

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
    @ActivityContext
    Context provideContext(){
        return activity;
    }

    @Provides
    Activity provideActivity(){
        return activity;
    }

    @Provides
    LocationModel provideMockData(){
        Random random = new Random();
        String location = mockLocations.get(random.nextInt(mockLocations.size()));
        return new LocationModel(
                location,
                location + " address",
                "" + (random.nextInt(90)),
                "" + (random.nextInt(180)));
    }

    @Provides
    AddJourneyActivityViewModel provideViewModel(AddJourneyActivityViewModelFactory factory){
        return ViewModelProviders.of(activity, factory).get(AddJourneyActivityViewModel.class);
    }

}
