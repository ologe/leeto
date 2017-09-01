package olog.dev.leeto.ui.fragment_stop;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;
import olog.dev.leeto.base.AbsPresenter;
import olog.dev.leeto.data.model.Stop;
import olog.dev.leeto.data.repository.IRepository;
import olog.dev.leeto.utility.dagger.annotations.scope.PerFragment;
import olog.dev.leeto.utility.reactive.BaseSchedulersProvider;

@PerFragment
public class StopPresenter extends AbsPresenter<StopContract.View> implements StopContract.Presenter {

    private final Stop stop;

    @Inject
    StopPresenter(StopContract.View view,
                  IRepository repository,
                  CompositeDisposable subscriptions,
                  BaseSchedulersProvider schedulers,
                  @Named("item") Stop stop) {
        super(view, repository, subscriptions, schedulers);
        this.stop = stop;
    }

    @Override
    protected void subscribe() {
        view.setupUI(stop);
    }
}
