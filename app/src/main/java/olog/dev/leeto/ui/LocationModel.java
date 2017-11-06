package olog.dev.leeto.ui;

import android.location.Address;
import android.location.Location;

public class LocationModel {

    private final String name;
    private final String latitude;
    private final String longitude;
    private final String address;

    public LocationModel(Location location, Address address){
        this.name = address.getCountryName();
        this.latitude = String.valueOf(location.getLatitude());
        this.longitude = String.valueOf(location.getLongitude());
        this.address = address.getThoroughfare() + ", " + address.getLocality();
    }

    public LocationModel(String name, String latitude, String longitude, String address) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }
}
