package olog.dev.leeto.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    @Nullable
    protected Fragment findFragmentByTag(String TAG){
        return getSupportFragmentManager().findFragmentByTag(TAG);
    }

}
