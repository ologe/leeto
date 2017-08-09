package olog.dev.leeto.dagger.component;

import dagger.Component;
import olog.dev.leeto.dagger.annotation.PerActivity;
import olog.dev.leeto.dagger.module.ActivityHelperModule;
import olog.dev.leeto.dagger.module.AddJourneyModule;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyActivity;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {
        AddJourneyModule.class,
        ActivityHelperModule.class
})
public interface AddJourneyComponent {

    void inject(AddJourneyActivity activity);

}
