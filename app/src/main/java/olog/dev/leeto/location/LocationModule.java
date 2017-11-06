package olog.dev.leeto.location;

import android.content.Context;

import com.patloew.rxlocation.RxLocation;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dev.olog.shared.ApplicationContext;

@Module
public class LocationModule {

    @Provides
    @Singleton
    RxLocation provideReactiveLocation(@ApplicationContext Context context){
        return new RxLocation(context);
    }

}
