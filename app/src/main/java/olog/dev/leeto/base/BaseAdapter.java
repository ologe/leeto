package olog.dev.leeto.base;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import olog.dev.leeto.DataBoundViewHolder;
import olog.dev.leeto.DisplayableItem;
import olog.dev.leeto.utility.recycler_view.Diff;

import static android.support.v7.util.DiffUtil.calculateDiff;
import static android.util.Pair.create;
import static olog.dev.leeto.utility.RxUtils.unsubscribe;

public abstract class BaseAdapter extends RecyclerView.Adapter<DataBoundViewHolder>
        implements DefaultLifecycleObserver {

    private final PublishProcessor<List<DisplayableItem>> publisher = PublishProcessor.create();

    protected List<DisplayableItem> dataSet = new Vector<>();

    private Disposable dataSetDisposable;

    public BaseAdapter(Lifecycle lifecycle) {

        lifecycle.addObserver(this);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }



    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        dataSetDisposable = publisher
                .toSerialized()
                .debounce(50, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.computation())
                .onBackpressureLatest()
                .distinctUntilChanged()
                .map(newDataSet -> create(newDataSet, calculateDiff(new Diff(this.dataSet, newDataSet))))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    boolean wasEmpty = this.dataSet.isEmpty();
                    this.dataSet = result.first;
                    if (wasEmpty){
                        notifyDataSetChanged();
                    } else {
                        result.second.dispatchUpdatesTo(this);
                    }

                    onDataSetUpdatedCallback();

                }, Throwable::printStackTrace);
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        unsubscribe(dataSetDisposable);
    }

    protected void updateData(List<DisplayableItem> dataSet){
        publisher.onNext(new ArrayList<>(dataSet));
    }

    protected void onDataSetUpdatedCallback(){}

}
