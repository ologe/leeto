package olog.dev.leeto.activity_main.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;

import javax.inject.Inject;

import olog.dev.leeto.R;
import olog.dev.leeto.activity_main.MainActivity;
import olog.dev.leeto.activity_main.adapter.JourneyAdapter;
import olog.dev.leeto.utility.DimensionUtils;
import olog.dev.leeto.utility.recycler_view.DragCallback;

public class ParallaxRecyclerView extends RecyclerView implements LifecycleObserver {

    private boolean isFabAdd = true;
    private static final int PIVOT = 200; // 200 == 1f(max alpha) - 1/200 (dy/200)

    private View scrim;
    private View toolbar;
    private FloatingActionButton fab;

    @Inject
    JourneyAdapter adapter;

    @Inject
    LinearLayoutManager layoutManager;

    private CoordinatorLayout.LayoutParams scrimParams = null;

    private int topMargin;

    public ParallaxRecyclerView(Context context) {
        this(context, null);
    }

    public ParallaxRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ParallaxRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // starting scrim accent top margin
        if(!isInEditMode()){
            init(context);
        }
    }

    public void init(@NonNull Context context){
        ((MainActivity) getContext())
                .getComponent()
                .inject(this);

        topMargin = DimensionUtils.dip(context, 125);

        // swipe
        ItemTouchHelper helper = new ItemTouchHelper(new DragCallback(adapter));
        helper.attachToRecyclerView(this);

    }

    public void setViews(View scrim, View toolbar, FloatingActionButton fab){
        this.scrim = scrim;
        this.toolbar = toolbar;
        this.fab = fab;

        scrimParams = (CoordinatorLayout.LayoutParams) scrim.getLayoutParams();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        addOnScrollListener(onScrollListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        removeOnScrollListener(onScrollListener);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        private int totalDy = 0;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            // after deleting an item, animation became buggy, so restore totalDy when
            // the list reach start

            if(!recyclerView.canScrollVertically(-1)){
                totalDy = 0;
            } else totalDy += dy;

            handleFabDrawable(recyclerView, totalDy);

            handleScrimParallax(totalDy, dy);
        }
    };

    private void handleFabDrawable(RecyclerView recyclerView, int totalDy){
        if(!recyclerView.canScrollVertically(-1)){
            // can't scroll up
            fab.setImageResource(R.drawable.vd_add);
            isFabAdd = true;
        }

        if(totalDy != 0){
            if(isFabAdd){
                isFabAdd = false;
                fab.setImageResource(R.drawable.vd_key_arrow_up);
            }
        }
    }

    private void handleScrimParallax(int totalDy, int dy){

        if(dy > 0){
            // moving up
            scrimParams.topMargin = Math.max(0, scrimParams.topMargin - dy/3);
            scrim.setLayoutParams(scrimParams);
            toolbar.setAlpha(Math.max(0f, toolbar.getAlpha() - (float)dy/200));
        } else {
            // moving down
            int firstVisible = layoutManager.findFirstCompletelyVisibleItemPosition();

            if(firstVisible <= 3){
                scrimParams.topMargin = Math.min(topMargin, scrimParams.topMargin - dy/3);
                scrim.setLayoutParams(scrimParams);
            }

            if(firstVisible <= 1 && totalDy <= PIVOT){
                toolbar.setAlpha(Math.min(.85f, toolbar.getAlpha() - (float)dy/200));
            }
        }
    }

    public boolean isFabAdd() {
        return isFabAdd;
    }
}