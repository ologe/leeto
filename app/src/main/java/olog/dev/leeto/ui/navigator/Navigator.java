package olog.dev.leeto.ui.navigator;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Pair;
import android.view.View;
import android.view.Window;

import java.util.Map;

import javax.inject.Inject;

import olog.dev.leeto.R;
import olog.dev.leeto.dagger.PerActivity;
import olog.dev.leeto.ui.activity_add_journey.AddJourneyActivity;
import olog.dev.leeto.ui.fragment_no_journey.JourneyEmptyStateFragment;

@PerActivity
public class Navigator {

    private final AppCompatActivity activity;

    @Inject
    Navigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void toDetailActivity(@NonNull Map<String, View> views, long journeyId, int currentPosition, @NonNull LinearLayoutManager layoutManager) {
        int above = 1;
        int bottom = 1;

        int firstVisible = layoutManager.findFirstVisibleItemPosition();
        int lastVisible = layoutManager.findLastVisibleItemPosition();

        for(int i=firstVisible; i<=lastVisible; i++){

            View viewHolder = layoutManager.findViewByPosition(i);

            if(i < currentPosition){
                viewHolder.setTransitionName("above" + above);
                views.put("above" + above, viewHolder);
                above++;
            } else if(i > currentPosition){
                viewHolder.setTransitionName("bottom" + bottom);
                views.put("bottom" + bottom, viewHolder);
                bottom++;
            }
        }

        // try to add navigation bar to hero transition
        View decorView = activity.getWindow().getDecorView();
        if(decorView != null){
            View navigationBar = decorView.findViewById(android.R.id.navigationBarBackground);
            if(navigationBar != null){
                navigationBar.setTransitionName(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
                views.put("navigationBar", navigationBar);
            }
        }

//        Intent intent = new Intent(activity, DetailActivity.class);
//        intent.putExtra(DetailActivity.BUNDLE_JOURNEY_ID, journeyId);
//        intent.putExtra(DetailActivity.BUNDLE_POSITION, currentPosition);

        @SuppressWarnings("unchecked")
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, createPairs(views));

//        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public void toAddJourneyActivity(@NonNull FloatingActionButton fab) {
        Intent intent = new Intent(activity, AddJourneyActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                activity, fab, fab.getTransitionName());
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public void toAddStopActivity(@NonNull FloatingActionButton fab) {
//        Intent intent = new Intent(activity, AddStopActivity.class);
//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
//                activity, fab, fab.getTransitionName());
//        activity.startActivity(intent, options.toBundle());
    }

    public void showJourneyEmptyState(){
        if (findFragmentByTag(JourneyEmptyStateFragment.TAG) != null){
            return;
        }

        JourneyEmptyStateFragment fragment = new JourneyEmptyStateFragment();

        activity.getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.root, fragment, JourneyEmptyStateFragment.TAG)
                .commitAllowingStateLoss();
    }

    public void hideJourneyEmptyState(){
        Fragment fragment = findFragmentByTag(JourneyEmptyStateFragment.TAG);
        if (fragment == null){
            return;
        }

        activity.getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commitAllowingStateLoss();
    }

    public void closeActivity() {
        activity.finishAfterTransition();
    }

    public void removeFragment(@NonNull String TAG) {
        Fragment fragment = findFragmentByTag(JourneyEmptyStateFragment.TAG);
        if(fragment != null){
            activity.getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .remove(fragment)
                    .commitAllowingStateLoss();
        }
    }

    private Pair[] createPairs(Map<String, View> views){
        Pair[] pairs = new Pair[views.size()];

        int i = 0;

        for (Map.Entry<String, View> pair : views.entrySet()) {
            View view = pair.getValue();
            pairs[i] = Pair.create(view, view.getTransitionName());
            pairs[i] = new Pair<>(view, view.getTransitionName());
            i++;
        }

        return pairs;
    }

    @Nullable
    private  <T extends Fragment> T findFragmentByTag(@NonNull String tag){
        //noinspection unchecked
        return (T) activity.getSupportFragmentManager().findFragmentByTag(tag);
    }

}
