package olog.dev.leeto.app;

import android.content.Context;
import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.utility.dagger.annotations.context.ApplicationContext;
import olog.dev.leeto.utility.dagger.annotations.scope.PerApplication;

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @ApplicationContext
    @PerApplication
    Context provideContext(){
        return app;
    }

    @Provides
    @PerApplication
    Resources provideResources(){
        return app.getResources();
    }

}
