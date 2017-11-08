package olog.dev.leeto.ui.fragment_journeys_stops;

import android.arch.lifecycle.Lifecycle;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;

import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseAdapter;
import olog.dev.leeto.base.DataBoundViewHolder;
import olog.dev.leeto.model.DisplayableItem;

public class JourneyStopsAdapter extends BaseAdapter<Integer> {

    public JourneyStopsAdapter(Lifecycle lifecycle) {
        super(lifecycle);

        for (int i = 0; i < 5; i++) {
            this.dataSet.add(new DisplayableItem<>(R.layout.item_stop, 0));
        }
    }

    @Override
    protected void initViewHolderListeners(DataBoundViewHolder viewHolder, int viewType) {

    }

    @Override
    protected void bind(@NonNull ViewDataBinding binding, @NonNull DisplayableItem<Integer> item, int position) {

    }

    @Override
    protected boolean areItemTheSame(DisplayableItem<Integer> oldItem, DisplayableItem<Integer> newItem) {
        return false;
    }

    @Override
    protected boolean areContentTheSame(DisplayableItem<Integer> oldItem, DisplayableItem<Integer> newItem) {
        return false;
    }
}
