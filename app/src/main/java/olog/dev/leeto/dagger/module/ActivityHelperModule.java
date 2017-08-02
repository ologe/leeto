package olog.dev.leeto.dagger.module;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.dagger.annotation.PerActivity;
import olog.dev.leeto.model.AppPermissionHelper;
import olog.dev.leeto.model.PermissionHelperInterface;

@Module
public class ActivityHelperModule {

    @Provides
    @PerActivity
    PermissionHelperInterface providePermissionHelper(AppPermissionHelper permissionHelper){
        return permissionHelper;
    }

}
