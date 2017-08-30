package olog.dev.leeto.ui._activity_main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import olog.dev.leeto.ui.fragment_no_journey.NoJourneyFragment;

public class MainActivity extends BaseActivity implements MainContract.View {

    @Inject MainContract.Presenter presenter;
    @Inject JourneyAdapter adapter;
    @Inject LinearLayoutManager layoutManager;

    @BindView(R.id.root) View root;
    @BindView(R.id.list) ParallaxRecyclerView list;
    @BindView(R.id.scrim) View scrim;
    @BindView(R.id.toolbar) View toolbar;
    @BindView(R.id.addJourneyFab) FloatingActionButton addJourney;
    @BindView(R.id.back) ImageView back;

    private Unbinder unbinder;

    @OnClick(R.id.addJourneyFab)
    public void addJourney(View view){
        presenter.onFabClick((FloatingActionButton) view, list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        getLifecycle().addObserver(list);

        list.setAdapter(adapter);
        list.setLayoutManager(layoutManager);
        list.setViews(scrim, toolbar, addJourney);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showJourneysList(@NonNull List<Journey> data) {
        Fragment fragment = findFragmentByTag(NoJourneyFragment.TAG);
        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .remove(fragment)
                    .commitAllowingStateLoss();
        }
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

    @Override
    public void scrollToPosition(int position) {
        list.smoothScrollToPosition(0);
    }
}
