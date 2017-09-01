package olog.dev.leeto.ui.fragment_stop;

import android.support.annotation.NonNull;

import olog.dev.leeto.base.BaseView;
import olog.dev.leeto.data.model.Stop;

public interface StopContract {

    interface View extends BaseView {
        void setupUI(@NonNull Stop stop);
    }

    interface Presenter {
    }

}
