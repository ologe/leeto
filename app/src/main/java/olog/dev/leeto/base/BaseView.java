package olog.dev.leeto.base;

import android.support.annotation.NonNull;

public interface BaseView<T>  {

    void attachLifecycleOwner(@NonNull T presenter);

}
