package olog.dev.leeto.ui._activity_main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseActivity;
import olog.dev.leeto.data.model.Journey;
import olog.dev.leeto.ui._activity_main.list.JourneyAdapter;
import olog.dev.leeto.ui._activity_main.list.ParallaxRecyclerView;
import olog.dev.leeto.utility.HandlerUtils;

public class MainActivity extends BaseActivity implements MainContract.View {

    public static final int REQUEST_CODE = 123;
    private static final int SCROLL_DELAY = 400;

    @Inject MainContract.Presenter presenter;
    @Inject JourneyAdapter adapter;
    @Inject LinearLayoutManager layoutManager;

    private Unbinder unbinder;
    private Handler handler;
    private SmoothScrollToPositionRunnable smoothScrollToPositionRunnable;

    @BindView(R.id.root) View root;
    @BindView(R.id.list) ParallaxRecyclerView list;
    @BindView(R.id.scrim) View scrim;
    @BindView(R.id.toolbar) View toolbar;
    @BindView(R.id.addJourneyFab) FloatingActionButton addJourney;
    @BindView(R.id.back) ImageView back;

    @OnClick(R.id.addJourneyFab)
    public void addJourney(FloatingActionButton view){
        if (list.isFabAdd()) {
            presenter.toAddJourney(view);
        } else {
            list.smoothScrollToPosition(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);
        handler = new Handler();

        getLifecycle().addObserver(list);

        list.setAdapter(adapter);
        list.setLayoutManager(layoutManager);
        list.setViews(scrim, toolbar, addJourney);
    }

    @Override
    protected void onStop() {
        super.onStop();
        HandlerUtils.removeCallback(handler, smoothScrollToPositionRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(adapter.getItemCount() > 1){
                toolbar.setAlpha(0);
                scrollToPositionWithDelay(0);
            }
        }
    }

    @Override
    public void showJourneysList(@NonNull List<Journey> data) {
        adapter.updateDataSet(data);
    }

    /**
     * setup detail activity animation
     */
    @Override
    public void onItemClick(@NonNull Journey journey, int currentPosition, View scrim, View journeyName) {
        Map<String, View> transitionViews = new WeakHashMap<>();
        transitionViews.put("scrim",scrim);
        transitionViews.put("journeyName", journeyName);
        transitionViews.put("addJourney", addJourney);
        transitionViews.put("root", root);
        transitionViews.put("toolbar", toolbar);
        transitionViews.put("back", back);
        presenter.toDetailActivity(transitionViews, journey.getId(), currentPosition, layoutManager);
    }


    private void scrollToPositionWithDelay(int position) {
        HandlerUtils.removeCallback(handler, smoothScrollToPositionRunnable);
        smoothScrollToPositionRunnable = new SmoothScrollToPositionRunnable(list, position);
        handler.postDelayed(smoothScrollToPositionRunnable, SCROLL_DELAY);
    }

    private static class SmoothScrollToPositionRunnable implements Runnable {

        private final int position;
        private final RecyclerView list;

        public SmoothScrollToPositionRunnable(RecyclerView list ,int position) {
            this.list = list;
            this.position = position;
        }

        @Override
        public void run() {
            list.smoothScrollToPosition(position);
        }
    }
}
