package olog.dev.leeto.ui._activity_main.list;

import android.arch.lifecycle.Lifecycle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import olog.dev.leeto.DataBoundViewHolder;
import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseAdapter;
import olog.dev.leeto.ui._activity_main.MainContract;
import olog.dev.leeto.utility.dagger.annotations.scope.PerActivity;

@PerActivity
public class JourneyAdapter extends BaseAdapter {

    private final MainContract.View view;
    private final LayoutInflater inflater;

    @Inject
    JourneyAdapter(Lifecycle lifecycle,
                   MainContract.View view,
                   LayoutInflater inflater){
        super(lifecycle);
        this.view = view;
        this.inflater = inflater;
    }

    @Override
    public void onBindViewHolder(DataBoundViewHolder holder, int position) {

    }

    @Override
    public DataBoundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    //    @Override
//    public JourneyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = inflater.inflate(viewType, parent, false);
//        JourneyHolder holder = new JourneyHolder(view);
//
//        view.setOnClickListener(v -> {
//            int position = holder.getAdapterPosition();
//            if(position == RecyclerView.NO_POSITION) return;
//
//            this.view.onItemClick(dataSet.get(position), position, holder.scrim, holder.journeyName);
//        });
//
//        return holder;
//    }

//    @Override
//    public void onBindViewHolder(JourneyHolder holder, int position) {
//        holder.bind(dataSet.get(position), position);
//    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_journey;
    }

}
