package olog.dev.leeto.data.repository;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.utility.dagger.annotations.scope.PerApplication;

@Module
public class RepositoryModule {

    @Provides
    @PerApplication
    IRepository provideRepository(Repository repository){
        return repository;
    }

}
