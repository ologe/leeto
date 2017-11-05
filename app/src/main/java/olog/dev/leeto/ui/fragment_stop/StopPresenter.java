package olog.dev.leeto.ui.fragment_stop;

import javax.inject.Inject;
import javax.inject.Named;

import olog.dev.leeto.base.AbsPresenter;
import olog.dev.leeto.data.model.Stop;
import olog.dev.leeto.utility.dagger.annotations.scope.PerFragment;

@PerFragment
public class StopPresenter extends AbsPresenter<StopContract.View> implements StopContract.Presenter {

    private final Stop stop;

    @Inject
    StopPresenter(StopContract.View view,
                  @Named("item") Stop stop) {
        super(view);
        this.stop = stop;
    }

    @Override
    protected void subscribe() {
        view.setupUI(stop);
    }
}
