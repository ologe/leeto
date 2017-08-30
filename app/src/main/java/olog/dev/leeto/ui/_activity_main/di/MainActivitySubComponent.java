package olog.dev.leeto.ui._activity_main.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import olog.dev.leeto.ui._activity_main.MainActivity;
import olog.dev.leeto.ui.navigator.NavigatorModule;
import olog.dev.leeto.utility.dagger.annotations.scope.PerActivity;

@Subcomponent(modules = {
        MainActivityModule.class,
        NavigatorModule.class
})
@PerActivity
public interface MainActivitySubComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity>{

        public abstract Builder mainActivityModule(MainActivityModule module);

        @Override
        public void seedInstance(MainActivity instance) {
            mainActivityModule(new MainActivityModule(instance));
        }

    }

}
