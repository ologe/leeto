package olog.dev.leeto.utility.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;

import static olog.dev.leeto.utility.Precondition.checkNonNegative;
import static olog.dev.leeto.utility.Precondition.checkNotNull;

public class DiffCallback implements ListUpdateCallback {

    private final int headers;
    private final RecyclerView.Adapter adapter;

    public DiffCallback(int headers, @NonNull RecyclerView.Adapter adapter) {
        this.headers = checkNonNegative(headers);
        this.adapter = checkNotNull(adapter);
    }

    @Override
    public void onInserted(int position, int count) {
        position = position + headers;
        adapter.notifyItemRangeInserted(position, count);
    }

    @Override
    public void onRemoved(int position, int count) {
        position = position + headers;
        adapter.notifyItemRangeRemoved(position, count);
    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {
        fromPosition = fromPosition + headers;
        toPosition = toPosition + headers;
        adapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onChanged(int position, int count, Object payload) {
        position = position + headers;
        adapter.notifyItemRangeChanged(position, count, payload);
    }
}
