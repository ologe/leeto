package olog.dev.leeto.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;

import com.patloew.rxlocation.RxLocation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.Single;
import olog.dev.leeto.dagger.PerActivity;

@PerActivity
public class LocationManager {

    private final RxLocation rxLocation;
    private final RxPermissions rxPermission;

    @Inject LocationManager(RxLocation rxLocation, RxPermissions rxPermission) {
        this.rxLocation = rxLocation;
        this.rxPermission = rxPermission;
    }

    @SuppressLint("MissingPermission")
    public Single<Location> request(){
        return rxPermission.request(Manifest.permission_group.LOCATION)
                .filter(isGranted -> isGranted)
                .flatMapMaybe(granted -> rxLocation.location().lastLocation())
                .firstOrError();
    }

}
