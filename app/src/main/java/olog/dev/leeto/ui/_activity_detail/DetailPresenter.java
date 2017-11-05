//package olog.dev.leeto.ui._activity_detail;
//
//import android.support.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;
//
//import javax.inject.Inject;
//
//import olog.dev.leeto.base.AbsPresenter;
//import olog.dev.leeto.data.model.Journey;
//import olog.dev.leeto.ui.navigator.INavigator;
//import olog.dev.leeto.utility.dagger.annotations.scope.PerActivity;
//
//@PerActivity
//public class DetailPresenter extends AbsPresenter<DetailContract.View> implements DetailContract.Presenter{
//
//    private INavigator navigator;
//    private Journey journey;
//
//    @Inject
//    DetailPresenter(DetailContract.View view,
//                    INavigator navigator,
//                    Journey journey) {
//        super(view);
//        this.navigator = navigator;
//        this.journey = journey;
//    }
//
//    @Override
//    public void init() {
//        view.setupUI(journey);
//    }
//
//    @Override
//    protected void subscribe() {}
//
//    @Override
//    public void toAddStopActivity(@NonNull FloatingActionButton fab) {
//        navigator.toAddStopActivity(fab);
//    }
//    @Override
//    public void closeActivity() {
//        navigator.closeActivity();
//    }
//}
