package olog.dev.leeto.ui._activity_main.di;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.ui._activity_main.MainActivity;
import olog.dev.leeto.ui._activity_main.MainContract;
import olog.dev.leeto.ui._activity_main.MainPresenter;
import olog.dev.leeto.utility.dagger.annotations.context.ActivityContext;
import olog.dev.leeto.utility.dagger.annotations.scope.PerActivity;

@Module
public class MainActivityModule {

    private MainActivity activity;

    public MainActivityModule(MainActivity mainActivity) {
        this.activity = mainActivity;
    }

    @Provides
    @PerActivity
    @ActivityContext
    Context provideContext(){
        return activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity provideActivity(){
        return activity;
    }

    @Provides
    @PerActivity
    MainContract.View provideView(){
        return activity;
    }

    @Provides
    @PerActivity
    MainContract.Presenter providePresenter(MainPresenter presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    LayoutInflater provideLayoutInflater(){
        return LayoutInflater.from(activity);
    }

    @Provides
    @PerActivity
    LinearLayoutManager provideLayoutManager(){
        return new LinearLayoutManager(activity);
    }

    @Provides
    @PerActivity
    Lifecycle provideLifecycle(){
        return activity.getLifecycle();
    }


}
