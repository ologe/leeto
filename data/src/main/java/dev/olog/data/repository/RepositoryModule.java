package dev.olog.data.repository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dev.olog.domain.repository.JourneyDataStore;

@Module
public abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract JourneyDataStore provideJourneyDataStore(JourneyDataStoreImpl dataStore);

}
