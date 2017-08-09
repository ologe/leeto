package olog.dev.leeto.ui.activity_detail;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import olog.dev.leeto.R;
import olog.dev.leeto.ui.activity_add_stop.AddStopActivity;

import static olog.dev.leeto.ui.activity_detail.DetailActivity.RC_ADD_STOP;

public class DetailActivityPresenter implements DetailActivityContract.Presenter{


    @Override
    public void startAddLocationActivity(View fab) {
        Context context = fab.getContext();
        Intent intent = new Intent(context, AddStopActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                (Activity) context, fab, context.getString(R.string.transition_fab));
        ((Activity)context).startActivityForResult(intent, RC_ADD_STOP, options.toBundle());
    }

    @Override
    public void closeActivity(Context context) {
        ((Activity)context).finishAfterTransition();
    }
}
