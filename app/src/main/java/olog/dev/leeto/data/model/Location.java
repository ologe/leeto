package olog.dev.leeto.data.model;

import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import olog.dev.leeto.utility.AppConstants;

public class Location {

    private final String name;
    private final double latitude;
    private final double longitude;
    private final String address;
    private final String description;

    public Location(@NonNull String name,
                    @FloatRange(from = -90, to = 90) double latitude,
                    @FloatRange(from = -180, to = 180) double longitude,
                    @NonNull String address,
                    @Nullable String description) {

        if(TextUtils.isEmpty(description)) {
            description = AppConstants.NO_DESCRIPTION;
        }

        this.name = name;
        // TODO
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }

    @NonNull
    public String getFormattedLocation(){
        return name + " \u00B7 " + address;
    }

}
