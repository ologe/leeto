package olog.dev.leeto.ui.activity_main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseActivity;
import olog.dev.leeto.dagger.component.DaggerMainActivityComponent;
import olog.dev.leeto.dagger.component.MainActivityComponent;
import olog.dev.leeto.dagger.module.MainActivityModule;
import olog.dev.leeto.model.pojo.Journey;
import olog.dev.leeto.ui.activity_detail.DetailActivity;
import olog.dev.leeto.ui.activity_main.adapter.JourneyAdapter;
import olog.dev.leeto.ui.activity_main.view.ParallaxRecyclerView;
import olog.dev.leeto.ui.fragment_no_journey.NoJourneyFragment;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainContract.View {

    private MainActivityComponent component;

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
        // init dagger before onCreate

        component = DaggerMainActivityComponent.builder()
                .appComponent(getAppComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build();

        component.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        getLifecycle().addObserver(presenter);
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
        Timber.d("showJourneysList");

        Fragment fragment = findFragmentByTag(NoJourneyFragment.TAG);
        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .remove(fragment)
                    .commitAllowingStateLoss();
        }
        adapter.updateDataSet(data);
    }

    @Override
    public void showNoJourneysFragment() {
        Timber.d("showNoJourneysFragment");
        if(findFragmentByTag(NoJourneyFragment.TAG) == null){
            NoJourneyFragment.newInstance(this);
        }
    }

    @Override
    public void showDeleteSnackBar(@StringRes int id, @NonNull Journey itemToDelete) {
        Snackbar.make(root, getString(id), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.undo), view -> adapter.restoreLastDismissedItem())
                .setActionTextColor(Color.WHITE)
                .addCallback(new Snackbar.Callback(){
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        Timber.d("onDismissed event " + event);
                        if(event != DISMISS_EVENT_ACTION){
                            presenter.deleteJourney(itemToDelete);
                            // TODO bug, non si aggiorna l'animazione della lista
                        }
                    }
                })
                .show();
    }

    /**
     * setup detail activity animation
     */
    @Override
    public void onItemClick(@NonNull Journey journey, int currentPosition, View scrim, View journeyName) {
        int above = 1;
        int bottom = 1;

        List<View> transitionViews = new ArrayList<>();
        transitionViews.add(scrim);
        transitionViews.add(journeyName);
        transitionViews.add(addJourney);
        transitionViews.add(root);
        transitionViews.add(toolbar);
        transitionViews.add(back);

        int firstVisible = layoutManager.findFirstVisibleItemPosition();
        int lastVisible = layoutManager.findLastVisibleItemPosition();

        for(int i=firstVisible; i<=lastVisible; i++){

            View viewHolder = layoutManager.findViewByPosition(i);

            if(i < currentPosition){
                viewHolder.setTransitionName("above" + above++);
                transitionViews.add(viewHolder);
            } else if(i > currentPosition){
                viewHolder.setTransitionName("bottom" + bottom++);
                transitionViews.add(viewHolder);
            }
        }

        // try to add navigation bar to hero transition
        View decorView = getWindow().getDecorView();
        if(decorView != null){
            View navigationBar = decorView.findViewById(android.R.id.navigationBarBackground);
            if(navigationBar != null){
                navigationBar.setTransitionName(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
                transitionViews.add(navigationBar);
            }
        }

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.BUNDLE_JOURNEY, journey);
        intent.putExtra(DetailActivity.BUNDLE_POSITION, currentPosition);

        @SuppressWarnings("unchecked")
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, getPairs(transitionViews));

        transitionViews.clear();

        startActivity(intent, options.toBundle());
    }

    private Pair[] getPairs(List<View> transitionViews){
        Pair[] pairs = new Pair[transitionViews.size()];

        for(int i=0;i<transitionViews.size();i++){
            View view = transitionViews.get(i);
            pairs[i] = new Pair<>(view, view.getTransitionName());
        }

        return pairs;
    }

    public MainActivityComponent getComponent() {
        return component;
    }

}
