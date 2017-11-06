//package olog.dev.leeto.ui._activity_detail.di;
//
//import dagger.Subcomponent;
//import dagger.android.AndroidInjector;
//import olog.dev.leeto.ui._activity_detail.DetailActivity;
//import olog.dev.leeto.ui.fragment_stop.di.StopInjector;
//import olog.dev.leeto.ui.navigator.NavigatorModule;
//import olog.dev.leeto.dagger.PerActivity;
//
//@Subcomponent(modules = {
//        DetailModule.class,
//        NavigatorModule.class,
//
//        StopInjector.class
//})
//@PerActivity
//public interface DetailActivitySubComponent extends AndroidInjector<DetailActivity> {
//
//    @Subcomponent.Builder
//    abstract class Builder extends AndroidInjector.Builder<DetailActivity>{
//
//        public abstract Builder detailActivityModule(DetailModule module);
//
//        @Override
//        public void seedInstance(DetailActivity instance) {
//            detailActivityModule(new DetailModule(instance));
//        }
//
//    }
//
//}
