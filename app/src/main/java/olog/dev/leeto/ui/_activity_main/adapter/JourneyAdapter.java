package olog.dev.leeto.ui._activity_main.adapter;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseAdapter;
import olog.dev.leeto.data.model.Journey;
import olog.dev.leeto.ui._activity_main.MainContract;
import olog.dev.leeto.utility.dagger.annotations.scope.PerActivity;
import olog.dev.leeto.utility.reactive.BaseSchedulersProvider;

@PerActivity
public class JourneyAdapter extends BaseAdapter<JourneyHolder, Journey> /*implements JourneyAdapterTouchHelper*/ {

    private static final int HEADERS = 0;

    private final MainContract.View view;
    private final LayoutInflater inflater;

//    private Pair<Integer, Journey> lastDeleted = null;

    @Inject
    public JourneyAdapter(Lifecycle lifecycle,
                          BaseSchedulersProvider schedulers,
                          MainContract.View view,
                          LayoutInflater inflater){
        super(Collections.emptyList(), lifecycle, schedulers, HEADERS);
        this.view = view;
        this.inflater = inflater;
    }

    @Override
    public JourneyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(viewType, parent, false);
        JourneyHolder holder = new JourneyHolder(view);

        view.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            if(position == RecyclerView.NO_POSITION) return;

            this.view.onItemClick(dataSet.get(position), position, holder.scrim, holder.journeyName);
        });

        return new JourneyHolder(view);
    }

    @Override
    public void onBindViewHolder(JourneyHolder holder, int position) {
        holder.bind(dataSet.get(position), position);
    }

    public void updateDataSet(@NonNull List<Journey> dataSet){
        super.updateData(dataSet);
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
//        view.showDeleteSnackBar(R.string.journey_deleted, lastDeleted.second);
//    }

//    public void restoreLastDismissedItem(){
//        if(lastDeleted == null) return;
//        dataSet.add(lastDeleted.first, lastDeleted.second);
//        notifyItemInserted(lastDeleted.first);
//        lastDeleted = null;
//    }

}
