package olog.dev.leeto.dagger.component;

import dagger.Component;
import olog.dev.leeto.app.AppComponent;
import olog.dev.leeto.dagger.module.AddJourneyModule;
import olog.dev.leeto.ui._activity_main.di.ActivityHelperModule;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyActivity;
import olog.dev.leeto.utility.dagger.annotations.scope.PerActivity;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {
        AddJourneyModule.class,
        ActivityHelperModule.class
})
public interface AddJourneyComponent {

    void inject(AddJourneyActivity activity);

}
