package olog.dev.leeto.base;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import olog.dev.leeto.model.DisplayableItem;

import static android.support.v7.util.DiffUtil.calculateDiff;
import static android.util.Pair.create;
import static olog.dev.leeto.utility.RxUtils.unsubscribe;

public abstract class BaseAdapter <T> extends RecyclerView.Adapter<DataBoundViewHolder>
        implements DefaultLifecycleObserver {

    private final PublishProcessor<List<DisplayableItem<T>>> publisher = PublishProcessor.create();

    protected List<DisplayableItem<T>> dataSet = new ArrayList<>();

    private Disposable dataSetDisposable;

    public BaseAdapter(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public final DataBoundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        DataBoundViewHolder<ViewDataBinding> vh = new DataBoundViewHolder<>(binding);
        initViewHolderListeners(vh, viewType);
        return vh;
    }

    protected abstract void initViewHolderListeners(
            DataBoundViewHolder viewHolder, int viewType);

    @Override
    public final void onBindViewHolder(DataBoundViewHolder holder, int position) {
        bind(holder.getBinding(), dataSet.get(position), position);
    }

    protected abstract void bind(@NonNull ViewDataBinding binding,
                                 @NonNull DisplayableItem<T> item,
                                 int position);

    @Override
    public final void onBindViewHolder(DataBoundViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public final void onStart(@NonNull LifecycleOwner owner) {
        dataSetDisposable = publisher
                .toSerialized()
                .debounce(50, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.computation())
                .onBackpressureLatest()
                .distinctUntilChanged()
                .map(newDataSet -> create(newDataSet, calculateDiff(new DiffUtil.Callback() {
                    @Override
                    public int getOldListSize() {
                        return dataSet.size();
                    }

                    @Override
                    public int getNewListSize() {
                        return newDataSet.size();
                    }

                    @Override
                    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                        DisplayableItem<T> oldItem = dataSet.get(oldItemPosition);
                        DisplayableItem<T> newItem = newDataSet.get(newItemPosition);
                        return BaseAdapter.this.areItemTheSame(oldItem, newItem);
                    }

                    @Override
                    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                        DisplayableItem<T> oldItem = dataSet.get(oldItemPosition);
                        DisplayableItem<T> newItem = newDataSet.get(newItemPosition);
                        return BaseAdapter.this.areContentTheSame(oldItem, newItem);
                    }
                })))
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
    public final int getItemViewType(int position) {
        return dataSet.get(position).getType();
    }

    @Override
    public final void onStop(@NonNull LifecycleOwner owner) {
        unsubscribe(dataSetDisposable);
    }

    public final void updateData(List<DisplayableItem<T>> dataSet){
        publisher.onNext(new ArrayList<>(dataSet));
    }

    protected void onDataSetUpdatedCallback(){}

    protected abstract boolean areItemTheSame(DisplayableItem<T> oldItem, DisplayableItem<T> newItem);

    protected abstract boolean areContentTheSame(DisplayableItem<T> oldItem, DisplayableItem<T> newItem);

}
