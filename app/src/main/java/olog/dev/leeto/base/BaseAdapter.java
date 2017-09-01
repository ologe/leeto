package olog.dev.leeto.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.processors.BehaviorProcessor;
import olog.dev.leeto.data.model.HasId;
import olog.dev.leeto.utility.reactive.BaseSchedulersProvider;
import olog.dev.leeto.utility.recycler_view.Diff;
import olog.dev.leeto.utility.recycler_view.DiffCallback;

import static android.support.v7.util.DiffUtil.calculateDiff;
import static android.util.Pair.create;
import static olog.dev.leeto.utility.RxUtils.unsubscribe;

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, Model extends HasId> extends RecyclerView.Adapter<VH>
        implements LifecycleObserver {

    private static final int DEFAULT_DEBOUNCE = 50;

    protected List<Model> dataSet;
    private final int DEBOUNCE;

    private final BehaviorProcessor<List<Model>> publisher;
    private Disposable dataSetDisposable;
    protected final BaseSchedulersProvider schedulers;
    protected final int headers;

    public BaseAdapter(List<Model> dataSet,
                       BaseSchedulersProvider schedulers,
                       Lifecycle lifecycle,
                       int headers){
        this(dataSet, schedulers, lifecycle, headers, DEFAULT_DEBOUNCE);
    }

    public BaseAdapter(List<Model> dataSet,
                       BaseSchedulersProvider schedulers,
                       Lifecycle lifecycle,
                       int headers,
                       int debounce) {
        this.dataSet = dataSet;
        this.schedulers = schedulers;
        this.headers = headers;
        this.DEBOUNCE = debounce;

        publisher = BehaviorProcessor.create();
        lifecycle.addObserver(this);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position >= headers ? dataSet.get(position - headers).getId() : (position - headers);
    }

    @Override
    public int getItemCount() {
        return dataSet.size() + headers;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start(){
        dataSetDisposable = publisher
                .onBackpressureLatest()
                .debounce(DEBOUNCE, TimeUnit.MILLISECONDS)
                .map(newDataSet -> create(newDataSet, calculateDiff(new Diff<>(this.dataSet, newDataSet))))
                .subscribeOn(schedulers.computation())
                .observeOn(schedulers.mainThread())
                .subscribe(result -> {
                    List<Model> oldList = new ArrayList<>(this.dataSet);
                    this.dataSet = result.first;
                    result.second.dispatchUpdatesTo(new DiffCallback(headers, this));
                    onDataSetUpdatedCallback(oldList, result.first);
                });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop(){
        unsubscribe(dataSetDisposable);
    }

    protected void updateData(List<Model> dataSet){
        publisher.onNext(dataSet);
    }

    protected void onDataSetUpdatedCallback(List<Model> oldDataSet,
                                            List<Model> newDataSet){}

}
