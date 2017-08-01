package olog.dev.leeto.dagger.component;

import dagger.Component;
import olog.dev.leeto.activity_main.MainActivity;
import olog.dev.leeto.custom_view.ParallaxRecyclerView;
import olog.dev.leeto.dagger.annotation.PerActivity;
import olog.dev.leeto.dagger.module.MainActivityModule;

@PerActivity
@Component(dependencies = AppComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {

    void inject(MainActivity activity);
    void inject(ParallaxRecyclerView recyclerView);

}
