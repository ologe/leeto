package olog.dev.leeto.dagger.module;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.dagger.annotation.PerActivity;
import olog.dev.leeto.model.permission.IPermissionHelper;
import olog.dev.leeto.model.permission.PermissionHelper;

@Module
public class ActivityHelperModule {

    @Provides
    @PerActivity
    IPermissionHelper providePermissionHelper(PermissionHelper permissionHelper){
        return permissionHelper;
    }

}
