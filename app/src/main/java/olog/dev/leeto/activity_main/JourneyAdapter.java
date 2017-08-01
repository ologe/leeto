package olog.dev.leeto.activity_main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import olog.dev.leeto.R;
import olog.dev.leeto.activity_detail.DetailActivity;
import olog.dev.leeto.dagger.annotation.PerActivity;
import olog.dev.leeto.model.pojo.Journey;
import olog.dev.leeto.model.pojo.Stop;
import olog.dev.leeto.utility.DateUtils;

@PerActivity
public class JourneyAdapter extends RecyclerView.Adapter<JourneyAdapter.Holder>
    /*implements JourneyAdapterTouchHelper*/ {

    private List<Journey> dataSet;
    private Pair<Integer, Journey> lastDeleted = null;

//    private Callback callback;
//    public void setCallback(Callback callback){
//        this.callback = callback;
//    }

    private LayoutInflater inflater;

    public JourneyAdapter(LayoutInflater inflater){
        this.inflater = inflater;
        this.dataSet = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public void updateDataSet(@NonNull List<Journey> dataSet){
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_journey;
    }

    //    @Override
//    public void onItemDismiss(int position) {
//        lastDeleted = new Pair<>(position, dataSet.get(position));
//        dataSet.remove(dataSet.get(position));
//        notifyItemRemoved(position);
//        callback.showDeleteConfirmSnackBar();
//    }

//    public void restoreLastDismissedItem(){
//        if(lastDeleted == null) return;
//        dataSet.add(lastDeleted.first, lastDeleted.second);
//        notifyItemInserted(lastDeleted.first);
//        lastDeleted = null;
//    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.scrim) View scrim;
        @BindView(R.id.journeyName) TextView journeyName;
        @BindView(R.id.journeyDate) TextView journeyDate;
        @BindView(R.id.location) TextView location;
        @BindView(R.id.journeyDescription) TextView journeyDescription;

        public Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
//            view.setOnClickListener(this);
        }

        private void bind(int position){
            Journey journey = dataSet.get(position);
            Stop firstStop = journey.getFirstStop();
            String date = DateUtils.toString(firstStop.getDate());

            journeyName.setText(journey.getName());
            journeyDate.setText(date);
            location.setText(firstStop.getLocation().getName());
            journeyDescription.setText(journey.getShortDescription());

            scrim.setTransitionName(DetailActivity.SHARED_ROOT + position);
            journeyName.setTransitionName(DetailActivity.SHARED_JOURNEY_NAME + position);
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

//            transitionViews.add(binding.scrim);
//            transitionViews.add(binding.journeyName);
//            transitionViews.add(activity.binding.addJourney);
//            transitionViews.add(activity.binding.root);
//            transitionViews.add(activity.binding.scrim);
//            transitionViews.add(activity.binding.toolbar);
//            transitionViews.add(activity.binding.back);

//            LinearLayoutManager layoutManager = activity.getLayoutManager();

//            for(int i=layoutManager.findFirstVisibleItemPosition(); i<=layoutManager.findLastVisibleItemPosition();i++){
//
//                View viewHolder = layoutManager.findViewByPosition(i);
//
//                if(i < currentPosition){
//                    viewHolder.setTransitionName("above" + above++);
//                    transitionViews.add(viewHolder);
//                } else if(i > currentPosition){
//                    viewHolder.setTransitionName("bottom" + bottom++);
//                    transitionViews.add(viewHolder);
//                }
//            }
//
//            // try to add navigation bar to hero transition
//            if(decorView != null){
//                View navigationBar = decorView.findViewById(android.R.id.navigationBarBackground);
//                if(navigationBar != null){
//                    navigationBar.setTransitionName(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
//                    transitionViews.add(navigationBar);
//                }
//            }
//
//            Intent intent = new Intent(ctx, DetailActivity.class);
//            intent.putExtra(DetailActivity.BUNDLE_JOURNEY, dataSet.get(currentPosition));
//            intent.putExtra(DetailActivity.BUNDLE_POSITION, currentPosition);
//
//            @SuppressWarnings("unchecked")
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)ctx,
//                    getPairs(transitionViews));
//            ctx.startActivity(intent, options.toBundle());

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
