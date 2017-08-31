package olog.dev.leeto.ui.activity_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseActivity;
import olog.dev.leeto.data.model.Journey;
import olog.dev.leeto.ui.activity_detail.view_pager.StopPagerAdapter;
import olog.dev.leeto.ui.custom_view.InkPageIndicator;
import olog.dev.leeto.ui.custom_view.Mappp;


public class DetailActivity extends BaseActivity implements DetailContract.View {

    private static final String TAG = "DetailActivity";

    public static final String BUNDLE_JOURNEY_ID = TAG + "bundle.journey_id";
    public static final String BUNDLE_POSITION = TAG + "bundle.position";

    public static final String SHARED_ROOT = TAG + "shared.root";
    public static final String SHARED_JOURNEY_NAME = TAG + "shared.journey_name";

    @BindView(R.id.journeyName) TextView journeyName;
    @BindView(R.id.rootBackground) View rootBackground;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.inkIndicator) InkPageIndicator inkIndicator;
    @BindView(R.id.map) Mappp map;

    @Inject DetailContract.Presenter presenter;
    @Inject int position;

    @OnClick(R.id.addStop)
    public void toAddStopActivity(FloatingActionButton view){
        presenter.toAddStopActivity(view);
    }

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        unbinder = ButterKnife.bind(this);

        map.onCreate(savedInstanceState);

        postponeEnterTransition();

        setNavigationBarTransition();
    }

    private void setNavigationBarTransition(){
        View decorView = getWindow().getDecorView();
        if(decorView == null) {
            startPostponedEnterTransition();
            return;
        }

        decorView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                decorView.getViewTreeObserver().removeOnPreDrawListener(this);

                View navigationBar = decorView.findViewById(android.R.id.navigationBarBackground);

                if(navigationBar == null) {
                    startPostponedEnterTransition();
                    return true;
                }
                navigationBar.setTransitionName(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);

                startPostponedEnterTransition();
                return true;
            }
        });

    }

    @Override
    public void setupUI(@NonNull Journey journey) {
        map.init(journey);

        journeyName.setText(journey.getName());

        journeyName.setTransitionName(SHARED_JOURNEY_NAME + position);
        rootBackground.setTransitionName(SHARED_ROOT + position);

        viewPager.setAdapter(new StopPagerAdapter(journey.getStopsList(), getSupportFragmentManager()));
        inkIndicator.setViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        map.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
        viewPager.removeOnPageChangeListener(onPageChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        map.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
        unbinder.unbind();
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
//            binding.map.getMapAsync(googleMap -> mapController.moveTo(
//                    journey.getStopsList().get(position).getLocation(), position));
        }
    };

}
