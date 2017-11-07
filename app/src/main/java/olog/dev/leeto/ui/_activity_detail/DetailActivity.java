package olog.dev.leeto.ui._activity_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;

import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseActivity;
import olog.dev.leeto.ui.custom_view.InkPageIndicator;
import olog.dev.leeto.utility.RxUtils;


public class DetailActivity extends BaseActivity {

    private static final String TAG = "DetailActivity";

    public static final String BUNDLE_JOURNEY_ID = TAG + "bundle.journey_id";

    public static final String SHARED_ROOT = TAG + "shared.root";
    public static final String SHARED_JOURNEY_NAME = TAG + "shared.journey_name";

    private Disposable preDrawDisposable;

    @BindView(R.id.rootBackground) View rootBackground;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.inkIndicator) InkPageIndicator inkIndicator;

    @Inject DetailActivityViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        postponeEnterTransition();

        viewPager.setAdapter(new JourneyPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(JourneyPagerAdapter.PAGE_COUNT - 1);
        viewModel.setCurrentViewPagerPage(1);

        inkIndicator.setViewPager(viewPager);

        View decorView = getWindow().getDecorView();

        long journeyId = getIntent().getLongExtra(BUNDLE_JOURNEY_ID, 0);
        rootBackground.setTransitionName(SHARED_ROOT + journeyId);

        preDrawDisposable = RxView.preDraws(decorView, () -> {
            if (decorView != null) {
                View navigationBar = decorView.findViewById(android.R.id.navigationBarBackground);

                if (navigationBar != null) {
                    navigationBar.setTransitionName(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
                }

            }
            return true;
        }).take(1).subscribe();

        viewModel.observeCurrentPage()
                .observe(this, viewPager::setCurrentItem);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxUtils.unsubscribe(preDrawDisposable);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() != 1){
            viewModel.setCurrentViewPagerPage(1);
        } else {
            super.onBackPressed();
        }
    }

}
