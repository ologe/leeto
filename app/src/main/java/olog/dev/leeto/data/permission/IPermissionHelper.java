package olog.dev.leeto.data.permission;

import android.support.annotation.NonNull;

import io.reactivex.Observable;

public interface IPermissionHelper {

    Observable<Boolean> observePermission(@NonNull String permission);
    void requestPermission(@NonNull String permission);
    boolean hasPermission(@NonNull String permission);

}
