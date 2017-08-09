package olog.dev.leeto.dagger.module;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.dagger.annotation.PerApplication;
import olog.dev.leeto.model.repository.IRepository;
import olog.dev.leeto.model.repository.Repository;

@Module
public class RepositoryModule {

    @Provides
    @PerApplication
    IRepository provideRepository(Repository repository){
        return repository;
    }

}
