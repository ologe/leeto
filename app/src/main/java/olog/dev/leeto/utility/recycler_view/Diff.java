package olog.dev.leeto.utility.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import java.util.List;

import olog.dev.leeto.data.model.HasId;

import static olog.dev.leeto.utility.Precondition.checkNotNull;
import static olog.dev.leeto.utility.ThreadUtils.assertBackGroundThread;

public class Diff<T extends HasId> extends DiffUtil.Callback {

    private List<T> oldList;
    private List<T> newList;

    public Diff(@NonNull List<T> oldList, @NonNull List<T> newList) {
        assertBackGroundThread();
        this.oldList = checkNotNull(oldList);
        this.newList = checkNotNull(newList);
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

}
