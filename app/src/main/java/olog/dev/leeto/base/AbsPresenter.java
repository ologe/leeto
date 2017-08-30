package olog.dev.leeto.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.CallSuper;

import io.reactivex.disposables.CompositeDisposable;
import olog.dev.leeto.data.repository.IRepository;
import olog.dev.leeto.utility.reactive.BaseSchedulersProvider;

public abstract class AbsPresenter<T extends BaseView> implements BasePresenter {

    protected final IRepository repository;
    protected final T view;
    protected final CompositeDisposable subscriptions;
    protected final BaseSchedulersProvider schedulers;

    public AbsPresenter(T view,
                        IRepository repository,
                        CompositeDisposable subscriptions,
                        BaseSchedulersProvider schedulers){
        this.repository = repository;
        this.view = view;
        this.subscriptions = subscriptions;
        this.schedulers = schedulers;
        view.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected abstract void subscribe();

    /**
     * Unsubscribe rxJava disposables
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    @CallSuper
    protected void unsubscribe(){
        subscriptions.clear();
    }

}
