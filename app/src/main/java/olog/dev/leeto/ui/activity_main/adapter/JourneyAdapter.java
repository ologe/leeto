package olog.dev.leeto.ui.activity_main.adapter;

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
import olog.dev.leeto.dagger.annotation.PerActivity;
import olog.dev.leeto.model.pojo.Journey;
import olog.dev.leeto.model.pojo.Stop;
import olog.dev.leeto.ui.activity_detail.DetailActivity;
import olog.dev.leeto.ui.activity_main.MainContract;
import olog.dev.leeto.utility.DateUtils;
import olog.dev.leeto.utility.recycler_view.JourneyAdapterTouchHelper;

@PerActivity
public class JourneyAdapter extends RecyclerView.Adapter<JourneyAdapter.Holder> implements JourneyAdapterTouchHelper {

    private MainContract.View view;
    private LayoutInflater inflater;
    private List<Journey> dataSet;

    private Pair<Integer, Journey> lastDeleted = null;

    public JourneyAdapter(MainContract.View view,
                          LayoutInflater inflater){
        this.view = view;
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
        // TODO bug nell'anizione al ritorno dalla detail, probabilemente causto
        // dalla mancanza di id nei pojo
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_journey;
    }

    @Override
    public void onItemDismiss(int position) {
        lastDeleted = new Pair<>(position, dataSet.get(position));
        dataSet.remove(dataSet.get(position));
        notifyItemRemoved(position);
        view.showDeleteSnackBar(R.string.journey_deleted, lastDeleted.second);
    }

    public void restoreLastDismissedItem(){
        if(lastDeleted == null) return;
        dataSet.add(lastDeleted.first, lastDeleted.second);
        notifyItemInserted(lastDeleted.first);
        lastDeleted = null;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.scrim) View scrim;
        @BindView(R.id.journeyName) TextView journeyName;
        @BindView(R.id.journeyDate) TextView journeyDate;
        @BindView(R.id.location) TextView location;
        @BindView(R.id.journeyDescription) TextView journeyDescription;

        public Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
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
        public void onClick(View v) {
            int pos = getAdapterPosition();
            view.onItemClick(dataSet.get(pos), pos, scrim, journeyName);
        }

    }

}
