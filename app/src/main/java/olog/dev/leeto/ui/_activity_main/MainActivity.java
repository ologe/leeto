package olog.dev.leeto.ui._activity_main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.math.MathUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.Lazy;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseActivity;
import olog.dev.leeto.model.DisplayableJourney;
import olog.dev.leeto.ui._activity_main.list.JourneyAdapter;
import olog.dev.leeto.ui.navigator.Navigator;
import olog.dev.leeto.utility.DimensionUtils;
import olog.dev.leeto.utility.RxUtils;

public class MainActivity extends BaseActivity {

    private int scrimTopMargin;
    private int headerTopMargin;

    private CoordinatorLayout.LayoutParams scrimLayoutParams;

    @Inject MainActivityViewModel viewModel;
    @Inject JourneyAdapter adapter;
    @Inject Lazy<Navigator> navigator;
    private LinearLayoutManager layoutManager;

    private Unbinder unbinder;

    @BindView(R.id.root) View root;
    @BindView(R.id.list) RecyclerView list;
    @BindView(R.id.scrim) View scrim;
    @BindView(R.id.header) View header;
    @BindView(R.id.addJourneyFab) FloatingActionButton addJourneyFab;
    @BindView(R.id.back) ImageView back;

    private Disposable fabImageDisposable;
    private Disposable parallaxDisposable;

    @OnClick(R.id.addJourneyFab)
    public void addJourney(FloatingActionButton view){
        if (!list.canScrollVertically(-1)) {
            navigator.get().toAddJourneyActivity(view);
        } else {
            list.smoothScrollToPosition(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this);

        list.setAdapter(adapter);
        list.setLayoutManager(layoutManager);

        viewModel.observeJourneys()
                .observe(this, this::onDataChanged);

        scrimLayoutParams = (CoordinatorLayout.LayoutParams) scrim.getLayoutParams();

        scrimTopMargin = DimensionUtils.dip(this, 125);
        headerTopMargin = DimensionUtils.dip(this, 16);
    }

    private void onDataChanged(@Nullable List<DisplayableJourney> displayableItems){
        if (displayableItems != null){
            adapter.updateData(displayableItems);

            if (displayableItems.isEmpty()){
                navigator.get().showJourneyEmptyState();
            } else {
                navigator.get().hideJourneyEmptyState();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Observable<RecyclerViewScrollEvent> listObservable = RxRecyclerView
                .scrollEvents(list)
                .share();

        fabImageDisposable = listObservable
                .map(event -> list.canScrollVertically(-1))
                .distinctUntilChanged()
                .subscribe(canScrollUp -> addJourneyFab.setImageResource(canScrollUp
                        ? R.drawable.vd_arrow_up : R.drawable.vd_add),
                        Throwable::printStackTrace
                );

        parallaxDisposable = listObservable
                .map(RecyclerViewScrollEvent::dy)
                .filter(integer -> layoutManager.findFirstVisibleItemPosition() == 0)
                .subscribe(dy -> {
                    int newTopMargin = MathUtils.clamp(scrimLayoutParams.topMargin - dy / 3, 0, scrimTopMargin);
                    scrimLayoutParams.topMargin = newTopMargin;
                    scrim.setLayoutParams(scrimLayoutParams);

                    float translation = Math.abs(scrimTopMargin - newTopMargin);

                    header.setTranslationY(-translation);
                    header.setAlpha(1 - (translation / 150));
                }, Throwable::printStackTrace);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RxUtils.unsubscribe(fabImageDisposable);
        RxUtils.unsubscribe(parallaxDisposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

//    public void onItemClick(@NonNull Journey journey, int currentPosition, View scrim, View journeyName) {
//        Map<String, View> transitionViews = new WeakHashMap<>();
//        transitionViews.put("scrim",scrim);
//        transitionViews.put("journeyName", journeyName);
//        transitionViews.put("addJourneyFab", addJourneyFab);
//        transitionViews.put("root", root);
//        transitionViews.put("header", header);
//        transitionViews.put("back", back);
////        presenter.toDetailActivity(transitionViews, journey.getId(), currentPosition, layoutManager);
//    }

}
