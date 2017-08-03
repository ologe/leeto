package olog.dev.leeto.model.pojo;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import olog.dev.leeto.R;

public class Location implements Parcelable{

    private String name;
    private double latitude;
    private double longitude;
    private String address;
    private String shortDescription;

    public Location(@NonNull Context context,
                    @NonNull String name,
                    @FloatRange(from = -90, to = 90) double latitude,
                    @FloatRange(from = -180, to = 180) double longitude,
                    @NonNull String address,
                    @Nullable String shortDescription) {
        if(shortDescription == null || TextUtils.isEmpty(shortDescription))
            shortDescription = context.getString(R.string.no_description);

        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.shortDescription = shortDescription;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    @NonNull
    public String getShortDescription() {
        return shortDescription;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public void setShortDescription(@NonNull String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLatitude(@FloatRange(from = -90, to = 90) double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(@FloatRange(from = -180, to = 180) double longitude) {
        this.longitude = longitude;
    }

    public Location(Parcel in){
        name = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        address = in.readString();
        shortDescription = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(address);
        parcel.writeString(shortDescription);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public final static Creator CREATOR = new Creator() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

}
