package olog.dev.leeto.ui.activity_add_journey.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import olog.dev.leeto.app.PermissionModule;
import olog.dev.leeto.dagger.PerActivity;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyActivity;

@Subcomponent(modules = {
        AddJourneyModule.class,
        PermissionModule.class
})
@PerActivity
public interface AddJourneySubComponent extends AndroidInjector<AddJourneyActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AddJourneyActivity>{

        public abstract Builder addJourneyModule(AddJourneyModule module);

        @Override
        public void seedInstance(AddJourneyActivity instance) {
            addJourneyModule(new AddJourneyModule(instance));
        }

    }

}
