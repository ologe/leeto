package olog.dev.leeto.ui.fragment_stop.di;

import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import olog.dev.leeto.data.model.Stop;
import olog.dev.leeto.ui.fragment_stop.StopContract;
import olog.dev.leeto.ui.fragment_stop.StopFragment;
import olog.dev.leeto.ui.fragment_stop.StopPresenter;
import olog.dev.leeto.utility.dagger.annotations.scope.PerFragment;

@Module
public class StopModule {

    private StopFragment fragment;

    public StopModule(StopFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @PerFragment
    @Named("item")
    Stop provideStop(List<Stop> stopList){
        int position = fragment.getArguments().getInt(StopFragment.ARGUMENT_STOP_POSITION);
        return stopList.get(position);
    }

    @Provides
    @PerFragment
    StopContract.View provideView(){
        return fragment;
    }

    @Provides
    @PerFragment
    StopContract.Presenter providePreseter(StopPresenter presenter){
        return presenter;
    }

}
