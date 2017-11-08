package olog.dev.leeto.ui.fragment_journeys_media;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import olog.dev.leeto.R;
import olog.dev.leeto.base.BaseAdapter;
import olog.dev.leeto.base.DataBoundViewHolder;
import olog.dev.leeto.databinding.ItemMediaBinding;
import olog.dev.leeto.model.DisplayableItem;

public class JourneyMediaAdapter extends BaseAdapter<Integer> {

    public JourneyMediaAdapter(Lifecycle lifecycle) {
        super(lifecycle);

        for (int i = 0; i < 30; i++) {
            this.dataSet.add(new DisplayableItem<>(R.layout.item_media, 0));
        }

    }

    @Override
    protected void initViewHolderListeners(DataBoundViewHolder viewHolder, int viewType) {

    }

    @Override
    protected void bind(@NonNull ViewDataBinding binding, @NonNull DisplayableItem<Integer> item, int position) {
        Context context = binding.getRoot().getContext();
        Glide.with(context)
                .load(ContextCompat.getDrawable(context, R.drawable.barcellona))
                .apply(new RequestOptions()
                        .error(R.drawable.ukelele)
                        .override(400)
                        .centerCrop()
                ).thumbnail(Glide.with(context).load(Uri.EMPTY))
                .into(((ItemMediaBinding) binding).image);
    }

    @Override
    protected boolean areItemTheSame(DisplayableItem<Integer> oldItem, DisplayableItem<Integer> newItem) {
        return false;
    }

    @Override
    protected boolean areContentTheSame(DisplayableItem<Integer> oldItem, DisplayableItem<Integer> newItem) {
        return false;
    }
}
