package olog.dev.leeto.app;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dev.olog.data.repository.RepositoryModule;
import dev.olog.data.repository.RepositoryModuleHelper;
import olog.dev.leeto.location.LocationModule;
import olog.dev.leeto.ui._activity_main.di.MainActivityInjector;
import olog.dev.leeto.ui.activity_add_journey.di.AddJourneyInjector;

@Component(modules = {
        AppModule.class,
        RepositoryModule.class,
        RepositoryModuleHelper.class,
        AppHelperModule.class,
        SchedulersModule.class,
        LocationModule.class,

        AndroidSupportInjectionModule.class,
        MainActivityInjector.class,
        AddJourneyInjector.class,
//        DetailActivityInjector.class
})
@Singleton
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App>{

        abstract Builder module(AppModule module);

        @Override
        public void seedInstance(App instance) {
            module(new AppModule(instance));
        }

    }

}
