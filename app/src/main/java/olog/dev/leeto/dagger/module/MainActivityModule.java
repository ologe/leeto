package olog.dev.leeto.dagger.module;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.activity_main.JourneyAdapter;
import olog.dev.leeto.activity_main.MainActivity;
import olog.dev.leeto.activity_main.MainContract;
import olog.dev.leeto.activity_main.MainPresenter;
import olog.dev.leeto.dagger.annotation.ActivityContext;
import olog.dev.leeto.dagger.annotation.PerActivity;

@Module
public class MainActivityModule {

    private MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    @PerActivity
    @ActivityContext
    Context provideContext(){
        return mainActivity;
    }

    @Provides
    @PerActivity
    MainContract.View provideView(){
        return mainActivity;
    }

    @Provides
    @PerActivity
    MainContract.Presenter providePresenter(MainPresenter presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    LayoutInflater provideLayoutInflater(){
        return LayoutInflater.from(mainActivity);
    }

    @Provides
    @PerActivity
    JourneyAdapter provideAdapter(LayoutInflater inflater){
        return new JourneyAdapter(inflater);
    }

    @Provides
    @PerActivity
    LinearLayoutManager provideLayoutManager(){
        return new LinearLayoutManager(mainActivity);
    }

}
