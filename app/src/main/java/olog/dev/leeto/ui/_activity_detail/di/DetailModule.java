package olog.dev.leeto.ui._activity_detail.di;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.dagger.ActivityContext;
import olog.dev.leeto.ui._activity_detail.DetailActivity;
import olog.dev.leeto.ui._activity_detail.DetailActivityViewModel;
import olog.dev.leeto.ui._activity_detail.DetailActivityViewModelFactory;

@Module
public class DetailModule {

    private DetailActivity activity;

    public DetailModule(DetailActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext(){
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity(){
        return activity;
    }

    @Provides
    Lifecycle provideLifecycle(){
        return activity.getLifecycle();
    }

    @Provides
    long provideJourneyId(){
        return activity.getIntent().getLongExtra(DetailActivity.BUNDLE_JOURNEY_ID, -1);
    }

    @Provides
    int providePosition(){
        return activity.getIntent().getIntExtra(DetailActivity.BUNDLE_POSITION, -1);
    }

    @Provides
    DetailActivityViewModel provideViewModel(DetailActivityViewModelFactory factory){
        return ViewModelProviders.of(activity, factory).get(DetailActivityViewModel.class);
    }

//    @Provides
//    @PerActivity
//    Journey provideJourney(long journeyId, IRepository repository){
//        return repository.getJourney(journeyId);
//    }

//    @Provides
//    @PerActivity
//    List<Stop> provideStopList(Journey journey){
//        return journey.getStopList();
//    }

}
