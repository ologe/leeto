package olog.dev.leeto.activity_main;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import olog.dev.leeto.activity_add_journey.AddJourneyActivity;
import olog.dev.leeto.activity_main.view.ParallaxRecyclerView;
import olog.dev.leeto.dagger.annotation.PerActivity;
import olog.dev.leeto.model.pojo.Journey;
import olog.dev.leeto.model.repository.RepositoryInterface;
import olog.dev.leeto.utility.rx.BaseSchedulerProvider;

@PerActivity
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private RepositoryInterface repository;
    private CompositeDisposable subscriptions;
    private BaseSchedulerProvider schedulerProvider;

    @Inject
    MainPresenter(MainContract.View view,
                  RepositoryInterface repository,
                  CompositeDisposable subscriptions,
                  BaseSchedulerProvider schedulerProvider) {
        this.view = view;
        this.repository = repository;
        this.subscriptions = subscriptions;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void subscribe() {
        Disposable disposable = repository.observeToData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe( journeys -> {
                    if(!journeys.isEmpty()){
                        view.showJourneysList(journeys);
                    } else view.showNoJourneysFragment();

                }, Throwable::printStackTrace);

        subscriptions.add(disposable);
    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();
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
