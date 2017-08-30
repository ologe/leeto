package olog.dev.leeto.data.model;

import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import olog.dev.leeto.utility.AppConstants;

@Value
@ToString(of = {"name", "latitude", "longitude"})
@EqualsAndHashCode(of = {"name", "latitude", "longitude", "address"})
public class Location {

    String name;
    double latitude;
    double longitude;
    String address;
    String shortDescription;

    public Location(@NonNull String name,
                    @FloatRange(from = -90, to = 90) double latitude,
                    @FloatRange(from = -180, to = 180) double longitude,
                    @NonNull String address,
                    @Nullable String shortDescription) {
        if(TextUtils.isEmpty(shortDescription))
            shortDescription = AppConstants.NO_DESCRIPTION;

        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.shortDescription = shortDescription;
    }

}
