package olog.dev.leeto.ui._activity_main.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import olog.dev.leeto.dagger.PerActivity;
import olog.dev.leeto.ui._activity_main.MainActivity;

@Subcomponent(modules = {
        MainActivityModule.class
})
@PerActivity
public interface MainActivitySubComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity>{

        public abstract Builder module(MainActivityModule module);

        @Override
        public void seedInstance(MainActivity instance) {
            module(new MainActivityModule(instance));
        }

    }

}
