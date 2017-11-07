package olog.dev.leeto.ui._activity_main.list;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import olog.dev.leeto.BR;
import olog.dev.leeto.ImagePicker;
import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseAdapter;
import olog.dev.leeto.base.DataBoundViewHolder;
import olog.dev.leeto.dagger.ActivityContext;
import olog.dev.leeto.dagger.PerActivity;
import olog.dev.leeto.model.DisplayableItem;
import olog.dev.leeto.model.DisplayableJourney;
import olog.dev.leeto.ui._activity_detail.DetailActivity;
import olog.dev.leeto.ui._activity_main.MainActivityViewModel;

@PerActivity
public class JourneyListAdapter extends BaseAdapter<DisplayableItem<DisplayableJourney>> {

    private final Context context;
    private final OnJourneySelected callback;
    private final MainActivityViewModel viewModel;

    @Inject JourneyListAdapter(
            Lifecycle lifecycle,
            @ActivityContext Context context,
            OnJourneySelected callback,
            MainActivityViewModel viewModel){

        super(lifecycle);
        this.context = context;
        this.callback = callback;
        this.viewModel = viewModel;
    }

    @Override
    protected void initViewHolderListeners(DataBoundViewHolder viewHolder, int viewType) {
        if (viewType == R.layout.item_journey){
            viewHolder.itemView.setOnClickListener(view -> itemViewClick(viewHolder));
            viewHolder.itemView.setOnLongClickListener(view -> itemViewLongClick(viewHolder));

            viewHolder.itemView.findViewById(R.id.share).setOnClickListener(view ->
                    shareClickListener(viewHolder));

            viewHolder.itemView.findViewById(R.id.addMedia).setOnClickListener(view ->
                    addMediaClickListener(viewHolder));
        }
    }

    @Override
    protected void bind(@NonNull ViewDataBinding binding, @NonNull DisplayableItem<DisplayableJourney> item, int position) {
        DisplayableJourney model = item.getModel();
        if (model != null){
            binding.setVariable(BR.journey, model);
        }
    }

    private void itemViewClick(RecyclerView.ViewHolder viewHolder){
        int position = viewHolder.getAdapterPosition();
        if (position == RecyclerView.NO_POSITION){
            return;
        }
        DisplayableItem<DisplayableJourney> item = dataSet.get(position);
        if (item.getModel() != null){
            long journeyId = item.getModel().getId();
            View scrim = viewHolder.itemView.findViewById(R.id.scrim);
            View journeyName = viewHolder.itemView.findViewById(R.id.journeyName);
            scrim.setTransitionName(DetailActivity.SHARED_ROOT + journeyId);
            journeyName.setTransitionName(DetailActivity.SHARED_JOURNEY_NAME + journeyId);

            callback.onClick(
                    item.getModel().getId(),
                    position,
                    scrim,
                    journeyName
            );
        }
    }

    private boolean itemViewLongClick(RecyclerView.ViewHolder viewHolder){
        int position = viewHolder.getAdapterPosition();
        if (position == RecyclerView.NO_POSITION){
            return false;
        }

        callback.onLongClick();
        return true;
    }

    private void addMediaClickListener(RecyclerView.ViewHolder viewHolder){
        int position = viewHolder.getAdapterPosition();
        if (position == RecyclerView.NO_POSITION){
            return;
        }

        ImagePicker.multiple(context)
                .takeUntil(RxView.detaches(viewHolder.itemView))
                .subscribe(result -> {

                });
    }

    private void shareClickListener(RecyclerView.ViewHolder viewHolder){
        int position = viewHolder.getAdapterPosition();
        if (position == RecyclerView.NO_POSITION){
            return;
        }

        DisplayableJourney model = dataSet.get(position).getModel();
        if(model != null){
            viewModel.share(context, model.getName());
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

    public interface OnJourneySelected {
        void onClick(long journeyId, int currentPosition, View scrim, View journeyName);
        void onLongClick();
    }

}
