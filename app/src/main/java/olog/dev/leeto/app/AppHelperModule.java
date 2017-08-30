package olog.dev.leeto.app;

import java.util.Calendar;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.utility.dagger.annotations.scope.PerApplication;

@Module
public class AppHelperModule {

    @Provides
    @PerApplication
    Calendar provideCalendar(){
        return Calendar.getInstance();
    }

}
