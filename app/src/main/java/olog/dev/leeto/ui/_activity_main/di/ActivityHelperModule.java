package olog.dev.leeto.ui._activity_main.di;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.data.permission.IPermissionHelper;
import olog.dev.leeto.data.permission.PermissionHelper;
import olog.dev.leeto.utility.dagger.annotations.scope.PerActivity;

@Module
public class ActivityHelperModule {

    @Provides
    @PerActivity
    IPermissionHelper providePermissionHelper(PermissionHelper permissionHelper){
        return permissionHelper;
    }

}
