package olog.dev.leeto.ui._activity_detail.di;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import olog.dev.leeto.ui._activity_detail.DetailActivity;

@Module(subcomponents = DetailActivitySubComponent.class)
public abstract class DetailActivityInjector {

    @Binds
    @IntoMap
    @ActivityKey(DetailActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindDetailActivityInjectorFactory(
            DetailActivitySubComponent.Builder builder
    );

}
