package olog.dev.leeto.activity_detail;

import android.app.Activity;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import olog.dev.leeto.R;
import olog.dev.leeto.activity_add_stop.AddStopActivity;
import olog.dev.leeto.databinding.ActivityDetailBinding;
import olog.dev.leeto.model.pojo.Journey;
import olog.dev.leeto.model.pojo.Stop;
import timber.log.Timber;


public class DetailActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private static final String TAG = AddStopActivity.class.getSimpleName();
    public static String EXTRA_STOP = TAG + "extra.stop";
    public static int RC_ADD_STOP = 200;

    public static final String BUNDLE_JOURNEY = TAG + "bundle.journey";
    public static final String BUNDLE_POSITION = TAG + "bundle.position";

    public static final String SHARED_ROOT = TAG + "shared.root";
    public static final String SHARED_JOURNEY_NAME = TAG + "shared.journey_name";

    private DetailActivityContract.Presenter presenter;

    private Journey journey;

    private ActivityDetailBinding binding;

    private MapController mapController;

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        postponeEnterTransition();

        presenter = new DetailActivityPresenter();

        binding.setPresenter(presenter);

        journey = getIntent().getParcelableExtra(BUNDLE_JOURNEY);
        int position = getIntent().getIntExtra(BUNDLE_POSITION, -1);

        mapController = new MapController(journey, savedInstanceState, getLifecycle(), binding.map);

        binding.journeyName.setText(journey.getName());

        binding.journeyName.setTransitionName(SHARED_JOURNEY_NAME + position);
        binding.root.setTransitionName(SHARED_ROOT + position);

        binding.viewPager.setAdapter(new StopPagerAdapter(journey.getStopsList(), getSupportFragmentManager()));
        binding.inkIndicator.setViewPager(binding.viewPager);

        setNavigationBarTransition();

        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override public void onTransitionStart(Transition transition) {}

            @Override public void onTransitionCancel(Transition transition) {}

            @Override public void onTransitionPause(Transition transition) {}

            @Override public void onTransitionResume(Transition transition) {}

            @Override
            public void onTransitionEnd(Transition transition) {
                Timber.d("onTransitionEnd");
                binding.root2.setVisibility(View.VISIBLE);
            }
        });

        getWindow().getSharedElementReturnTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                Timber.d("onTransitionStart");
                binding.root2.setVisibility(View.INVISIBLE);
            }

            @Override public void onTransitionEnd(Transition transition) {}

            @Override public void onTransitionCancel(Transition transition) {}

            @Override public void onTransitionPause(Transition transition) {}

            @Override public void onTransitionResume(Transition transition) {}
        });
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
    public void onResume() {
        super.onResume();
        binding.viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.viewPager.removeOnPageChangeListener(onPageChangeListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_ADD_STOP && resultCode == Activity.RESULT_OK){
            // TODO dentro try catch
            Stop stop = data.getParcelableExtra(EXTRA_STOP);

            mapController.addMarker(stop);
//            Repository.getInstance(this)
//                    .addStopToJourney(this, journey, stop); TODO

//            journey.addStop(stop);
            binding.viewPager.getAdapter().notifyDataSetChanged();
        }
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
        @Override public void onPageScrollStateChanged(int state) {}

        @Override
        public void onPageSelected(int position) {
            binding.map.getMapAsync(googleMap -> mapController.moveTo(
                    journey.getStopsList().get(position).getLocation(), position));
        }
    };

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

}
