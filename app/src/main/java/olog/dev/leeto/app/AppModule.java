package olog.dev.leeto.app;

import android.content.Context;
import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;
import dev.olog.shared.ApplicationContext;

@Module
public class AppModule {

    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return app;
    }

    @Provides
    Resources provideResources(){
        return app.getResources();
    }

}
