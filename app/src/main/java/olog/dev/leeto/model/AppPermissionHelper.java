package olog.dev.leeto.model;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.ArrayMap;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import olog.dev.leeto.dagger.annotation.PerActivity;
import olog.dev.leeto.utility.BuildVersion;
import timber.log.Timber;

@PerActivity
public class AppPermissionHelper implements PermissionHelperInterface{

    public static final int REQUEST_CODE = 123;

    public static final String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    private Map<String, BehaviorSubject<Boolean>> subjects;

    private Activity activity;

    @Inject
    AppPermissionHelper(Activity activity) {
        this.activity = activity;
        subjects = new ArrayMap<>(1);
    }

    @Override
    public Observable<Boolean> observePermission(@NonNull String permission) {
        Timber.d("observePermission " + permission);

        if(!subjects.containsKey(permission)){
            BehaviorSubject<Boolean> subject = BehaviorSubject.create();
            subjects.put(permission, subject);
        }

        return subjects.get(permission)
                // initialize with current permission value
                .doOnSubscribe(disposable -> subjects.get(permission).onNext(hasPermission(permission)));
    }

    @Override
    public void requestPermission(@NonNull String permission) {
        Timber.d("requestPermission " + permission);

        if(BuildVersion.Marshmallow()){
            ActivityCompat.requestPermissions(activity, new String[]{permission}, REQUEST_CODE);
        }
    }

    @SuppressWarnings({"SimplifiableConditionalExpression", "UnnecessaryLocalVariable"})
    @Override
    public boolean hasPermission(@NonNull String permission) {
        boolean hasPermission = BuildVersion.Marshmallow()
                ? ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
                : true;

        Timber.d("hasPermission " + permission + " = " + hasPermission);

        return hasPermission;
    }

    @Override
    public void updatePermission(@NonNull String permission, boolean value) {
        Timber.d("updatePermission: " + permission + " to " + value);
    }

    @Override
    public boolean permissionNeverShowAgain(@NonNull String permission) {
        boolean userBitch = !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);

        if(userBitch) Timber.w("neverShowAgain for permission " + permission);

        return userBitch;
    }

    @Override
    public void goToSettingsForPermission() {
        Timber.d("goToSettingsForPermission");

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }

}
