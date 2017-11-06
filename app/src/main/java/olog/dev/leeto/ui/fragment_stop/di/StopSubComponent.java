//package olog.dev.leeto.ui.fragment_stop.di;
//
//import dagger.Subcomponent;
//import dagger.android.AndroidInjector;
//import olog.dev.leeto.ui.fragment_stop.StopFragment;
//import olog.dev.leeto.dagger.PerFragment;
//
//@Subcomponent(modules = {
//        StopModule.class
//})
//@PerFragment
//public interface StopSubComponent extends AndroidInjector<StopFragment> {
//
//
//    @Subcomponent.Builder
//    abstract class Builder extends AndroidInjector.Builder<StopFragment>{
//
//        public abstract Builder stopModule(StopModule module);
//
//        @Override
//        public void seedInstance(StopFragment instance) {
//            stopModule(new StopModule(instance));
//        }
//
//    }
//
//}
