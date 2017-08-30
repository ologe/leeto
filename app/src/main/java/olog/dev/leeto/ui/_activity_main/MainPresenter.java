package olog.dev.leeto.ui._activity_main;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import olog.dev.leeto.base.AbsPresenter;
import olog.dev.leeto.data.repository.IRepository;
import olog.dev.leeto.ui._activity_main.list.ParallaxRecyclerView;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyActivity;
import olog.dev.leeto.ui.navigator.INavigator;
import olog.dev.leeto.utility.dagger.annotations.scope.PerActivity;
import olog.dev.leeto.utility.reactive.BaseSchedulersProvider;

@PerActivity
public class MainPresenter extends AbsPresenter<MainContract.View> implements MainContract.Presenter {

    private INavigator navigator;

    @Inject
    MainPresenter(MainContract.View view,
                  IRepository repository,
                  CompositeDisposable subscriptions,
                  BaseSchedulersProvider schedulers,
                  INavigator navigator) {
        super(view, repository, subscriptions, schedulers);
        this.navigator = navigator;
    }

    @Override
    public void subscribe() {
        Disposable dataDisposable = repository.observeToData()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe( journeys -> {

                    if(!journeys.isEmpty()){
                        view.showJourneysList(journeys);
                    } else navigator.toNoJourneyFragment();

                }, Throwable::printStackTrace);

        Disposable repositoryUpdatesDisposable = repository.registerToUpdates();

        subscriptions.addAll(dataDisposable, repositoryUpdatesDisposable);
    }

    @Override
    public void onFabClick(@NonNull FloatingActionButton view, @NonNull ParallaxRecyclerView recyclerView) {
        if(!recyclerView.isFabAdd()){
            recyclerView.smoothScrollToPosition(0);
        } else AddJourneyActivity.startActivity(view);
    }

    @Override
    public void toDetailActivity(@NonNull Map<String, View> views, long journeyId, int currentPosition, @NonNull LinearLayoutManager layoutManager) {
        navigator.toDetailActivity(views, journeyId, currentPosition, layoutManager);
    }
}
