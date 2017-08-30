package olog.dev.leeto.ui.activity_add_journey;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import olog.dev.leeto.R;
import olog.dev.leeto.base.AbsPresenter;
import olog.dev.leeto.data.model.Journey;
import olog.dev.leeto.data.model.Location;
import olog.dev.leeto.data.permission.IPermissionHelper;
import olog.dev.leeto.data.permission.PermissionHelper;
import olog.dev.leeto.data.repository.IRepository;
import olog.dev.leeto.utility.LocationUtils;
import olog.dev.leeto.utility.dagger.annotations.context.ActivityContext;
import olog.dev.leeto.utility.reactive.BaseSchedulersProvider;

public class AddJourneyPresenter extends AbsPresenter<AddJourneyContract.View> implements AddJourneyContract.Presenter {

    private Context context;
    private IPermissionHelper permissionHelper;

    @Inject
    AddJourneyPresenter(@ActivityContext Context context,
                        AddJourneyContract.View view,
                        IPermissionHelper permissionHelper,
                        BaseSchedulersProvider schedulers,
                        CompositeDisposable subscriptions,
                        IRepository repository){
        super(view, repository, subscriptions, schedulers);
        this.context = context;
        this.permissionHelper = permissionHelper;
    }

    @Override
    public void onLocationRequestClick() {
        if(permissionHelper.hasPermission(PermissionHelper.LOCATION)){
            getCurrentLocation();
        } else permissionHelper.requestPermission(PermissionHelper.LOCATION);
    }

    @Override
    protected void subscribe() {
        Disposable disposable = permissionHelper.observePermission(PermissionHelper.LOCATION)
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe(hasPermission -> {
                    if(hasPermission) {
                        getCurrentLocation();
                    }
                }, Throwable::printStackTrace);
        subscriptions.add(disposable);
    }

    @Override
    public void addJourneyToRepository(@NonNull Journey journey) {
        repository.addJourney(journey);
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation(){

        if(!LocationUtils.isLocationEnabled(context)){
            Toast.makeText(context, R.string.enable_location, Toast.LENGTH_SHORT).show();
            return;
        }

        LocationServices.getFusedLocationProviderClient(context)
                .getLastLocation()
                .addOnSuccessListener((Activity) context, location -> {
                    if(location == null) return;

                    Location locationObject = null;

                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                    try {
                        Address address = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1).get(0);

                        locationObject = new Location(
                                address.getCountryName(),
                                location.getLatitude(),
                                location.getLongitude(),
                                address.getThoroughfare() + ", " + address.getLocality(),
                                null
                        );

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context, R.string.location_not_found, Toast.LENGTH_SHORT).show();
                    }

                    view.updateLocation(locationObject);

                })
                .addOnFailureListener((Activity) context, e -> {
                    e.printStackTrace();
                    Toast.makeText(context, R.string.location_something_went_wrong, Toast.LENGTH_SHORT).show();
        });
    }

}
