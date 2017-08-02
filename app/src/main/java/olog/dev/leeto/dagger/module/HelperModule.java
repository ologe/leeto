package olog.dev.leeto.dagger.module;

import java.util.Calendar;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.dagger.annotation.PerApplication;

@Module
public class HelperModule {

    @Provides
    @PerApplication
    Calendar provideCalendar(){
        return Calendar.getInstance();
    }

}
