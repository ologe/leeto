package olog.dev.leeto.activity_add_journey;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import olog.dev.leeto.dagger.annotation.ActivityContext;
import olog.dev.leeto.model.AppPermissionHelper;
import olog.dev.leeto.model.PermissionHelperInterface;
import olog.dev.leeto.model.pojo.Journey;
import olog.dev.leeto.model.pojo.Location;
import olog.dev.leeto.model.repository.RepositoryInterface;
import olog.dev.leeto.utility.LocationUtils;
import olog.dev.leeto.utility.rx.BaseSchedulerProvider;
import timber.log.Timber;

public class AddJourneyPresenter implements AddJourneyContract.Presenter {

    private Context context;
    private AddJourneyContract.View view;
    private PermissionHelperInterface permissionHelper;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable subscriptions;
    private RepositoryInterface repository;

    @Inject
    AddJourneyPresenter(@ActivityContext Context context,
                        AddJourneyContract.View view,
                        PermissionHelperInterface permissionHelper,
                        BaseSchedulerProvider schedulerProvider,
                        CompositeDisposable subscriptions,
                        RepositoryInterface repository){
        this.context = context;
        this.view = view;
        this.permissionHelper = permissionHelper;
        this.schedulerProvider = schedulerProvider;
        this.subscriptions = subscriptions;
        this.repository = repository;
    }

    @Override
    public void showDatePicker(DatePickerDialog dialog) {
        dialog.show();
    }

    @Override
    public void onLocationRequestClick(Context context) {
        if(permissionHelper.hasPermission(AppPermissionHelper.LOCATION)){
            getCurrentLocation(context);
        } else permissionHelper.requestPermission(AppPermissionHelper.LOCATION);
    }

    @Override
    public void subscribe() {
        Timber.d("subscribe");

        Timber.e("onCreate " + permissionHelper);

        Disposable disposable = permissionHelper.observePermission(AppPermissionHelper.LOCATION)
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(hasPermission -> {
                    if(hasPermission) {
                       getCurrentLocation(context);
                    }
                }, Throwable::printStackTrace);
        subscriptions.add(disposable);
    }

    @Override
    public void unsubscribe() {
        Timber.d("unsubscribe");
        subscriptions.clear();
    }

    @Override
    public void addJourneyToRepository(@NonNull Journey journey) {
        repository.addJourney(journey);
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation(Context context){

        if(!LocationUtils.isLocationEnabled(context)){
            Toast.makeText(context, R.string.enable_location, Toast.LENGTH_SHORT).show();
            return;
        }

        LocationServices.getFusedLocationProviderClient(context)
                .getLastLocation()
                .addOnSuccessListener((Activity) context, location -> {
                    if(location == null) return;

                    Location locationObject = new Location();

                    locationObject.setLatitude(location.getLatitude());
                    locationObject.setLongitude(location.getLongitude());

                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                    try {
                        Address address = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1).get(0);

                        locationObject.setName(address.getCountryName());
                        locationObject.setAddress(address.getThoroughfare() + ", " + address.getLocality());

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
