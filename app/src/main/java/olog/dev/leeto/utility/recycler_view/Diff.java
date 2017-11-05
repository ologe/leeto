package olog.dev.leeto.utility.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import java.util.List;

import olog.dev.leeto.DisplayableItem;

import static olog.dev.leeto.utility.ThreadUtils.assertBackGroundThread;

public class Diff extends DiffUtil.Callback {

    private List<DisplayableItem> oldList;
    private List<DisplayableItem> newList;

    public Diff(@NonNull List<DisplayableItem> oldList,
                @NonNull List<DisplayableItem> newList) {
        assertBackGroundThread();
        this.oldList = oldList;
        this.newList = newList;
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
        DisplayableItem oldItem = oldList.get(oldItemPosition);
        DisplayableItem newItem = newList.get(newItemPosition);
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        DisplayableItem oldItem = oldList.get(oldItemPosition);
        DisplayableItem newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }

}
