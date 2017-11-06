package olog.dev.leeto.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;

import com.patloew.rxlocation.RxLocation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dev.olog.shared.ApplicationContext;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import olog.dev.leeto.dagger.PerActivity;

import static android.content.Context.LOCATION_SERVICE;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

@PerActivity
public class LocationManager {

    private final Context context;
    private final RxLocation rxLocation;
    private final RxPermissions rxPermission;

    @Inject LocationManager(
            @ApplicationContext Context context,
            RxLocation rxLocation,
            RxPermissions rxPermission) {

        this.context = context;
        this.rxLocation = rxLocation;
        this.rxPermission = rxPermission;
    }

    @SuppressLint("MissingPermission")
    public Single<LocationModel> request(){
        return rxPermission.request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .filter(isGranted -> isGranted)
                .flatMap(checkIfLocationIsEnabled)
                .flatMapMaybe(granted -> rxLocation.location()
                        .lastLocation()
                        .timeout(4, TimeUnit.SECONDS)
                ).firstOrError()
                .flatMap(location -> rxLocation.geocoding().fromLocation(
                            location.getLatitude(), location.getLongitude()
                        ).toSingle()
                        .timeout(4, TimeUnit.SECONDS)
                        .map(address -> new LocationModel(location, address))
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Function<Boolean, Observable<Boolean>> checkIfLocationIsEnabled = permissionAlreadyGranted -> {
        if (isLocationEnabled()){
            return Observable.just(permissionAlreadyGranted);
        } else {
            return Observable.error(new DisabledLocationException());
        }
    };

    private boolean isLocationEnabled(){
        android.location.LocationManager locationManager = (android.location.LocationManager) context.getSystemService(LOCATION_SERVICE);

        boolean isGpsEnabled = locationManager != null && locationManager.isProviderEnabled(GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager != null && locationManager.isProviderEnabled(NETWORK_PROVIDER);

        return isGpsEnabled && isNetworkEnabled;
    }



}
