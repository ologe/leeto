package olog.dev.leeto.ui;

import android.Manifest;
import android.annotation.SuppressLint;

import com.patloew.rxlocation.RxLocation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
    public Single<LocationModel> request(){
        return rxPermission.request(Manifest.permission_group.LOCATION)
                .filter(isGranted -> isGranted)
                .flatMapMaybe(granted -> rxLocation.location().lastLocation())
                .timeout(4, TimeUnit.SECONDS)
                .firstOrError()
                .flatMap(location -> rxLocation.geocoding().fromLocation(
                        location.getLatitude(), location.getLongitude())
                        .toSingle()
                        .map(address -> new LocationModel(location, address))
                )
                .timeout(4, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
