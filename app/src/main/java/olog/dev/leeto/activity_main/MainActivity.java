package olog.dev.leeto.activity_main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseActivity;
import olog.dev.leeto.custom_view.ParallaxRecyclerView;
import olog.dev.leeto.dagger.component.DaggerMainActivityComponent;
import olog.dev.leeto.dagger.component.MainActivityComponent;
import olog.dev.leeto.dagger.module.MainActivityModule;
import olog.dev.leeto.model.pojo.Journey;

public class MainActivity extends BaseActivity implements MainContract.View, JourneyAdapter.Callback {

    private MainActivityComponent component;

    @Inject
    MainContract.Presenter presenter;

    @Inject
    JourneyAdapter adapter;

    @Inject
    LinearLayoutManager layoutManager;

    @BindView(R.id.root) View root;
    @BindView(R.id.list) ParallaxRecyclerView list;
    @BindView(R.id.scrim) View scrim;
    @BindView(R.id.toolbar) View toolbar;
    @BindView(R.id.addJourney) FloatingActionButton addJourney;
    @BindView(R.id.back) ImageView back;

    @OnClick(R.id.addJourney)
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

        ButterKnife.bind(this);

        getLifecycle().addObserver(presenter);
        getLifecycle().addObserver(list);

        list.setViews(scrim, toolbar, addJourney);
        adapter.setCallback(this);
    }

    @Override
    public void updateJourneysList(List<Journey> data) {
        adapter.updateDataSet(data);
    }

    @Override
    public void showDeleteConfirmSnackBar() {
        Snackbar.make(root, "Journey deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", view -> adapter.restoreLastDismissedItem())
                .show();
    }

    public MainActivityComponent getComponent() {
        return component;
    }
}
