package dev.olog.data.repository;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dev.olog.data.db.AppDatabase;
import dev.olog.data.db.JourneyDao;
import dev.olog.shared.ApplicationContext;

@Module
public class RepositoryModuleHelper {

    @Provides
    @Singleton
    AppDatabase provideRoomDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "db")
                .build();
    }

    @Provides
    @Singleton
    JourneyDao provideJourneyDao(AppDatabase database){
        return database.journeyDao();
    }

}
