package olog.dev.leeto.dagger.component;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;
import olog.dev.leeto.App;
import olog.dev.leeto.dagger.annotation.PerApplication;
import olog.dev.leeto.dagger.module.AppContextModule;
import olog.dev.leeto.dagger.module.ReactiveModule;
import olog.dev.leeto.dagger.module.RepositoryModule;
import olog.dev.leeto.model.repository.RepositoryInterface;
import olog.dev.leeto.utility.rx.BaseSchedulerProvider;

@Component(modules = {
        AppContextModule.class,
        RepositoryModule.class,
        ReactiveModule.class
})
@PerApplication
public interface AppComponent {

    void inject(App app);

    RepositoryInterface getRepository();

    BaseSchedulerProvider getSchedulerProvider();

    CompositeDisposable getCompositeDisposable();
}
