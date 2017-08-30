package olog.dev.leeto.ui.activity_add_journey.di;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyActivity;

@Module(subcomponents = AddJourneySubComponent.class)
public abstract class AddJourneyInjector {

    @Binds
    @IntoMap
    @ActivityKey(AddJourneyActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindAddJourneyActivityInjectorFactory(
            AddJourneySubComponent.Builder builder
    );


}
