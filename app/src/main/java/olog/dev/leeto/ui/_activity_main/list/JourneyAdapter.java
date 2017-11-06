package olog.dev.leeto.ui._activity_main.list;

import android.arch.lifecycle.Lifecycle;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import olog.dev.leeto.BR;
import olog.dev.leeto.base.BaseAdapter;
import olog.dev.leeto.base.DataBoundViewHolder;
import olog.dev.leeto.dagger.PerActivity;
import olog.dev.leeto.model.DisplayableItem;
import olog.dev.leeto.model.DisplayableJourney;

@PerActivity
public class JourneyAdapter extends BaseAdapter<DisplayableItem<DisplayableJourney>> {

    @Inject JourneyAdapter(Lifecycle lifecycle){

        super(lifecycle);
    }

    @Override
    protected void initViewHolderListeners(DataBoundViewHolder viewHolder, int viewType) {
//        this.view.onItemClick(dataSet.get(position), position, holder.scrim, holder.journeyName);
        viewHolder.setOnClickListener(() -> {
        });
    }

    @Override
    protected void bind(@NonNull ViewDataBinding binding, @NonNull DisplayableItem<DisplayableJourney> item, int position) {
        DisplayableJourney model = item.getModel();
        if (model != null){
            binding.setVariable(BR.journey, model);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position).getType();
    }

    @Override
    protected boolean areItemTheSame(DisplayableItem<DisplayableJourney> oldItem, DisplayableItem<DisplayableJourney> newItem) {
        int oldType = oldItem.getType();
        int newType = newItem.getType();
        DisplayableJourney oldModel = oldItem.getModel();
        DisplayableJourney newModel = newItem.getModel();

        return oldType == newType &&
                oldModel != null && newModel != null &&
                oldModel.getId() == newModel.getId();
    }

    @Override
    protected boolean areContentTheSame(DisplayableItem<DisplayableJourney> oldItem, DisplayableItem<DisplayableJourney> newItem) {
        int oldType = oldItem.getType();
        int newType = newItem.getType();
        DisplayableJourney oldModel = oldItem.getModel();
        DisplayableJourney newModel = newItem.getModel();

        return oldType == newType &&
                oldModel != null && newModel != null &&
                oldModel.equals(newModel);
    }
}
