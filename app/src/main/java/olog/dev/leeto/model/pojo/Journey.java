package olog.dev.leeto.model.pojo;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import olog.dev.leeto.R;

public class Journey implements Parcelable {

    private String name;
    private String shortDescription;
    private List<Stop> stopList;

    {
        stopList = new ArrayList<>();
    }

    public Journey(@NonNull Context context,
                   @NonNull String name,
                   @NonNull String shortDescription) {
        if(TextUtils.isEmpty(shortDescription))
            shortDescription = context.getString(R.string.no_description);

        this.name = name;
        this.shortDescription = shortDescription;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(@NonNull String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void addStop(@NonNull Date date, @NonNull Location location){
        stopList.add(new Stop(date, location));
    }

    @NonNull
    public Stop getFirstStop(){
        return stopList.get(0);
    }

    @NonNull
    public List<Stop> getStopsList() {
        return stopList;
    }

    // parcelable
    public Journey(Parcel in){
        name = in.readString();
        shortDescription = in.readString();
        stopList = new ArrayList<>();
        in.readList(stopList, Stop.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(shortDescription);
        parcel.writeList(stopList);
    }

    @Override
    public int describeContents() {
        return 0;
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
