package olog.dev.leeto.ui._activity_main.list;

import android.arch.lifecycle.Lifecycle;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.Lazy;
import olog.dev.leeto.BR;
import olog.dev.leeto.DataBoundViewHolder;
import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseAdapter;
import olog.dev.leeto.dagger.PerActivity;
import olog.dev.leeto.model.DisplayableJourney;
import olog.dev.leeto.ui.navigator.Navigator;

@PerActivity
public class JourneyAdapter extends BaseAdapter<DisplayableJourney> {

    private final Lazy<Navigator> navigator;

    @Inject JourneyAdapter(
            Lifecycle lifecycle,
            Lazy<Navigator> navigator){

        super(lifecycle);
        this.navigator = navigator;
    }

    @Override
    protected void initViewHolderListeners(DataBoundViewHolder viewHolder, int viewType) {
//        this.view.onItemClick(dataSet.get(position), position, holder.scrim, holder.journeyName);
        viewHolder.setOnClickListener(() -> {
        });
    }

    @Override
    protected void bind(@NonNull ViewDataBinding binding, @NonNull DisplayableJourney item, int position) {
        binding.setVariable(BR.journey, item);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_journey;
    }

    @Override
    protected boolean areItemTheSame(DisplayableJourney oldItem, DisplayableJourney newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    protected boolean areContentTheSame(DisplayableJourney oldItem, DisplayableJourney newItem) {
        return oldItem.equals(newItem);
    }
}
