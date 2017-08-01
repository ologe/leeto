package olog.dev.leeto.dagger.module;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.dagger.annotation.PerApplication;
import olog.dev.leeto.model.repository.Repository;
import olog.dev.leeto.model.repository.RepositoryInterface;

@Module
public class RepositoryModule {

    @Provides
    @PerApplication
    RepositoryInterface provideRepository(Repository repository){
        return repository;
    }

}
