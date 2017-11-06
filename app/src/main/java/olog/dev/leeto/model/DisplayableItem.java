package olog.dev.leeto.model;

import android.support.annotation.Nullable;

public class DisplayableItem<T> {

    private final int type;

    @Nullable
    private final T model;

    public DisplayableItem(int type, @Nullable T model) {
        this.type = type;
        this.model = model;
    }

    public int getType() {
        return type;
    }

    @Nullable
    public T getModel() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DisplayableItem<?> that = (DisplayableItem<?>) o;

        if (type != that.type) return false;
        return model != null ? model.equals(that.model) : that.model == null;
    }

    @Override
    public int hashCode() {
        int result = type;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
    }
}
