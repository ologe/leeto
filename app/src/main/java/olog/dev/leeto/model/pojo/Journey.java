package olog.dev.leeto.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Journey implements Parcelable {

    private String name;
    private String shortDescription;
    private List<Stop> stopList;

    public Journey(String name, String shortDescription) {
        this.name = name;
        this.shortDescription = shortDescription;

        stopList = new ArrayList<>();
    }

    public Journey(@NonNull String name, @NonNull String shortDescription, @NonNull Date startDate, @NonNull Location startLocation) {
        this(name, shortDescription);
        stopList.add(new Stop(startDate, startLocation));
    }

    public Journey(Parcel in){
        name = in.readString();
        shortDescription = in.readString();
        stopList = new ArrayList<>();
        in.readList(stopList, Stop.class.getClassLoader());
    }

    public String getName() {
        return name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public List<Stop> getStopsList() {
        return stopList;
    }

    public void addStop(Date date, Location location){
        stopList.add(new Stop(date, location));
    }

    public void addStop(Stop stop){
        stopList.add(stop);
    }

    public Stop getFirstStop(){
        return stopList.get(0);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(shortDescription);
        parcel.writeList(stopList);
    }

    public final static Creator CREATOR = new Creator() {
        @Override
        public Journey createFromParcel(Parcel source) {
            return new Journey(source);
        }

        @Override
        public Journey[] newArray(int size) {
            return new Journey[size];
        }
    };

}
