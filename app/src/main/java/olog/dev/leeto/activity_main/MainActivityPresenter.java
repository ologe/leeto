package olog.dev.leeto.activity_main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import olog.dev.leeto.activity_add_journey.AddJourneyActivity;
import olog.dev.leeto.custom_view.ParallaxRecyclerView;
import olog.dev.leeto.model.repository.JourneyRepository;

public class MainActivityPresenter implements MainActivityContract.Presenter, LifecycleObserver {

    private MainActivityContract.View view;

    private JourneyRepository repository;
    private CompositeDisposable compositeDisposable;
    private Lifecycle lifecycle;

    public MainActivityPresenter(Context context, MainActivityContract.View view, Lifecycle lifecycle) {
        this.view = view;
        compositeDisposable = new CompositeDisposable();
        this.lifecycle = lifecycle;

        lifecycle.addObserver(this);

        repository = JourneyRepository.getInstance(context);
        loadJourneysList(context);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        lifecycle.removeObserver(this);
        compositeDisposable.clear();

        view = null;
        lifecycle = null;
    }

    @Override
    public void onFabClick(View view, ParallaxRecyclerView recyclerView) {
        if(!recyclerView.isFabAdd()){
            recyclerView.smoothScrollToPosition(0);
        } else startAddJourneyActivity(view);

    }

    private void startAddJourneyActivity(View view){
        Context ctx = view.getContext();
        Intent intent = new Intent(ctx, AddJourneyActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                (Activity) ctx, view, view.getTransitionName());
        ((Activity) ctx).startActivityForResult(intent, MainActivity.RC_ADD_JOURNEY, options.toBundle());
    }

    @Override
    public void loadJourneysList(Context context) {

        Disposable disposable = repository.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( journeys -> {
                    if(!journeys.isEmpty()) view.updateJourneysList(journeys);
                    // TODO else
                }, Throwable::printStackTrace);

        compositeDisposable.add(disposable);
    }
}
