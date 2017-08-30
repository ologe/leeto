package olog.dev.leeto.ui._activity_main;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import olog.dev.leeto.base.AbsPresenter;
import olog.dev.leeto.data.model.Journey;
import olog.dev.leeto.data.repository.IRepository;
import olog.dev.leeto.ui._activity_main.view.ParallaxRecyclerView;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyActivity;
import olog.dev.leeto.utility.dagger.annotations.scope.PerActivity;
import olog.dev.leeto.utility.reactive.BaseSchedulersProvider;

@PerActivity
public class MainPresenter extends AbsPresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    MainPresenter(MainContract.View view,
                  IRepository repository,
                  CompositeDisposable subscriptions,
                  BaseSchedulersProvider schedulers) {
        super(view, repository, subscriptions, schedulers);
    }

    @Override
    public void subscribe() {
        Disposable disposable = repository.observeToData()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe( journeys -> {

                    if(!journeys.isEmpty()){
                        view.showJourneysList(journeys);
                    } else view.showNoJourneysFragment();

                }, Throwable::printStackTrace);

        subscriptions.add(disposable);
    }

    @Override
    public void onFabClick(@NonNull FloatingActionButton view, @NonNull ParallaxRecyclerView recyclerView) {
        if(!recyclerView.isFabAdd()){
            recyclerView.smoothScrollToPosition(0);
        } else AddJourneyActivity.startActivity(view);

    }

    @Override
    public void deleteJourney(@NonNull Journey journey) {
        repository.deleteJourney(journey);
    }
}
