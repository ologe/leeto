package olog.dev.leeto.ui._activity_main.di;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.dagger.ActivityContext;
import olog.dev.leeto.ui._activity_main.MainActivity;
import olog.dev.leeto.ui._activity_main.MainActivityViewModel;
import olog.dev.leeto.ui._activity_main.MainActivityViewModelFactory;
import olog.dev.leeto.ui._activity_main.list.JourneyListAdapter;

@Module
public class MainActivityModule {

    private final MainActivity activity;

    public MainActivityModule(MainActivity mainActivity) {
        this.activity = mainActivity;
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
    MainActivityViewModel provideViewModel(MainActivityViewModelFactory factory){
        return ViewModelProviders.of(activity, factory).get(MainActivityViewModel.class);
    }

    @Provides
    JourneyListAdapter.OnJourneySelected OnJourneySelectedCallcack(){
        return activity;
    }

}
