package olog.dev.leeto.ui._activity_detail;

import android.arch.lifecycle.LiveDataReactiveStreams;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseActivity;

import static io.reactivex.BackpressureStrategy.LATEST;


public class DetailActivity extends BaseActivity {

    private static final String TAG = "DetailActivity";

    public static final String BUNDLE_JOURNEY_ID = TAG + "bundle.journey_id";
    public static final String BUNDLE_POSITION = TAG + "bundle.position";

    public static final String SHARED_ROOT = TAG + "shared.root";
    public static final String SHARED_JOURNEY_NAME = TAG + "shared.journey_name";

    @BindView(R.id.journeyName) TextView journeyName;
    @BindView(R.id.rootBackground) View rootBackground;
//    @BindView(R.id.viewPager) ViewPager viewPager;
//    @BindView(R.id.inkIndicator) InkPageIndicator inkIndicator;
    @BindView(R.id.map) DetailMap map;

    @Inject DetailActivityViewModel viewModel;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        unbinder = ButterKnife.bind(this);

        postponeEnterTransition();

        View decorView = getWindow().getDecorView();

        LiveDataReactiveStreams.fromPublisher(Observable.combineLatest(viewModel.observeJourney().toObservable(),
                RxView.preDraws(decorView, () -> {
                    if (decorView != null) {
                        View navigationBar = decorView.findViewById(android.R.id.navigationBarBackground);

                        if (navigationBar == null) {
                            startPostponedEnterTransition();
                            return true;
                        }
                        navigationBar.setTransitionName(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
                    }
                    int position = getIntent().getIntExtra(BUNDLE_POSITION, 0);
                    journeyName.setTransitionName(SHARED_JOURNEY_NAME + position);
                    rootBackground.setTransitionName(SHARED_ROOT + position);

                    return true;
                }).take(1),
                (journey, t2) -> {
                    journeyName.setText(journey.getName());
                    return true;
                })
                .toFlowable(LATEST))
                .observe(this, aBoolean -> {
                    startPostponedEnterTransition();
                });

    }

    public void setupUI() {
//        map.init(journey);

//        viewPager.setAdapter(new StopPagerAdapter(journey.getStopList(), getSupportFragmentManager()));
//        inkIndicator.setViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
//        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
//        viewPager.removeOnPageChangeListener(onPageChangeListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if added:
//            mapController.addMarker(stop);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
        @Override public void onPageScrollStateChanged(int state) {}

        @Override
        public void onPageSelected(int position) {
//            binding.mapToDomain.getMapAsync(googleMap -> mapController.moveTo(
//                    journey.getStopsList().get(position).getLocation(), position));
        }
    };

}
