package olog.dev.leeto.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

public interface BasePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void subscribe();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void unsubscribe();

}
