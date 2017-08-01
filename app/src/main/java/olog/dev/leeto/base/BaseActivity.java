package olog.dev.leeto.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.v7.app.AppCompatActivity;

import olog.dev.leeto.App;
import olog.dev.leeto.dagger.component.AppComponent;

public abstract class BaseActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    protected AppComponent getAppComponent(){
        return ((App) getApplication()).getAppComponent();
    }

}
