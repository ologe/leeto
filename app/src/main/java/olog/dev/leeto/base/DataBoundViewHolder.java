package olog.dev.leeto.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class DataBoundViewHolder <T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private final T binding;

    public DataBoundViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public T getBinding() {
        return binding;
    }

    public void setOnClickListener(Runnable runnable){
        itemView.setOnClickListener(v -> {
            if (getAdapterPosition() != RecyclerView.NO_POSITION){
                runnable.run();
            }
        });
    }

    public void setOnLongClickListener(Runnable runnable){
        itemView.setOnLongClickListener(v -> {
            if (getAdapterPosition() != RecyclerView.NO_POSITION){
                runnable.run();
                return true;
            }
            return false;
        });
    }

}
