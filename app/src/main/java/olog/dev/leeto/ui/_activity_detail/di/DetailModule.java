//package olog.dev.leeto.ui._activity_detail.di;
//
//import android.arch.lifecycle.Lifecycle;
//import android.content.Context;
//import android.support.v7.app.AppCompatActivity;
//
//import java.util.List;
//
//import dagger.Module;
//import dagger.Provides;
//import olog.dev.leeto.data.model.Journey;
//import olog.dev.leeto.data.model.Stop;
//import olog.dev.leeto.data.repository.IRepository;
//import olog.dev.leeto.ui._activity_detail.DetailActivity;
//import olog.dev.leeto.ui._activity_detail.DetailContract;
//import olog.dev.leeto.ui._activity_detail.DetailPresenter;
//import olog.dev.leeto.dagger.ActivityContext;
//import olog.dev.leeto.dagger.PerActivity;
//
//@Module
//public class DetailModule {
//
//    private DetailActivity activity;
//
//    public DetailModule(DetailActivity activity) {
//        this.activity = activity;
//    }
//
//    @Provides
//    @PerActivity
//    @ActivityContext
//    Context provideContext(){
//        return activity;
//    }
//
//    @Provides
//    @PerActivity
//    AppCompatActivity provideActivity(){
//        return activity;
//    }
//
//    @Provides
//    @PerActivity
//    DetailContract.View provideView(){
//        return activity;
//    }
//
//
//    @Provides
//    @PerActivity
//    DetailContract.Presenter providePresenter(DetailPresenter presenter){
//        return presenter;
//    }
//
//    @Provides
//    @PerActivity
//    Lifecycle provideLifecycle(){
//        return activity.getLifecycle();
//    }
//
//    @Provides
//    @PerActivity
//    long provideJourneyId(){
//        return activity.getIntent().getLongExtra(DetailActivity.BUNDLE_JOURNEY_ID, -1);
//    }
//
//    @Provides
//    @PerActivity
//    int providePosition(){
//        return activity.getIntent().getIntExtra(DetailActivity.BUNDLE_POSITION, -1);
//    }
//
////    @Provides
////    @PerActivity
////    Journey provideJourney(long journeyId, IRepository repository){
////        return repository.getJourney(journeyId);
////    }
//
////    @Provides
////    @PerActivity
////    List<Stop> provideStopList(Journey journey){
////        return journey.getStopList();
////    }
//
//}
