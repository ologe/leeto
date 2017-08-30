package olog.dev.leeto.ui.navigator;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.utility.dagger.annotations.scope.PerActivity;

@Module
public class NavigatorModule {

    @Provides
    @PerActivity
    INavigator navigator(Navigator navigator){
        return navigator;
    }

}
