package olog.dev.leeto.ui._activity_main.list;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.jakewharton.rxbinding2.view.RxView;
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import olog.dev.leeto.BR;
import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseAdapter;
import olog.dev.leeto.base.DataBoundViewHolder;
import olog.dev.leeto.dagger.ActivityContext;
import olog.dev.leeto.dagger.PerActivity;
import olog.dev.leeto.model.DisplayableItem;
import olog.dev.leeto.model.DisplayableJourney;
import olog.dev.leeto.ui._activity_main.MainActivityViewModel;

@PerActivity
public class JourneyAdapter extends BaseAdapter<DisplayableItem<DisplayableJourney>> {

    private final Context context;
    private final MainActivityViewModel viewModel;

    @Inject JourneyAdapter(
            Lifecycle lifecycle,
            @ActivityContext Context context,
            MainActivityViewModel viewModel){

        super(lifecycle);
        this.context = context;
        this.viewModel = viewModel;
    }

    @Override
    protected void initViewHolderListeners(DataBoundViewHolder viewHolder, int viewType) {
//        this.view.onItemClick(dataSet.get(position), position, holder.scrim, holder.journeyName);
        viewHolder.setOnClickListener(() -> {
        });
        viewHolder.itemView.findViewById(R.id.share).setOnClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            if (RecyclerView.NO_POSITION != position){
                DisplayableJourney model = dataSet.get(position).getModel();
                if(model != null){
                    viewModel.share(context, model.getName());
                }
            }
        });
        viewHolder.itemView.findViewById(R.id.addMedia).setOnClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            if (RecyclerView.NO_POSITION != position){
                RxPaparazzo.multiple((Activity) context)
                        .usingGallery()
                        .takeUntil(RxView.detaches(viewHolder.itemView))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(activityListResponse -> {

                        });
            }
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
