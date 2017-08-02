package olog.dev.leeto.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Journey implements Parcelable {

    private long id;
    private String name;
    private String shortDescription;
    private List<Stop> stopList;

    public Journey(long id, String name, String shortDescription) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;

        stopList = new ArrayList<>();
    }

    public Journey(long id, @NonNull String name, @NonNull String shortDescription, @NonNull Date startDate, @NonNull Location startLocation) {
        this(id, name, shortDescription);
        stopList.add(new Stop(startDate, startLocation));
    }

    public Journey(Parcel in){
        name = in.readString();
        shortDescription = in.readString();
        id = in.readLong();
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

    public long getId() {
        return id;
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
        parcel.writeLong(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return id == ((Journey) o).id;
    }

    @Override
    public int hashCode() {
        return (int) id;
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
