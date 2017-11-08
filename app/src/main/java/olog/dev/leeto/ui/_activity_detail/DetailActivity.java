package olog.dev.leeto.ui._activity_detail;

import android.arch.lifecycle.LiveDataReactiveStreams;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseActivity;
import olog.dev.leeto.utility.RxUtils;


public class DetailActivity extends BaseActivity {

    private static final String TAG = "DetailActivity";

    public static final String BUNDLE_JOURNEY_ID = TAG + "bundle.journey_id";

    public static final String SHARED_ROOT = TAG + "shared.root";
    public static final String SHARED_JOURNEY_NAME = TAG + "shared.journey_name";
    public static final String SHARED_JOURNEY_IMAGE = TAG + "shared.journey_image";

    private Disposable preDrawDisposable;

    @BindView(R.id.rootBackground) View rootBackground;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.journeyName) TextView journeyName;
    @BindView(R.id.addStop) ImageView addStop;

    @Inject DetailActivityViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        postponeEnterTransition();

        View decorView = getWindow().getDecorView();

        long journeyId = getIntent().getLongExtra(BUNDLE_JOURNEY_ID, 0);
        rootBackground.setTransitionName(SHARED_ROOT + journeyId);
        image.setTransitionName(SHARED_JOURNEY_IMAGE + journeyId);
        journeyName.setTransitionName(DetailActivity.SHARED_JOURNEY_NAME + journeyId);

        preDrawDisposable = RxView.preDraws(decorView, () -> {
            if (decorView != null) {
                View navigationBar = decorView.findViewById(android.R.id.navigationBarBackground);

                if (navigationBar != null) {
                    navigationBar.setTransitionName(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
                }

            }
            return true;
        }).take(1).subscribe();

        LiveDataReactiveStreams.fromPublisher(viewModel.observeJourney()
                .delay(20, TimeUnit.MILLISECONDS))
                .observe(this, journey -> {
                    if (journey != null){
                        journeyName.setText(journey.getName());
                    }
                    startPostponedEnterTransition();
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxUtils.unsubscribe(preDrawDisposable);
    }


}
