package olog.dev.leeto.dagger.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.dagger.annotation.ActivityContext;
import olog.dev.leeto.dagger.annotation.PerActivity;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyActivity;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyContract;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyPresenter;

@Module
public class AddJourneyModule {

    private AddJourneyActivity activity;

    public AddJourneyModule(AddJourneyActivity activity) {
        this.activity = activity;
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

}
