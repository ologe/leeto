package olog.dev.leeto.ui._activity_detail;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;

import olog.dev.leeto.base.BaseView;
import olog.dev.leeto.data.model.Journey;

public interface DetailContract {

    interface View extends BaseView{
        void setupUI(@NonNull Journey journey);
    }

    interface Presenter {
        void init();
        void toAddStopActivity(@NonNull FloatingActionButton fab);
        void closeActivity();
    }

}
