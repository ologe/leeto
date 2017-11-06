package olog.dev.leeto.data.permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import olog.dev.leeto.dagger.PerActivity;
import olog.dev.leeto.utility.BuildVersion;

@PerActivity
public class PermissionHelper implements IPermissionHelper {

    public static final int REQUEST_CODE = 123;

    public static final String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    private static Map<String, BehaviorSubject<Boolean>> subjects;

    private Activity activity;
    private PermissionFragment fragment;

    @Inject PermissionHelper(Activity activity) {
        this.activity = activity;
        subjects = new ArrayMap<>(1);

        fragment = (PermissionFragment) ((AppCompatActivity)activity).getSupportFragmentManager()
                .findFragmentByTag(PermissionFragment.TAG);

        if (fragment == null){
            fragment = PermissionFragment.newInstance((AppCompatActivity) activity);
        }
    }

    @Override
    public Observable<Boolean> observePermission(@NonNull String permission) {
        BehaviorSubject<Boolean> subject = subjects.get(permission);

        if(subject == null){
            subject = BehaviorSubject.create();
            subjects.put(permission, subject);
        }

        // initialize with current permission value
        return subject.doOnSubscribe(disposable -> subjects.get(permission).onNext(hasPermission(permission)));
    }

    @Override
    public void requestPermission(@NonNull String permission) {
        fragment.requestPermission(permission);
    }

    @SuppressWarnings({"SimplifiableConditionalExpression", "UnnecessaryLocalVariable"})
    @Override
    public boolean hasPermission(@NonNull String permission) {
        boolean hasPermission = BuildVersion.Marshmallow()
                ? ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
                : true;

        return hasPermission;
    }

    public static class PermissionFragment extends Fragment {

        private static final String TAG = "PermissionFragment";

        public static PermissionFragment newInstance(AppCompatActivity activity){
            PermissionFragment fragment = new PermissionFragment();

            activity.getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, fragment, TAG)
                    .commit();

            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if(requestCode != REQUEST_CODE){
                throw new IllegalArgumentException(String.valueOf(REQUEST_CODE));
            }

            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];

                boolean permissionGranted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                subjects.get(permission).onNext(permissionGranted);

                if(!permissionGranted && permissionNeverShowAgain(permission)){
                    onPermissionBlocked(permission);
                }
            }
        }

        private boolean permissionNeverShowAgain(@NonNull String permission) {
            return !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission);
        }

        private void onPermissionBlocked(@NonNull String permission){

            // TODO hardcoded strings
            if(permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Permission denied")
                        .setMessage("Without storage permission the app is unable to load songs")
                        .setPositiveButton("Go to settings",
                                (dialogInterface, i) -> goToSettingsForPermission()
                        );

                builder.show();
            }
        }

        private void goToSettingsForPermission() {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
            getActivity().startActivity(intent);
        }

        public void requestPermission(@NonNull String permission) {
            if(BuildVersion.Marshmallow()){
                requestPermissions(new String[]{ permission }, REQUEST_CODE);
            }
        }
    }


}
