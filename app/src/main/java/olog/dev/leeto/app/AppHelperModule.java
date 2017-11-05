package olog.dev.leeto.app;

import java.util.Calendar;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppHelperModule {

    @Provides
    @Singleton
    Calendar provideCalendar(){
        return Calendar.getInstance();
    }

}
