package olog.dev.leeto.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.App;
import olog.dev.leeto.dagger.annotation.ApplicationContext;
import olog.dev.leeto.dagger.annotation.PerApplication;

@Module
public class AppContextModule {

    private App app;

    public AppContextModule(App app) {
        this.app = app;
    }

    @Provides
    @ApplicationContext
    @PerApplication
    Context provideContext(){
        return app;
    }

}
