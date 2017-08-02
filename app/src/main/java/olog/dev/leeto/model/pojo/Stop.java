package olog.dev.leeto.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

public class Stop implements Parcelable{

    private Date date;
    private Location location;

    public Stop(@NonNull Date date,@NonNull Location location) {
        this.date = date;
        this.location = location;
    }

    public Stop(Parcel in){
        date = new Date(in.readLong());
        location = in.readParcelable(Location.class.getClassLoader());
    }

    public Date getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(date.getTime());
        parcel.writeParcelable(location, flags);
    }


    public final static Creator CREATOR = new Creator() {
        @Override
        public Stop createFromParcel(Parcel source) {
            return new Stop(source);
        }

        @Override
        public Stop[] newArray(int size) {
            return new Stop[size];
        }
    };

}
