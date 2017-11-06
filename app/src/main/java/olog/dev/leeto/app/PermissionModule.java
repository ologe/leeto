package olog.dev.leeto.app;

import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.dagger.PerActivity;

@Module
public class PermissionModule {

    @Provides
    @PerActivity
    RxPermissions provideRxPermission(Activity activity){
        return new RxPermissions(activity);
    }

}
