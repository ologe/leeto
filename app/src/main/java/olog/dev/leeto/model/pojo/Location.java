package olog.dev.leeto.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import io.reactivex.annotations.Nullable;

public class Location implements Parcelable{

    private String name;
    private double latitude;
    private double longitude;
    private String address;
    private String shortDescription;

    public Location(){
        name = "";
        latitude = 0;
        longitude = 0;
        address = "";
        shortDescription = "";
    }

    public Location(@NonNull String name, double latitude, double longitude,@NonNull String address, @Nullable String shortDescription) {
        if(shortDescription == null) shortDescription = "";

        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.shortDescription = shortDescription;
    }

    public Location(Parcel in){
        name = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        address = in.readString();
        shortDescription = in.readString();
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(address);
        parcel.writeString(shortDescription);
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


}
