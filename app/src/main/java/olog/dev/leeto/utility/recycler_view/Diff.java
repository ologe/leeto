//package olog.dev.leeto.utility.recycler_view;
//
//import android.support.annotation.NonNull;
//import android.support.v7.util.DiffUtil;
//
//import java.util.List;
//
//import olog.dev.leeto.model.DisplayableJourney;
//
//import static olog.dev.leeto.utility.ThreadUtils.assertBackGroundThread;
//
//public class Diff extends DiffUtil.Callback {
//
//    private List<DisplayableJourney> oldList;
//    private List<DisplayableJourney> newList;
//
//    public Diff(@NonNull List<DisplayableJourney> oldList,
//                @NonNull List<DisplayableJourney> newList) {
//        assertBackGroundThread();
//        this.oldList = oldList;
//        this.newList = newList;
//    }
//
//    @Override
//    public int getOldListSize() {
//        return oldList.size();
//    }
//
//    @Override
//    public int getNewListSize() {
//        return newList.size();
//    }
//
//    @Override
//    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//        DisplayableJourney oldItem = oldList.get(oldItemPosition);
//        DisplayableJourney newItem = newList.get(newItemPosition);
//        return oldItem.getId() == newItem.getId();
//    }
//
//    @Override
//    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//        DisplayableJourney oldItem = oldList.get(oldItemPosition);
//        DisplayableJourney newItem = newList.get(newItemPosition);
//        return oldItem.equals(newItem);
//    }
//
//}
