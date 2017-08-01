package olog.dev.leeto.activity_main;

import android.app.Activity;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import olog.dev.leeto.R;
import olog.dev.leeto.databinding.ActivityMainBinding;
import olog.dev.leeto.model.pojo.Journey;

public class MainActivity extends AppCompatActivity
        implements MainActivityContract.View, LifecycleRegistryOwner, JourneyAdapter.Callback {

    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    public ActivityMainBinding binding;
    private MainActivityContract.Presenter presenter;


    public static int RC_ADD_JOURNEY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.list.attachLifecycle(getLifecycle());
        binding.list.setViews(binding.scrim, binding.toolbar, binding.addJourney);
        binding.list.getAdapter().setCallback(this);

        presenter = new MainActivityPresenter(this,this, getLifecycle());
        binding.setPresenter(presenter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_ADD_JOURNEY && resultCode == Activity.RESULT_OK){
            // reload list from repository
            presenter.loadJourneysList(this);
        }
    }

    @Override
    public void updateJourneysList(List<Journey> data) {
        getAdapter().updateDataSet(data);
    }

    public LinearLayoutManager getLayoutManager(){
        return binding.list.getLayoutManager();
    }

    public JourneyAdapter getAdapter(){
        return binding.list.getAdapter();
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    public void showDeleteConfirmSnackBar() {
        Snackbar.make(binding.root, "Journey deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", view -> binding.list.getAdapter().restoreLastDismissedItem())
                .show();
    }
}
