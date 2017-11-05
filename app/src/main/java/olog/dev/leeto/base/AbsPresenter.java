package olog.dev.leeto.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.CallSuper;

public abstract class AbsPresenter<T extends BaseView> implements BasePresenter {

    protected final T view;

    public AbsPresenter(T view){
        this.view = view;
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
//        subscriptions.clear();
    }

}
