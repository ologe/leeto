package olog.dev.leeto.data.model;

import android.support.annotation.NonNull;

import java.util.Date;

import olog.dev.leeto.utility.DateUtils;

public class Stop {

    @NonNull
    private final Date date;
    @NonNull
    private final Location location;

    public Stop(@NonNull Date date, @NonNull Location location) {
        this.date = date;
        this.location = location;
    }

    @NonNull
    public String getFormattedDate(){
        return DateUtils.toString(date);
    }

    @NonNull
    public Location getLocation() {
        return location;
    }
}
