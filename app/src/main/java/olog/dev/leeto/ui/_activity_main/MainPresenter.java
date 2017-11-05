package olog.dev.leeto.ui._activity_main;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import olog.dev.leeto.base.AbsPresenter;
import olog.dev.leeto.ui.navigator.INavigator;
import olog.dev.leeto.utility.RxUtils;
import olog.dev.leeto.utility.dagger.annotations.scope.PerActivity;

@PerActivity
public class MainPresenter extends AbsPresenter<MainContract.View> implements MainContract.Presenter {

    private INavigator navigator;
    private Disposable repositoryUpdatesDisposable;

    @Inject
    MainPresenter(MainContract.View view,
                  INavigator navigator) {
        super(view);
        this.navigator = navigator;

//        repositoryUpdatesDisposable = repository.registerToUpdates();
    }

    @Override
    public void subscribe() {
//        Disposable dataDisposable = repository.observeToData()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe( journeys -> {
//
//                    if(!journeys.isEmpty()){
//                        navigator.removeFragment(NoJourneyFragment.TAG);
//                        view.showJourneysList(journeys);
//                    } else {
//                        navigator.toNoJourneyFragment();
//                    }
//
//                });

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        RxUtils.unsubscribe(repositoryUpdatesDisposable);
    }

    @Override
    public void toAddJourney(@NonNull FloatingActionButton fab) {
        navigator.toAddJourneyActivity(fab);
    }

    @Override
    public void toDetailActivity(@NonNull Map<String, View> views, long journeyId, int currentPosition, @NonNull LinearLayoutManager layoutManager) {
        navigator.toDetailActivity(views, journeyId, currentPosition, layoutManager);
    }
}
