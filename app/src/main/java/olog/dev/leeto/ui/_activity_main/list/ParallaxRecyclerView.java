package olog.dev.leeto.ui._activity_main.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.jakewharton.rxbinding2.view.RxView;

import olog.dev.leeto.R;
import olog.dev.leeto.utility.DimensionUtils;

public class ParallaxRecyclerView extends RecyclerView {

    private boolean isFabAdd = true;
    private static final int PIVOT = 200; // 200 == 1f(max alpha) - 1/200 (dy/200)
    private static final float ALPHA_MAX = 1f;

    private View scrim;
    private View toolbar;
    private FloatingActionButton fab;

    private CoordinatorLayout.LayoutParams scrimParams = null;

    private int topMargin;

    public ParallaxRecyclerView(Context context) {
        this(context, null, 0);
    }

    public ParallaxRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ParallaxRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // starting scrim accent top margin
        init(context);
    }

    public void init(@NonNull Context context){
        topMargin = DimensionUtils.dip(context, 125);
    }

    public void setViews(View scrim, View toolbar, FloatingActionButton fab){
        this.scrim = scrim;
        this.toolbar = toolbar;
        this.fab = fab;

        scrimParams = (CoordinatorLayout.LayoutParams) scrim.getLayoutParams();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        RxRecyclerView.scrollEvents(this)
                .takeUntil(RxView.detaches(this))
                .map(RecyclerViewScrollEvent::dy)
                .filter(integer -> getLayoutManager().findFirstVisibleItemPosition() == 0)
                .subscribe(dy -> {
                    scrimParams.topMargin = Math.max(0, scrimParams.topMargin - dy/3);
                    scrim.setLayoutParams(scrimParams);
                    toolbar.setAlpha(Math.max(0f, toolbar.getAlpha() - (float)dy/200));
                }, Throwable::printStackTrace);

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
                fab.setImageResource(R.drawable.vd_arrow_up);
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
            int firstVisible = getLayoutManager().findFirstCompletelyVisibleItemPosition();

            if(firstVisible <= 3){
                scrimParams.topMargin = Math.min(topMargin, scrimParams.topMargin - dy/3);
                scrim.setLayoutParams(scrimParams);
            }

            if(firstVisible <= 1 && totalDy <= PIVOT){
                toolbar.setAlpha(Math.min(ALPHA_MAX, toolbar.getAlpha() - (float)dy/200));
            }
        }
    }

    public boolean isFabAdd() {
        return isFabAdd;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return (LinearLayoutManager) super.getLayoutManager();
    }

    @Override
    public JourneyAdapter getAdapter() {
        return (JourneyAdapter) super.getAdapter();
    }
}
