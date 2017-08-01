package olog.dev.leeto.utility.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import olog.dev.leeto.activity_main.JourneyAdapter;

public class DragCallback extends ItemTouchHelper.SimpleCallback{

    private JourneyAdapter adapter;

    public DragCallback(JourneyAdapter adapter, int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }


}
