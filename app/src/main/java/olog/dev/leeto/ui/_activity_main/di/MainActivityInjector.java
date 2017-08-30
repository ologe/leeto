package olog.dev.leeto.ui._activity_main.di;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import olog.dev.leeto.ui._activity_main.MainActivity;

@Module(subcomponents = MainActivitySubComponent.class)
public abstract class MainActivityInjector {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMainActivityInjectorFactory(
            MainActivitySubComponent.Builder builder
    );

}
