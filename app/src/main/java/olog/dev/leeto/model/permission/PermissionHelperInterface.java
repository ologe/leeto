package olog.dev.leeto.model.permission;

import android.support.annotation.NonNull;

import io.reactivex.Observable;

public interface PermissionHelperInterface {

    Observable<Boolean> observePermission(@NonNull String permission);
    void requestPermission(@NonNull String permission);
    boolean hasPermission(@NonNull String permission);
    void updatePermission(@NonNull String permission, boolean value);
    boolean permissionNeverShowAgain(@NonNull String permission);
    void goToSettingsForPermission();

}
