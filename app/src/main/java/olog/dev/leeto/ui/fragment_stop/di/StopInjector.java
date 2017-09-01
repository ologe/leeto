package olog.dev.leeto.ui.fragment_stop.di;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import olog.dev.leeto.ui.fragment_stop.StopFragment;

@Module(subcomponents = StopSubComponent.class)
public abstract class StopInjector {

    @Binds
    @IntoMap
    @FragmentKey(StopFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindStopFragmentInjectorFactory(
            StopSubComponent.Builder builder
    );

}
