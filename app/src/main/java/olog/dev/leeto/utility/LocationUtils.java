package olog.dev.leeto.utility;

import android.content.Context;
import android.location.LocationManager;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LocationUtils {

    public static boolean isLocationEnabled(Context context){
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        boolean isGpsEnabled = locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager != null && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return isGpsEnabled && isNetworkEnabled;
    }

}
