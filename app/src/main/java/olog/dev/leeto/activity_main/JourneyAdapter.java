package olog.dev.leeto.activity_main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import olog.dev.leeto.BR;
import olog.dev.leeto.R;
import olog.dev.leeto.activity_detail.DetailActivity;
import olog.dev.leeto.databinding.ItemJourneyBinding;
import olog.dev.leeto.model.pojo.Journey;
import olog.dev.leeto.utility.recycler_view.JourneyAdapterTouchHelper;

public class JourneyAdapter extends RecyclerView.Adapter<JourneyAdapter.Holder>
    implements JourneyAdapterTouchHelper {

    private List<Journey> dataSet = new ArrayList<>();
    private Pair<Integer, Journey> lastDeleted = null;

    private Callback callback;

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(ItemJourneyBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(dataSet.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public void updateDataSet(List<Journey> dataSet){
        if(dataSet == null) return;

        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public void onItemDismiss(int position) {
        lastDeleted = new Pair<>(position, dataSet.get(position));
        dataSet.remove(dataSet.get(position));
        notifyItemRemoved(position);
        callback.showDeleteConfirmSnackBar();
    }

    public void restoreLastDismissedItem(){
        if(lastDeleted == null) return;
        dataSet.add(lastDeleted.first, lastDeleted.second);
        notifyItemInserted(lastDeleted.first);
        lastDeleted = null;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemJourneyBinding binding;

        public Holder(ItemJourneyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        private void bind(Journey journey, int position){
            binding.scrim.setTransitionName(DetailActivity.SHARED_ROOT + position);
            binding.journeyName.setTransitionName(DetailActivity.SHARED_JOURNEY_NAME + position);
            binding.setVariable(BR.journey, journey);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            int currentPosition = getAdapterPosition();

            Context ctx = view.getContext();

            int above = 1;
            int bottom = 1;

            MainActivity activity = (MainActivity) ctx;
            View decorView = activity.getWindow().getDecorView();

            List<View> transitionViews = new ArrayList<>();

            transitionViews.add(binding.scrim);
            transitionViews.add(binding.journeyName);
            transitionViews.add(activity.binding.addJourney);
            transitionViews.add(activity.binding.root);
            transitionViews.add(activity.binding.scrim);
            transitionViews.add(activity.binding.toolbar);
            transitionViews.add(activity.binding.back);

            LinearLayoutManager layoutManager = activity.getLayoutManager();

            for(int i=layoutManager.findFirstVisibleItemPosition(); i<=layoutManager.findLastVisibleItemPosition();i++){

                View viewHolder = layoutManager.findViewByPosition(i);

                if(i < currentPosition){
                    viewHolder.setTransitionName("above" + above++);
                    transitionViews.add(viewHolder);
                } else if(i > currentPosition){
                    viewHolder.setTransitionName("bottom" + bottom++);
                    transitionViews.add(viewHolder);
                }
            }

            // try to add navigation bar to hero transition
            if(decorView != null){
                View navigationBar = decorView.findViewById(android.R.id.navigationBarBackground);
                if(navigationBar != null){
                    navigationBar.setTransitionName(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
                    transitionViews.add(navigationBar);
                }
            }

            Intent intent = new Intent(ctx, DetailActivity.class);
            intent.putExtra(DetailActivity.BUNDLE_JOURNEY, dataSet.get(currentPosition));
            intent.putExtra(DetailActivity.BUNDLE_POSITION, currentPosition);

            @SuppressWarnings("unchecked")
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)ctx,
                    getPairs(transitionViews));
            ctx.startActivity(intent, options.toBundle());

        }

        private Pair[] getPairs(List<View> transitionViews){
            Pair[] pairs = new Pair[transitionViews.size()];

            for(int i=0;i<transitionViews.size();i++){
                View view = transitionViews.get(i);
                pairs[i] = new Pair<>(view, view.getTransitionName());
            }

            return pairs;
        }

    }

    interface Callback {
        void showDeleteConfirmSnackBar();
    }

}
