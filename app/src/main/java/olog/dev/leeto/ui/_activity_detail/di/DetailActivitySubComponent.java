package olog.dev.leeto.ui._activity_detail.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import olog.dev.leeto.dagger.PerActivity;
import olog.dev.leeto.ui._activity_detail.DetailActivity;

@Subcomponent(modules = {
        DetailModule.class
})
@PerActivity
public interface DetailActivitySubComponent extends AndroidInjector<DetailActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DetailActivity>{

        public abstract Builder detailActivityModule(DetailModule module);

        @Override
        public void seedInstance(DetailActivity instance) {
            detailActivityModule(new DetailModule(instance));
        }

    }

}
