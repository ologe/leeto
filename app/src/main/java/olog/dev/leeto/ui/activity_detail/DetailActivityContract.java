package olog.dev.leeto.ui.activity_detail;

import android.content.Context;
import android.view.View;

public interface DetailActivityContract {

    interface Presenter {
        void startAddLocationActivity(View fab);
        void closeActivity(Context context);
    }

}
